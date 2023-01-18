//// =====================================================================================
//// Example program.
////
//// This demonstrates the use of a post-delegatipon ('local first') classloader.
////
//// Scenario
////    In source root, src/main/kotlin
////      In loader
////        PostDelegationClassLoader.kt [This file is loaded using primary/default classloader]
////      In loader/app (the directory under the project root structure for app package classloader.component)
////        Main.kt (this file) [Classes in this file are loaded using primary/default classloader]
////          main function - instantiates App and calls run() on it
////        App.kt
////          App class 
////          Component class
////          Plugin interface
////    In test root, src/test/kotlin [These are loaded using a post-delegation class loader]
////      In components
////        ComponentImpl.kt 
////      In plugins
////        PluginImpl.kt 
////
//// -------------------------------------------------------------------------------------
//// Using kotlinc:
////    Compile from root of project with
////      classloader> kotlinc -cp "./src/kotlin/main" offtheecliptic.loader.test.basic.Main.kt
//// Run with (if there was a main)
////      classloader> kotlin -cp "build/classes/kotlin/main" offtheecliptic.loader.test.basic.Main
//// -------------------------------------------------------------------------------------
//// Using gradle (much easier/faster):
////    Compile from root of project:
////       classloader> gradle clean build
////    Run with Java or Kotlin:
////       loader> kotlin -cp "build/classes/kotlin/main" offtheecliptic.loader.test.basic.Main
//// =====================================================================================
@file:JvmName("Main")  

package offtheecliptic.loader.test.basic

import offtheecliptic.loader.test.basic.app.*

/// =====================================================================================================
/// MAIN FUNCTION
/// =====================================================================================================

// The application
fun main(args: Array<String>) {    
    println("\n================================================================================")

    val app: App = App()
    app.run()

    println("\n================================================================================")
}

/// =====================================================================================================
/// OLD STUFF THAT WAS IN MAIN
/// =====================================================================================================

    // // Use the initial version of CommonClass in the main application
    // // This version of CommonClass (from this file) is loaded with the default classloader
    // println(">>> Main Component says: " + ComponentImpl().toString())

    // println("\n----- CASE 1 -------------------------------------------------------------------")
    // val extensionRootDir1      = "build/classes/kotlin/test/"
    // val extensionClass1        = "examples.classloader.test.extensions.PluginImpl"
    // //loadExtension("Case 1", extensionRootDir1, extensionClass1)

    // println("\n----- CASE 2 -------------------------------------------------------------------")
    // val extensionRootDir2      = "build/classes/kotlin/test/"
    // val extensionClass2        = "extensions.PluginImpl"
    // loadExtension("Case 2", pluginRootDir2, pluginClass2)

    // println("\n----- CASE 3 -------------------------------------------------------------------")
    // val pluginRootDir3      = "build/classes/kotlin/test/plugins/"
    // val pluginClass3        = "PluginImpl"
    // //loadExtension("Case 3", pluginRootDir3, pluginClass3)

