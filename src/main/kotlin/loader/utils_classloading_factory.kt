//// =====================================================================================
//// Utility. Classloading. Factory LoadedClassFactory.
////
//// This factory is a singleton that instantiates an instance of a class from the
//// class name, and an environment; the environment has configuration information 
//// and zero or more resource registries that provide access to resources (such 
//// as additional configurations, models, or sub-components) used by the consuming 
//// component.
////
//// The singleton can be invoked using the following syntax and its initialization 
//// will be lazy and thread-safe:
//// 
////    LoadedClassFactory.getInstance(environment).doMethodInLoadedClass()
////
//// -------------------------------------------------------------------------------------
//// This is a constrained form of a generic factory, in two ways:
////    1. It instantiates a class that has a single argument constructor
////    2. It expects the class to be instantiated to derive from a well-known 
////       interface.
////
//// The well-known interface that classes to be instantiated must derive from is
//// Loadable, which has a single function taking an Environment argument.
////
////    fun initialize(env: Environment) 
////
//// The Environment interface exposes the configuration inforamtion needed to 
//// instantiate the target class; it exposes the following functions.
////
////     fun configuration(): Configuration
////     fun resourceRegistries(): Map<String,ResourceRegistry>?
////     fun property(name: String): Any?
//// 
//// Configuration:
////
////
//// Resource Registry:
////
//// 
//// -------------------------------------------------------------------------------------
//// Provenance:
////   This was from the components1 project.  See also the classloading utilities from
////   the Clocca library, which now reside in utils-kt.
//// -------------------------------------------------------------------------------------
//// Compile with
////    kotlinc -cp . LoadedClassFactory.kt
//// Run with (if there was a main)
////    kotlin -cp ".;some.jar; some-other.jar"  utils.classloading.LoadedClassFactory
//// =====================================================================================
@file:JvmName("LoadedClassFactoryEx") 

package offtheecliptic.utils.classloading.factory

// SPI interfaces
import  offtheecliptic.loader.spi.interfaces.env.Environment
import  offtheecliptic.loader.spi.interfaces.loading.Loadable

// Utils-kt library, converts a single-arg constructed class to a singleton
import  offtheecliptic.utils.singleton.SingletonContainer

//import utils.map.mergeReduce        ;; Does not work, Kotlin sucks
import  java.util.Properties


/// --------------------------------------------------------------------------------------
/// UTILITY FUNCTIONS

// Tried to load this via import, but that did not work.

fun <K, V> Map<K, V>.mergeReduce(other: Map<K, V>, reduce: (V, V) -> V = { a, b -> b }): Map<K, V> =
    other.entries.fold(this.toMutableMap()) { acc, entry ->
        acc[entry.key] = acc[entry.key]?.let { reduce(entry.value, it) } ?: entry.value
        acc
    }

/// --------------------------------------------------------------------------------------
/// FACTORY

/**
 * This factory is a singleton that instantiates an instance of a class from the
 * class name, and an environment; the environment has configuration information 
 * and zero or more resource registries that provide access to resources (such 
 * as additional configurations, models, or sub-components) used by the consuming 
 * component.
 *
 * The singleton can be invoked using the following syntax and its initialization 
 * will be lazy and thread-safe:
 *
 *      LoadedClassFactory.getInstance(environment).doMethodInLoadedClass()
 */
class LoadedClassFactory private constructor(environment: Environment) {
    val env: Environment = environment

    /**
     * Instantiates a class and initializes it with the provided properties.
     */
    fun createInstance(className: String, 
                       configProperties: Map<String,Any>,
                       userOverrideProperties: Map<String,Any>,
                       classLoader: ClassLoader): Loadable {

        val instance = newInstance(className, classLoader) as Loadable
        
        // Merge the configuration properties and any override properties defined in the config
        val mergedProps = userOverrideProperties.let { 
            if (!it.isEmpty()) {
                val overrides = it 
                configProperties.mergeReduce(overrides)
            } else {
                configProperties 
            }
        }

        println("Calling comp.configure: " + mergedProps)
        
        // The implementation is responsible for initializing itself
        instance.initInstance( env )
        
        return instance
    }

   /**
     * Instantiates a class with the default classloader.
     */
    fun createInstance(className: String): Loadable {

        val instance = newInstance(className, null) as Loadable
        println("Calling comp.init...")

        // The implementation is responsible for initializing itself
        instance.initInstance( env )
      
        return instance
    }
        
    /**
     * Function that uses the designated classloader, or the default classloader,
     * to instantiate an instance of the named class.
     * 
     * Inputs:
     *      className   The name of the class to create an instance of.
     *      classLoader The classloader to use to resolve the class; if null, the
     *                  default classloader is used.
     * Output:
     *      New instance of the named class, in tne designated classloader.
     * Throws:
     *      RuntimeException if any problem occurs.
     */
    fun newInstance(className: String, classLoader: ClassLoader?): Any {
        
        var theClass: Class<*>
        var instance: Any

        try
        {
            theClass = 
                if (classLoader != null)
                    Class.forName(className, true, classLoader) // as Class<out ExtensionInterface>
                else
                    Class.forName(className) // as Class<out ExtensionInterface>

            instance = theClass.getDeclaredConstructor().newInstance()
            println("*** Factory: object instantiated, class ${instance::class.java.simpleName}")
        }
        catch (e: Exception) {
            throw RuntimeException(
                "Error instantiating class '" + className + "': " + e.message, e)
        }
        return instance
    }

    /**
     * Uses the designated classloader, or the default classloader, to load the
     * class with the specified name onto the classpath.
     * 
     * @params
     *      className   The name of the class to create an instance of.
     *      classLoader The classloader to use to resolve the class; if null, the
     *                  default classloader is used.
     * @returns
     *      New instance of the named class, in tne designated classloader.
     * @throws
     *      RuntimeException if any problem occurs.
     */
    fun loadClass(className: String, classLoader: ClassLoader?): Class<*> {
        
        var theClass: Class<*>

        try
        {
            theClass = 
                if (classLoader != null)
                    Class.forName(className, true, classLoader) // as Class<out ExtensionInterface>
                else
                    Class.forName(className) // as Class<out ExtensionInterface>
            println("*** Factory: object instantiated, class ${theClass.simpleName}")
        }
        catch (e: Exception) {
            throw RuntimeException(
                "Error loading class '" + className + "': " + e.message, e)
        }
        return theClass
    }

    // Make this a singleton, which takes a single arg, of type Configuration
    companion object : SingletonContainer<LoadedClassFactory, Environment>(::LoadedClassFactory)
}
