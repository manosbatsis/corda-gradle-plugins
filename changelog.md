# Changelog

## Version 4

### Version 4.0.38

### Version 4.0.37

* `cordapp`: Cordapp naming conventions changed to CordappContract and CordappWorkflow for all attributes (name, version, vendor, licence)

### Version 4.0.36

* `cordapp`: Fixed the development key for Cordapp signing (incorrect ISO country code in CN).

### Version 4.0.35

* `cordapp`: New `sealing` entry allows to opt-out generation of sealed CorDapp JAR.

### Version 4.0.34

* `cordapp`: New `SignJar` task allows to sign arbitrary JAR files by reusing `signing` configuration of the `Cordapp` plugin

### Version 4.0.33

* `cordformation`: Add ability to specify a flow override as part of cordformation via `flowOverride`

### Version 4.0.32

* `cordformation`: Exclude transitive dependencies from Jolokia agent.
* `cordformation`: Support for Signature Constraints - the plugin signs all Cordapp JARs by default with Corda development key. It can be disabled or configured to generate ad-hoc keyStore/key or use an external keyStore.
* `cordapp`: Support for Signature Constraints - the plugin signs Cordapp JAR by default with Corda development key, it can be disabled or configured to use an external keyStore. Signed CorDapp JAR is also marked as sealed.

### Versions 4.0.30, 4.0.31

* `cordapp`: Add `targetPlatformVersion` and `minimumPlatformVersion` to the CorDapp Info. The min platform version defaults to 1. If the target version is not set by the user, the plugin attempts to read the platform version of the project's Corda dependency. 
* `cordformation`: Removed experimental feature `CordformDefinition`

### Version 4.0.29

* `cordformation`: Allow us not to deploy cordformation's project as another cordapp to all nodes.

### Version 4.0.28

* `quasar-utils`: Add `quasar-core` to the `compileClasspath` configuration, and its transitive dependencies to the `runtimeClasspath` configuration.

### Version 4.0.27

* `jar-filter`: Initial import from Corda.

### Version 4.0.26

* `cordapp`: Remove `cordaCompile` and `cordaRuntime` dependencies from the CorDapp's transitive dependencies.
* `cordformation`: Use latest version of jolokia, when version not found in project settings.
* `cordformation`: The `sshd` port entry in `node` produces an entry in `node.conf` that matches the config API.

### Version 4.0.25

* `quasar-utils': The plugin now does not require projects to specify `quasar_group` and `qauas

### Version 4.0.24

* `cordformation`: The `node` entry has a new optional element `drivers`, which is a list of JAR files to be copied to the `./driver` subdirectory relative to node directory (ENT-2035).

* `cordformation`: ENT-2059 The optional `configFile` element of the `node` entry ìs no longer added to `node.conf` as it was never used by the node and it's used internally by `cordformation` only.

### Version 4.0.23

* `publish-utils`: CORDA-1576 Support publishing non-default JAR artifacts.

### Version 4.0.22

* `cordformation`,`cordapp`: ENT-1871 Rebrand R3Corda to Corda Enterprise.

### Version 4.0.21

### Version 4.0.20

* `cordformation`: Use default parent classpath when loading `NetworkBootstrapper` to stop node picking up plugin dependencies.  
* `cordformation`: Ensure that subclasses of `CordformDefinition` cannot access anything on the Gradle plugins classpath.

### Version 4.0.19

### Version 4.0.18

* `cordformation`: Set `devMode=true` by default, and set option to set it

### Version 4.0.17

* `cordapp`: Added `CordappInfoExtension`, supporting specification of CorDapp information "name", "vendor" and "version".

### Version 4.0.16

 * `api-scanner`: Write each annotation on a new line and remove its package name; remove `@Deprecated` annotations from the generated output.
 * `api-scanner`: Remove the `@JvmDefault` annotation from the generated output.
 * `api-scanner`: Add generic parameters to the api scanner output

### Version 4.0.15

 * `cordformation`: added Jolokia fix such that when the Jolokia JVM agent jar file is missing the Node process will still start (and give a warning)
 * `cordformation`: root nodes directory is deleted before running

### Version 4.0.14

* `cordform-common`,`cordapp`: Replace deprecated `kotlin-stdlib-jre8` with `kotlin-stdlib-jdk8`.

### Version 4.0.13

* `cordformation`: No longer copies the CorDapp jars to the nodes' `cordapps` directories. This will instead be done by
  the network bootstrapper.

### Version 4.0.12

* `cordformation`: Correctly copying CorDapp configs to cordapps/config

### Version 4.0.11

* `quasar-utils`: Add quasar-core to cordaRuntime. This ensures that it is excluded from "fat jar" CorDapps [CORDA-1301].

### Version 4.0.10

* `cordformation`: Fixes a crash in Dockerform. 
* `cordformation`: NodeRunner delegates Corda jolokia logging to slf4j.

### Version 4.0.9

* `cordformation`: Fix WebServer config file missing `security` section [CORDA-1231] 
* `api-scanner`: Remove the `@JvmStatic` annotation from the generated output.

### Version 4.0.8

* `publish-utils`: Revert "Setting the `name` property no longer triggers configuration." because it breaks publishing to Artifactory.

### Version 4.0.7

* `api-scanner`: Handle `@Inherited` annotations from library modules.
* `publish-utils`: Setting the `name` property no longer triggers configuration. This is now applied after the project has been evaluated.
* `publish-utils`: Add `publishJavadoc` property so that publishing Javadoc can be optional.

### Version 4.0.6

* `api-scanner`: Field annotations are now written out in alphanumerical order. The `@JvmField` annotation is no longer written at all.

### Version 4.0.5

* `cordformation`: Fix webserver startup when headless.

### Version 4.0.4

* `api-scanner`: Class/interface/method annotations are now written out in alphanumerical order.
* `api-scanner`: Handle methods with vararg parameters correctly.

### Version 4.0.3

* `cordformation`: Fix parsing of unknown node configuration properties.

### Version 4.0.2

* `cordformation`: Add `NetworkParameters` contract implementation whitelist.

### Version 4.0.1

* `cordformation`: Use updated rpc users config format.

### Version 4.0.0

* Split repository from [Corda](https://github.com/corda/corda) repository.

## Pre-version 4

All pre-4 changes were untagged in the [Corda](https://github.com/corda/corda) repository.
