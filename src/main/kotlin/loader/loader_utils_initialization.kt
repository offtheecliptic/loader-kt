//// =====================================================================================
//// Utility. Classloading. Initialization Interfaces and Functions.
////
//// Provides a means of loading and instantiating a class dynamically, using an
//// 'initialize' function.  
////
//// This library provides a simple utility function encapsulating the logic to:
////    1. Dynamically load a class
////    2. Instantiate it
////    3. Initialize it with configuration properties
////
//// In order to do the 3rd item, a resource being loaded must implement the 
//// Loadable interface, which has a single method 'initialize' which takes a
//// single argument of type 'Environment'.
////
//// Note that this set of functions does *not* allow for initializing the 
//// instantiated class through its constructors.  To initialize using the
//// loaded class's constructors, use the following:
////
////            offtheecliptic.utils.classloading.instantiation-utils
//// 
//// To use a singleton factory, which can both be instantiated through its 
//// constructors and intiialize the constructed class using an initialize()
//// method, use the LoadedCClassFactory in the following:
////
////            offtheecliptic.utils.classloading.factory
////
//// ------------------------------------------------------------------------------------
//// Limitations
////   1. Doesn't support specifying a ClassLoader.  Use classloading.factory-utils.
////   2. Doesn't support initialization via constructors. Use classloading.instantiation-utils.
//// ------------------------------------------------------------------------------------
//// Dependencies
////   offtheecliptic.utils.classloading.instantiation-utils.createInstance
////   offtheecliptic.babushka.interfaces.configuration.Configuration
////   offtheecliptic.babushka.interfaces.registry.ResourceRegistry
//// ------------------------------------------------------------------------------------
//// Provenance
////   This is new, as of babushka.
//// =====================================================================================
package offtheecliptic.utils.classloading.initialization

import  offtheecliptic.utils.classloading.instantiation.createInstance

// SPI interfaces
import  offtheecliptic.loader.spi.interfaces.env.Environment
import  offtheecliptic.loader.spi.interfaces.loading.Loadable

/// --------------------------------------------------------------------------------------
/// FUNCTIONS

/**
 * Simple class for instantiating a class with its default no-arg constructore, 
 * and initializing the instance by passing the environment to its initialize()
 * function.
 *
 * The created instance must implement the Loadable interface, so that its
 * initialize(Environment) method can be called.
 */
//fun <T: Loadable> createInstance(className: String, environment: Environment): T {
fun createInstance(className: String, environment: Environment): Loadable {
    
    // Load and instantiate the class
    //val instance = createInstance<T>(className) as T
    val instance = createInstance(className) as Loadable
    
    // Initialize and return the loaded instance
    //instance.initialize(environment)
    instance.initInstance( environment )

    return instance as Loadable
}

/// --------------------------------------------------------------------------------------
/// DynamicResourceFactory

/**
 * Simple class for instantiating a class with its default no-arg constructore, 
 * and initializing the instance by passing the environment to its initialize()
 * function.
 */
// class DynamicResourceFactory {
//     object companion {
//         fun <T: Loadable> createInstance(className: String, type: T, environment: Environment): T {
//             // Load the class using a Java ClassLoader
//             //val clazz: Class = DynamicClassLoader.loadClass(className, type)
//             // Load and instantiate the class
//             val instance: createInstance(className) as T
//             // Initialize and return the loaded instance
//             instance.initialize(environment)
//             return instance
//         }
//     }
// }

