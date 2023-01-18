//// =====================================================================================
////
//// =====================================================================================
package offtheecliptic.loader.spi.interfaces.loading

// SPI interfaces
import  offtheecliptic.loader.spi.interfaces.env.Environment

/**
 * Resources can implement this if they are to be dynamically loadable using the
 * classloading utilities.
 */
interface Loadable {
    fun initInstance(environment: Environment)
}
