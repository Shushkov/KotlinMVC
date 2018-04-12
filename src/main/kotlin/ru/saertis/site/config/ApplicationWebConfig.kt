package ru.saertis.site.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.thymeleaf.spring4.SpringTemplateEngine
import org.thymeleaf.spring4.view.ThymeleafViewResolver
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver




/**
 * Created by Sergey Shushkov on 11.04.2018.
 * Java Team
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = arrayOf("ru.saertis.site.controller"))
class ApplicationWebConfig : WebMvcConfigurerAdapter(),
        ApplicationContextAware {

    private var applicationContext: ApplicationContext? = null

    @Autowired
    override fun setApplicationContext(applicationContext:
                                       ApplicationContext?) {
        this.applicationContext = applicationContext
    }


    override fun addResourceHandlers(registry: ResourceHandlerRegistry?) {
        registry!!.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(10)

    }

    @Bean
    fun templateResolver(): ClassLoaderTemplateResolver {
        return ClassLoaderTemplateResolver()
                .apply { prefix = "view/" }
                .apply { suffix = ".html" }
                .apply { templateMode = TemplateMode.HTML }
                .apply { characterEncoding = "UTF-8" }
                .apply { setApplicationContext(applicationContext) }
    }



    @Bean
    fun templateEngine(): SpringTemplateEngine {
        return SpringTemplateEngine()
                .apply { setTemplateResolver(templateResolver()) }
    }

    @Bean
    fun viewResolver(): ThymeleafViewResolver {
        return ThymeleafViewResolver()
                .apply { characterEncoding = "UTF-8" }
                .apply { contentType = "text/html; charset=UTF-8" }
                .apply { templateEngine = templateEngine() }
                .apply { order = 0 }
    }

}