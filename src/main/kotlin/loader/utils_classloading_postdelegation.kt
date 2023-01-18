//// ====================================================================================================
//// This is a basic post-delegation classloader, which inverts the default class
//// loading behavior in which the parent classloader is delegated to first before 
//// the local classloader is used.  Instead, this classloader uses the local 
//// classloader to load a class first, and only if the class is not found does it 
//// delegate to the parent classloader.
////
//// This implementation is largely based on Reference 1, with the resource-
//// loading considerations from Reference 2.
////
//// References:
////   1. Writing a post-delegation classloader, Alex Miller, 11/09/2006.
////        https://puredanger.github.io/tech.puredanger.com/2006/11/09/classloader/
////   2. Java: A Child-First Classloader, Isuru Weerarathna, 11/08/2018.
////        https://medium.com/@isuru89/java-a-child-first-class-loader-cbd9c3d0305
//// ====================================================================================================

package offtheecliptic.utils.classloading

import kotlin.collections.MutableList
import java.util.LinkedList
import java.util.Enumeration
import java.net.URL
import java.net.URLClassLoader
import java.net.URLStreamHandlerFactory
import java.io.IOException

/** 
 * A post-delegation class loader which inverts the default class loading behavior in
 * which the parent classloader is delegated to first before the local classloader is
 * used.  Instead, this classloader uses the local classloader to load a class first,
 * and only if the class is not found does it delegate to the parent classloader.
 */
internal class PostDelegationClassLoader : URLClassLoader {

    constructor(urls: Array<URL?>?, parent: java.lang.ClassLoader?) 
        : super(urls, parent) {}
    constructor(urls: Array<URL?>?, parent: java.lang.ClassLoader?, factory: URLStreamHandlerFactory?) 
        : super(urls, parent, factory  ) { }
    constructor(urls: Array<URL?>?) 
        : super(urls) {}

    /**
     * Override default behavior which delegates to the parent classloader first and
     * then to the local, and instead checks the local classloader first before it
     * delegates to the parent classloader.
     */
    @Synchronized
    @Throws(ClassNotFoundException::class)
    override fun loadClass(name: String): java.lang.Class<*>? {
        // First check whether it's already been loaded, if so use it
        var loadedClass: java.lang.Class<*>? = findLoadedClass(name)

        // Not loaded, try to load it 
        if (loadedClass == null) {
            try {
                // Ignore parent delegation and just try to load locally
                loadedClass = findClass(name)
            } catch (e: ClassNotFoundException) {
                // Swallow exception - does not exist locally
            }

            // If not found locally, use normal parent delegation in URLClassloader
            if (loadedClass == null) {
                // throws ClassNotFoundException if not found in delegation hierarchy at all
                loadedClass = super.loadClass(name) // or this.getParent().loadClass(name); ?
                //loadedClass = this.getParent().loadClass(name)
            }
        }
        // will never return null (ClassNotFoundException will be thrown)
        return loadedClass
    }

    /** 
     * This enables loading resources using the same class loader as is being
     * used to load the classes.
     */
    @Throws(IOException::class)
    override fun getResources(name: String?): Enumeration<URL?>? {
        val allRes: MutableList<URL> = LinkedList<URL>()

        // load resource from this classloader
        val thisRes: Enumeration<URL> = findResources(name)
        if (thisRes != null) {
            while (thisRes.hasMoreElements()) {
                allRes.add(thisRes.nextElement())
            }
        }

        // then try finding resources from parent classloaders
        val parentRes: Enumeration<URL> = super.findResources(name)
        if (parentRes != null) {
            while (parentRes.hasMoreElements()) {
                allRes.add(parentRes.nextElement())
            }
        }
        return object : Enumeration<URL?> {
            var it: Iterator<URL> = allRes.iterator()
            override fun hasMoreElements(): Boolean {
                return it.hasNext()
            }

            override fun nextElement(): URL {
                return it.next()
            }
        }
    }
    override fun getResource(name: String): URL {
        var url: URL = super.findResource(name)
        if (url == null){
            //url = this.getParent().getResource(name)
            url = super.getResource(name)
        }
        return url
    }  
}