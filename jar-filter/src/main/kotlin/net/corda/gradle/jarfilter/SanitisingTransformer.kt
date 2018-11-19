package net.corda.gradle.jarfilter

import kotlinx.metadata.*
import kotlinx.metadata.Flag.Constructor.IS_PRIMARY
import kotlinx.metadata.jvm.JvmConstructorExtensionVisitor
import kotlinx.metadata.jvm.JvmMethodSignature
import kotlinx.metadata.jvm.KotlinClassHeader
import kotlinx.metadata.jvm.KotlinClassMetadata
import org.gradle.api.logging.LogLevel
import org.gradle.api.logging.Logger
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*

/**
 * This is (hopefully?!) a temporary solution for classes with [JvmOverloads] constructors.
 * We need to be able to annotate ONLY the secondary constructors for such classes, but Kotlin
 * will apply any annotation to all constructors equally. Nor can we replace the overloaded
 * constructor with individual constructors because this will break ABI compatibility. (Kotlin
 * generates a synthetic public constructor to handle default parameter values.)
 *
 * This transformer identifies a class's primary constructor and removes all of its unwanted annotations.
 * It will become superfluous when Kotlin allows us to target only the secondary constructors with our
 * filtering annotations in the first place.
 */
class SanitisingTransformer(visitor: ClassVisitor, logger: Logger, private val unwantedAnnotations: Set<String>)
    : KotlinBeforeProcessor(ASM6, visitor, logger, mutableMapOf()) {

    var isModified: Boolean = false
        private set
    override val level: LogLevel = LogLevel.DEBUG

    private var className: String = "(unknown)"
    private var primaryConstructor: MethodElement? = null

    override fun getPackageVisitor(pv: KmPackageVisitor?): KmPackageVisitor? = null

    override fun getClassVisitor(cv: KmClassVisitor?): KmClassVisitor = object : KmClassVisitor(cv) {
        override fun visitConstructor(flags: Flags): KmConstructorVisitor? {
            return if (IS_PRIMARY(flags)) {
                object : KmConstructorVisitor() {
                    override fun visitExtensions(type: KmExtensionType): KmConstructorExtensionVisitor? {
                        if (type != JvmConstructorExtensionVisitor.TYPE) return null

                        return object : JvmConstructorExtensionVisitor() {
                            override fun visit(desc: JvmMethodSignature?) {
                                val signature = desc ?: return
                                primaryConstructor = signature.toMethodElement()
                                logger.log(level, "Class {} has primary constructor {}", className, signature.asString())
                            }
                        }
                    }
                }
            } else {
                null
            }
        }
    }

    override fun processClassMetadata(header: KotlinClassHeader, metadata: KotlinClassMetadata.Class): KotlinClassHeader? {
        metadata.accept(getClassVisitor(null))
        return null
    }

    override fun visit(version: Int, access: Int, clsName: String, signature: String?, superName: String?, interfaces: Array<String>?) {
        className = clsName
        super.visit(version, access, clsName, signature, superName, interfaces)
    }

    override fun visitMethod(access: Int, methodName: String, descriptor: String, signature: String?, exceptions: Array<String>?): MethodVisitor? {
        val method = MethodElement(methodName, descriptor, access)
        val mv = super.visitMethod(access, methodName, descriptor, signature, exceptions) ?: return null
        return if (method == primaryConstructor) SanitisingMethodAdapter(mv, method) else mv
    }

    private inner class SanitisingMethodAdapter(mv: MethodVisitor, private val method: MethodElement) : MethodVisitor(api, mv) {
        override fun visitAnnotation(descriptor: String, visible: Boolean): AnnotationVisitor? {
            if (unwantedAnnotations.contains(descriptor)) {
                logger.info("Sanitising annotation {} from method {}.{}{}", descriptor, className, method.name, method.descriptor)
                isModified = true
                return null
            }
            return super.visitAnnotation(descriptor, visible)
        }
    }
}
