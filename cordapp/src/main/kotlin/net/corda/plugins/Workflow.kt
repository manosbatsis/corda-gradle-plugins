package net.corda.plugins

import org.gradle.api.tasks.Input

open class Workflow {
    @get:Input
    var name: String? = null
    /** relaxed type so users can specify Integer or String identifiers */
    @get:Input
    var versionId: Any? = null
    @get:Input
    var vendor: String? = null
    @get:Input
    var licence: String? = null
}