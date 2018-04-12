package ru.saertis.site.config

import org.springframework.web.WebApplicationInitializer
import org.springframework.web.context.ContextLoaderListener
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.servlet.DispatcherServlet
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer
import javax.servlet.ServletContext
import org.springframework.web.filter.CharacterEncodingFilter
import org.springframework.web.filter.DelegatingFilterProxy


/**
 * Created by Sergey Shushkov on 11.04.2018.
 * Java Team
 */
class ApplicationWebInitializer:
        WebApplicationInitializer {



    override fun onStartup(servletContext: ServletContext?) {
        val applicationContext = AnnotationConfigWebApplicationContext()
        applicationContext.register(SecurityConfig::class.java!!)

        servletContext!!.addListener(ContextLoaderListener(applicationContext))

        val webContext = AnnotationConfigWebApplicationContext()
        webContext.register(ApplicationWebConfig::class.java!!)
        webContext.register(SecurityConfig::class.java!!)

        val encodingFilter = servletContext.addFilter("encoding-filter", CharacterEncodingFilter())
        encodingFilter.setInitParameter("encoding", "UTF-8")
        encodingFilter.setInitParameter("forceEncoding", "true")
        encodingFilter.addMappingForUrlPatterns(null, true, "/*")

        val secFilter = servletContext.addFilter("springSecurityFilterChain", DelegatingFilterProxy())
        secFilter.addMappingForUrlPatterns(null, false, "/*")

        val dispatcherServlet = servletContext.addServlet("spring-dispatcher", DispatcherServlet(webContext))

        dispatcherServlet.setLoadOnStartup(1)
        dispatcherServlet.setAsyncSupported(true)
        dispatcherServlet.addMapping("/*")


        applicationContext.registerShutdownHook()
    }
}