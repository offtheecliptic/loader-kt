//// =====================================================================================
//// Loader Utilities.  Extension Loading Functions.
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
package offtheecliptic.loader.utils.extensionloader

import offtheecliptic.loader.spi.interfaces.extend.*
import offtheecliptic.utils.classloading.PostDelegationClassLoader

import java.net.URL
import java.io.File


/// =====================================================================================================
/// HELPER FUNCTIONS
/// =====================================================================================================

/**
 * Function to load an extension, which may be a main component, or a plugin 
 * a component uses.
 *
 * Loading an extension involves 3 steps:
 *    1. Load the extension class, gicwen a classpath where we know extenmsions live
 *    2. Instantiate the extension instance from the loaded class
 *    3. Initialize the extension instance by passing it some properties it expects
 */
fun loadExtension(properties: Map<String,String>,
                  extensionRootDir: String, extensionFullyQualifiedClass: String): Extension {

    val extensionVersion: String = properties["extensionVersion"] as String
    val classLoaderVersion: String = properties["classLoaderVersion"] as String
    val parentClassLoaderVersion: String = properties["parentClassLoaderVersion"] as String

    println("\nLoading extension with class loader version " + classLoaderVersion +
            ", which implements Plugin interface in " + parentClassLoaderVersion)
    
    // Get the URL of the root directory that the extension implementatiuons live in.
    // We pass in an exemplar extension impl class to check that the root dir is correct.
    val extensionRootURL: URL? = getDirURL(extensionRootDir, extensionFullyQualifiedClass)

    println("Extension root URL: " + extensionRootURL.toString())

    if (extensionRootURL != null) {

        // We verified this directory holds the extension, so use it
        //val extensionRootRelPath: String = /*"file:./" + */ extensionRootDir
        //val extensionRootRelURL: URL = URL("file" + ":" + extensionRootRelPath)
        // println("extension ClassLoader Relative URL: " + extensionRootRelURL)

        //val classpath: Array<URL> = arrayOf( extensionRootRelURL)       // This does not work!
        val classpath: Array<URL> = arrayOf( extensionRootURL )           // This works
        //val classpath: Array<URL> = arrayOf( URL("file:../test/") )  // This does not work!
        //val classpath: Array<URL> = arrayOf( URL("file:./examples/classloader/test/") ) // This does not work!
        
        println("Extension classpath: " + classpath[0].toString())

        // Instantiate the PostDelegationClassLoader with a classpath where extension impls live.
        // We must pass in the parent classloader; choose the one that loaded the extension interface.
        // This class loader is loaded using the default CL, but it is *used* to load extensions 
        // into a class loader that is isolated from the default class loader.
        val classLoader: PostDelegationClassLoader = PostDelegationClassLoader(classpath as Array<URL?>?,
                                                                               Extension::class.java.classLoader) 
        
        // Now use the post-delegation classloader to instantiate the extension in the classloader.  
        // This is done by reflection in the classloader, so there is no need for this main app
        // to have the extensionImpl class at compile time, although it must have the extension interface.

        // The 'extension' instance is actually a extension impl, which implements the extension interface.
        // The ExtensionImpl class will be loaded in the extension-specific class loader, then instantiated.
        val extension: Extension = classLoader.loadClass(extensionFullyQualifiedClass)!!.newInstance() as Extension
        
        // Initialize extension
        extension.init(properties)

        //val extension: Extension = classLoader.loadClass(extensionClass)!!.newInstance() as Extension
        //val extension: Extension = classLoader.loadClass(extensionClass)!!.newInstance() as Extension

        // Use the extension, which will use a different version of CommonClass
        // Because the secondary CL was used to load the 2 classes in extension.kt, we get CommonClass v2
        //println(">>> extension says: " + extension.test())

        return extension
    }
    throw Exception("Class could not be loaded.")
}

/**
 * Helper function to compute the plugin root directory URL given the pluginRootURL  
 * and an example (in that root dirdctory) class file's fully-qualified class name.
 *
 * Params:
 *     fileDir   - Root directory under which all plugins live
 *     className - Fully-qualified class name of an exemplar plug impl file, to verify
 *                   that the file root does indeed hold plugins
 */
fun getDirURL(fileDir: String, className: String): URL? {
    val dirFile: File = File(fileDir)
    val filePath: String = className.replace(".", "/")

    if (dirFile.exists() && dirFile.isDirectory()) {
        println("Directory " + dirFile.getAbsolutePath().replace("C:", "") + " OK")

        val fullFilePath = fileDir + "/" + filePath + ".class"
        val fileFile: File = File(fullFilePath)

        if (fileFile.exists()) {
            println("File " + fileFile.getAbsolutePath().replace("C:", "") + " OK")
            val dirURL: URL = dirFile.toURI().toURL()
            println("ClassLoader URL: " + dirURL)
            return dirURL
        } else {
            println("File " + fileFile.getAbsolutePath().replace("C:", "") + " NOT OK")
            return null
        }

    } else {
        println("Directory " + dirFile.getAbsolutePath().replace("C:", "") + " NOT OK")
        return null
    }
}
