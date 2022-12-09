//// =====================================================================================
//// THis is a pseudo application that consists of a primary application, a single
//// main component, and a singe primary plugin for that component.
////
//// Independently of the main app a second component will be loaded, and a second
//// plugin will be loaded for that component.  These will be loaded using a 
//// post-delegatipon ('local first') classloader so they are isolated from the
//// component in the main application.
////
//// -------------------------------------------------------------------------------------
//// Using kotlinc:
////    Compile from root of project with
////      classloader> kotlinc -cp "./src/kotlin/main" offtheecliptic.loader.test.ex01.Test01.kt
//// Run with (if there was a main)
////      classloader> kotlin -cp "build/classes/kotlin/main" offtheecliptic.loader.test.ex01.Test01
//// -------------------------------------------------------------------------------------
//// Using gradle (much easier/faster):
////    Compile from root of project:
////       classloader> gradle clean build
////    Run with Java or Kotlin:
////       loader> kotlin -cp "build/classes/kotlin/main" offtheecliptic.loader.test.ex01.Test01
//// =====================================================================================
package offtheecliptic.loader.test.basic.app

import offtheecliptic.loader.spi.interfaces.extend.*
import offtheecliptic.utils.classloading.PostDelegationClassLoader
import offtheecliptic.loader.utils.extensionloader.*

import java.net.URL
import java.io.File
import java.io.IOException

/// =====================================================================================================
/// IMPLEMENTATION FUNCTIONS
/// =====================================================================================================

/**
 * Basic application which loads 2 diffeerent components, each of which loads a plugin.
 * The first component and plugin impls are loaded in the same default class loader as 
 * the App.
 */
public class App {
    fun run() {
        val component1: Component = ComponentImpl("1", "CL 1", "CL 1")
        println("\n>>> App Component 1 is statically loaded: " + 
                "\n" + component1.toString())

        val componentRootDir2  = "build/classes/kotlin/test/"
        val componentClass2    = "components.ComponentImpl"
        val properties         = mapOf<String,String>("extensionVersion" to "2",
                                                      "classLoaderVersion" to "CL 2",
                                                      "parentClassLoaderVersion" to "CL 1")
        val component2         = loadExtension(properties, componentRootDir2, componentClass2)

        println("\n>>> App Component 2 is dynamically loaded: " + 
                "\n" + component2.toString())
    }
}

// Simple implementation of Component interface; will have multiple versions
public class ComponentImpl(var extensionVersion: String? = "",
                           var classLoaderVersion: String? = "", 
                           var parentClassLoaderVersion: String? = ""): Component {
    val plugin: Plugin = PluginImpl("1", "CL 1", "CL 1")
    override fun init(properties: Map<String,String>) {
        // No-op, initialized in constructor above
    }
    override fun toString(): String  {
        return "    ### ComponentImpl class version " + extensionVersion + " loaded in " + classLoaderVersion +
               ", which implements Component interface loaded in " + parentClassLoaderVersion +
               "\n        >>> Component Plugin is statically loaded: " +
               "\n        " + plugin.toString()
    }
}

// Simple implementation of Plugin interface
class PluginImpl(var extensionVersion: String? = "",
                 var classLoaderVersion: String? = "", 
                 var parentClassLoaderVersion: String? = ""): Plugin {
    //override fun test(): String {
    override fun init(properties: Map<String,String>) {
        // No-op, initialized in constructor above
    }
    override fun toString(): String {
        //return ComponentImpl().toString()
        return "    ### PluginImpl class version " + extensionVersion + " loaded in " + classLoaderVersion + 
               ", which implements Plugin interface loaded in " + parentClassLoaderVersion
    }
}

