//// =====================================================================================
//// Loader.  Primary interfaces defining the component and extensions.
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

package offtheecliptic.loader.spi.interfaces.extend

import offtheecliptic.utils.classloading.PostDelegationClassLoader
import java.net.URL
import java.io.File
import java.io.IOException

/// =====================================================================================================
/// INTERFACE FUNCTIONS
/// =====================================================================================================

public interface Extension {
    fun init(properties: Map<String,String>)
    //fun test(): String
}

// Simple component interface
public interface Component: Extension {
    //override fun test(): String
}

// Simple plugin interface
public interface Plugin: Extension {
    //override fun test(): String
}