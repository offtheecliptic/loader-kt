//package offtheecliptic.loader.test.plugins
package components

import plugins.*
import offtheecliptic.loader.spi.interfaces.extend.*
import offtheecliptic.loader.utils.extensionloader.loadExtension

//import offtheecliptic.loader.test.ex01.*

// Second version of component impl class used by the plugin.
class ComponentImpl(var extensionVersion: String? = "",
                    var classLoaderVersion: String? = "", 
                    var parentClassLoaderVersion: String? = ""): Component {
    val pluginRootDir  = "build/classes/kotlin/test/"
    val pluginClass    = "plugins.PluginImpl"
    val properties     = mapOf<String,String>("extensionVersion" to "2",
                                              "classLoaderVersion" to "CL 3",
                                              "parentClassLoaderVersion" to "CL 1")
    val plugin         = loadExtension(properties, pluginRootDir, pluginClass)

    override fun init(properties: Map<String,String>) {
        extensionVersion = properties["extensionVersion"]
        classLoaderVersion = properties["classLoaderVersion"]
        parentClassLoaderVersion = properties["parentClassLoaderVersion"]
    }
    override fun toString(): String {
        return "    ### ComponentImpl class version " + extensionVersion + " loaded in " + classLoaderVersion +
               ", which implements Component interface loaded in " + parentClassLoaderVersion +
               "\n        >>> Component Plugin is dynamically loaded: " +
               "\n        " + plugin.toString()
    }
} 
