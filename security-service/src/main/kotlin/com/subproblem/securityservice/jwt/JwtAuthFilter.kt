package com.subproblem.securityservice.jwt

import com.subproblem.securityservice.config.CustomUserDetailsService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Service
import org.springframework.web.filter.OncePerRequestFilter


@Service
class JwtAuthFilter(
    private val jwtService: JwtService,
    private val customUserDetailsService: CustomUserDetailsService
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val authHeader = request.getHeader("Authorization")

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val jwt = authHeader.substring(7)
        val userEmail = jwtService.extractEmailFromToken(jwt)

        if (userEmail != null && SecurityContextHolder.getContext().authentication == null) {

            val userDetails = customUserDetailsService.loadUserByUsername(userEmail)

            if (jwtService.isTokenValid(jwt, userEmail)) {
                val authenticationToken: UsernamePasswordAuthenticationToken =
                    UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.authorities
                    )

                authenticationToken.details =
                    WebAuthenticationDetailsSource()
                        .buildDetails(request)

                SecurityContextHolder.getContext().authentication = authenticationToken
            }
            filterChain.doFilter(request, response)
        }
    }
}