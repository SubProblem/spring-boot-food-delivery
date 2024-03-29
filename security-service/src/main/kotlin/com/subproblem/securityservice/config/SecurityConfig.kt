package com.subproblem.securityservice.config

import com.subproblem.securityservice.entity.Permissions
import com.subproblem.securityservice.entity.Permissions.USER_READ
import com.subproblem.securityservice.entity.Role
import com.subproblem.securityservice.entity.Role.USER
import com.subproblem.securityservice.jwt.JwtAuthFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val authFilter: JwtAuthFilter,
    private val authenticationProvider: AuthenticationProvider
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/api/v1/auth/**").permitAll()
                    .anyRequest()
                    .authenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }
}