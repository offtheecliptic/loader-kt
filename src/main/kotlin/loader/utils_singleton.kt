//// =====================================================================================
//// SingletonContainer.
////
//// This utility can be used to turn a regular class into a singleton.  Usage is 
//// as follows:
////
////    class ExampleSingleton private constructor(arg: ArgClass) {
////        // local variables
////        init {
////            // Initialize local variables using 'arg' argument
////        }
////        fun function1(...): ReturnType { ... }  // This will act like a static function
////        ...                                     // As many functions as you want
////        
////        The class name for the target singleton class must be specified in the generic
////        // The ArgClass for the target singleton class must be specified in the generic
////        // The argument to SingletonContainer can be a 1-arg fn or ref to the singleton
////        companion object : SingletonContainer<ExampleSingleton, ArgClass>(::ExampleSingleton)
////    }
////
//// The singleton may now be invoked using the following syntax; its initialization 
//// will be lazy and thread-safe:
////
////        ExampleSingleton.getInstance(context).doStuff()
//// 
//// Reference:
////   https://bladecoder.medium.com/kotlin-singletons-with-argument-194ef06edd9e
//// -------------------------------------------------------------------------------------
//// Compile with
////    kotlinc -cp . SingletonContainer.kt
//// Run with
////    kotlin -cp ".;some.jar; some-other.jar"  utils.singleton.SingletonContainer
//// =====================================================================================

package offtheecliptic.utils.singleton

open class SingletonContainer<out T: Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile private var instance: T? = null

    fun getInstance(arg: A): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}