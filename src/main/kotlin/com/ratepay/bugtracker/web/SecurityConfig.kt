package com.ratepay.bugtracker.web

import com.ratepay.bugtracker.web.jwt.JwtAuthenticationEntryPoint
import com.ratepay.bugtracker.web.jwt.JwtRequestFilter
import com.ratepay.bugtracker.web.jwt.JwtUserDetailsService
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Order(1)
@EnableWebSecurity
class SecurityConfig(private val jwtRequestFilter: JwtRequestFilter,
                     private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
                     private val jwtUserDetailsService: JwtUserDetailsService,
                     private val passwordEncoder: BCryptPasswordEncoder
    ): WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http?.csrf()?.disable()
            // dont authenticate this particular request
            ?.authorizeRequests()?.antMatchers( "/api/login/",
                "/api/signup/user")?.permitAll()

                // all other requests need to be authenticated
            ?.and()?.authorizeRequests()?.anyRequest()?.authenticated()?.and()?.
                // make sure we use stateless session; session won't be used to
                // store user's state.
            exceptionHandling()?.authenticationEntryPoint(jwtAuthenticationEntryPoint)?.and()?.sessionManagement()
            ?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http?.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)


       /* http?.sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            ?.and()
            ?.csrf()?.disable()
            ?.cors()
            ?.and()
            ?.exceptionHandling()
            ?.authenticationEntryPoint(jwtAuthenticationEntryPoint)
            ?.and()
            ?.addFilterBefore(
                jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java
            )*/




    }


   /* override fun configure(web: WebSecurity?) {
        web?.ignoring()?.antMatchers(
           "/api/login/",
            "/api/signup/user")
    }*/


    @ConditionalOnMissingBean
    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }

   /* @Autowired
    @Throws(java.lang.Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService<UserDetailsService>(jwtUserDetailsService).passwordEncoder(passwordEncoder)
    }*/


}