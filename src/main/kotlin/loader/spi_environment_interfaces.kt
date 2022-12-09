//// =====================================================================================
//// SPI. Environment interfaces.
////
//// =====================================================================================
package offtheecliptic.loader.spi.interfaces.env

/// --------------------------------------------------------------------------------------
/// Environment Interface and Default Impl

/**
 * This interface defines the environment in which a component will operate. 
 *
 * The environment consists of the following:
 *    - Configuration
 *    - Registries holding resources that may need to be accessed during instantiation
 *      or initialization, which may consist of
 *        -- Configuration information
 *        -- Domain models
 *        -- Sub-components 
 *    - Additional properties
 */
interface Environment {
    /** Returns the configuration information needed to instantiate and intiialize the instance. */
    val configuration: Configuration
    /** The names of any registries that may contain resource information for initialization. */
    val registryKeys: Set<Any>?
    /** Returns a single registry given its name.  Returns null if a registry does not exist with that name. */
    fun registry(name: String): Registry?
    /** Returns all the environment information as a map. */
    fun asMap(): Map<String,Any?>
}

/**
 * Configuration interface.  This is bare-bones, meant to be extended.
 */
interface Configuration {
    /** The fully-qualified names of the configuration properties. */
    val propertyNames: Set<String>?
    /** Returns a single additional property given its name.  Returns null if the property name does not exist.*/
    fun property(name: String): Any?
    /** Returns all the configuration information as a map. */
    fun asMap(): Map<String,Any?>
}

/**
 * Configuration interface.  This is bare-bones, meant to be extended.
 */
interface Registry {
    /** The fully-qualified names of the confientries in the registryguration properties. */
    val entryKeys: Set<Any>?
    /** Returns a single additional property given its name.  Returns null if the property name does not exist.*/
    fun entry(key: Any): Any?
    /** Returns all the registry information as a map. */
    fun asMap(): Map<Any,Any?>
}