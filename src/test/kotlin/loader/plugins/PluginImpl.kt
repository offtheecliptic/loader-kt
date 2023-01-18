//package offtheecliptic.loader.test.plugins
package plugins

//import offtheecliptic.loader.test.ex01.*
import offtheecliptic.loader.spi.interfaces.extend.*

// Plugin implementation which is loaded and instantiated by one of the Component Impls.
// Implements Plugin interface which is loaded in the parent classloader.
class PluginImpl(var extensionVersion: String? = "",
                 var classLoaderVersion: String? = "", 
                 var parentClassLoaderVersion: String? = ""): Plugin {
    override fun init(properties: Map<String,String>) {
        extensionVersion = properties["extensionVersion"]
        classLoaderVersion = properties["classLoaderVersion"]
        parentClassLoaderVersion = properties["parentClassLoaderVersion"]
    }
    override fun toString(): String {
        //return ComponentImpl().toString()
        return "    ### PluginImpl class version " + extensionVersion + " loaded in " + classLoaderVersion + 
               ", which implements Plugin interface loaded in " + parentClassLoaderVersion
    }
    //override fun test(): String {
}
