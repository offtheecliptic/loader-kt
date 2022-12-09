//// =====================================================================================
//// Utility. Classloading. Instantiation Functions.
////
//// Provides a means of loading and instantiating a class dynamically, using not 
//// just the class's default constructor, but also single-argument constructors 
//// as well as variadic-arg constructors.
////
//// Note that this set of functions does *not* allow for initializing the 
//// instantiated class through a method; it only operates on constructors.
//// To initialize using an initialize method, use the following:
////
////            offtheecliptic.utils.classloading.initialization-utils
////
//// To use a singleton factory, which can both be instantiated through its 
//// constructors and intiialize the constructed class using an initialize()
//// method, use the LoadedCClassFactory in the following:
////
////            offtheecliptic.utils.classloading.factory
////
//// ------------------------------------------------------------------------------------
//// Provenance
////   This is from the Clocca library, now lives in utils-kt library.  See also
////   LoadedClassFactory from the components1 project.
//// References
////   -  https://stackoverflow.com/a/36665249
//// =====================================================================================
//// From the directory this file is in, compile with
////    kotlinc -cp . utils_classloading.kt
//// From the directory this file is in, run with 
////    kotlin offtheecliptic.utils.ClassLoadingUtils
//// =====================================================================================
package offtheecliptic.utils.classloading.instantiation

//import kotlin.reflect.full.createInstance
import  java.lang.reflect.Constructor


/**
 * Instantiate a class with its default constructor from its class name.
 */
//fun <T, ARG_TYPE> createInstance(className: String, arg: ARG_TYPE): T {
fun <T> createInstance(className: String): T {

    return Class.forName(className).newInstance() as T

    // val javaClass = try {
    //     Class.forName(className)
    // } catch(e: Exception) {
    //     throw RuntimeException("Could not load Class from class name '$className'.")
    // }

    // val instance = try {
    //     javaClass.newInstancet() as T
    // } catch (e: Exception) {
    //     throw RuntimeException("Could not instantiate class for class name '$className' and argument '$arg'.")
    // }
    // return instance 
}
    
/**
 * Instantiate a class with a single-argument constructor from its class name and a value
 * for the constructor.
 */
//fun <T, ARG_TYPE> createInstance(className: String, arg: ARG_TYPE): T {
fun <T> createInstance(className: String, arg: Any): T {

    val javaClass = try {
        Class.forName(className)
    } catch(e: Exception) {
        throw RuntimeException("Could not load Class from class name '$className'.")
    }

    // Now get the constructor for the class and instantiate the class from the constructor 
    //val kotlinClass = javaClass::class
    val instance = try {
        // Get the constructor, which may not be publicly visible
        val ctor: Constructor<*>  = javaClass.declaredConstructors.first()
        // In case it is private, make it accessible
        ctor.setAccessible(true)
        // Instantiate it
        newInstance(ctor, arg) as T
    } catch (e: Exception) {
        throw RuntimeException("Could not instantiate class for class name '$className' and argument '$arg'.")
    }
    return instance 
}
    
/**
 * Instantiate a class with a single-argument constructor from its class name and a value
 * for the constructor.
 */
//fun <T, ARG_TYPE> createInstance(className: String, arg: ARG_TYPE): T {
fun <T> createInstanceN(className: String, vararg args: Any): T {

    val javaClass = try {
        Class.forName(className)
    } catch(e: Exception) {
        throw RuntimeException("Could not load Class from class name '$className'.")
    }

    // Now get the constructor for the class and instantiate the class from the constructor 
    //val kotlinClass = javaClass::class
    val operationInstance = try {
        // Get the constructor, which may not be publicly visible
        val ctor: Constructor<*>  = javaClass.declaredConstructors.first()
        // In case it is private, make it accessible
        ctor.setAccessible(true)
        // Instantiate it
        newInstance(ctor, args) as T
    } catch (e: Exception) {
        throw RuntimeException("Could not instantiate class for class name '$className' and arguments '$args'.")
    }
    return operationInstance 
}
    

/**
 * Create an instance of a class using a constructor that takes a single argument.  
 * 
 * For this use case, call with:
 *   createEntity(javaClass.constructors.first(), *constructorArgs)
 *   where javaClass: Class<T>, e.g., MyClass::class.java or Class.forName(className)
 *
 * @param   T   The type of the instance to be created
 * 
 * Reference:
 *   https://stackoverflow.com/a/36665249
 */
private fun <T> newInstance(constructor: Constructor<T>, vararg args: Any) : T {
    return constructor.newInstance(*args)   // Uses 'spread' operator
}


// This one does not work; would have to mess iwth superclasses.
// fun newInstance1(className: String, 
//     operationSetProvider: CloccaOperationSetProvider): CloccaOperationAPI { 
//     if (className.isEmpty()) {
//         throw RuntimeException("Must specify a valid class name.")
//     }
//     //val operationInstance = Class.forName(className).newInstance() as CloccaOperationAPI
//     val providerJavaClass = operationSetProvider::class.java
//     val operationInstance = try {
//         Class.forName(className)
//                 .getConstructor(providerJavaClass)
//                 .newInstance(operationSetProvider) 
//                 as CloccaOperationAPI
//     } catch (e: Exception) {
//         throw RuntimeException("Could not instantiate operation provider '$operationSetProvider' " +
//                                 "for class name '$className' using " +
//                                 "constructor taking '$providerJavaClass' as parameter.")
//     }
//     return operationInstance
// }
