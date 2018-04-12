package ru.saertis.site

import org.eclipse.jetty.annotations.AnnotationConfiguration
import org.eclipse.jetty.annotations.ClassInheritanceHandler
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.util.ConcurrentHashSet
import org.eclipse.jetty.webapp.Configuration
import org.eclipse.jetty.webapp.WebAppContext
import org.springframework.web.WebApplicationInitializer
import ru.saertis.site.config.ApplicationWebInitializer

/**
 * Created by Sergey Shushkov on 10.04.2018.
 * Java Team
 */


fun main(args: Array<String>){

    val webAppContext = WebAppContext()
    webAppContext.contextPath = "/"

    webAppContext.configurations = arrayOf<Configuration>(object : AnnotationConfiguration() {
        @Throws(Exception::class)
        override fun preConfigure(context: WebAppContext) {
            val map = AnnotationConfiguration.ClassInheritanceMap()
            map.put(
                    WebApplicationInitializer::class.java.name,
                    object : ConcurrentHashSet<String>() {
                        init {
                            add(ApplicationWebInitializer::class.java!!.getName())
                        }
                    }
            )
            context.setAttribute(AnnotationConfiguration.CLASS_INHERITANCE_MAP, map)
            _classInheritanceHandler = ClassInheritanceHandler(map)
        }
    })

    val server = Server(8080)
    server.handler = webAppContext
    server.start()
    server.join()


}