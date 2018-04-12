package ru.saertis.site.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.security.core.userdetails.User


/**
 * Created by Sergey Shushkov on 12.04.2018.
 * Java Team
 */
@EnableWebSecurity
@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfig(
        @Value("\${security.admin.login}") private val adminPassword: String,
        @Value("\${security.admin.password}") private val adminLogin: String
) : WebSecurityConfigurerAdapter() {

    override fun configure(hppp: HttpSecurity){
        http
                .authorizeRequests()
                    .antMatchers("/static/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login").permitAll()

                    .and()
                .httpBasic()
    }

    @Bean
    @Throws(Exception::class)
    public override fun userDetailsService(): UserDetailsService {
        val manager = InMemoryUserDetailsManager()
        manager.createUser(User.withUsername("user").password("password").roles("ADMIN").build())
        return manager
    }

}