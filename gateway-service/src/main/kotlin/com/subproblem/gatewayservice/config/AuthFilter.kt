package com.subproblem.gatewayservice.config

import com.subproblem.gatewayservice.dto.TokenValidationDTO
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Component
class AuthFilter(private val webClient: WebClient.Builder) : AbstractGatewayFilterFactory<AuthFilter.Config>(Config::class.java) {

    private val permittedEndpoints = setOf(
        "/api/v1/auth/register",
        "/api/v1/auth/login"
    )

    override fun apply(config: AuthFilter.Config?): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val jwtToken = extractToken(exchange.request)

            if (checkEndpoint(exchange.request)) {
                return@GatewayFilter chain.filter(exchange)
            } else {
                if (jwtToken.isEmpty()) {
                    throw RuntimeException("Wrong information")
                }
            }

            webClient.build()
                .post()
                .uri("lb://SECURITY-SERVICE/api/v1/auth/validate?token=${jwtToken.substring(7)}")
                .retrieve()
                .bodyToMono<TokenValidationDTO>()
                .flatMap { dto ->
                    exchange.request
                        .mutate()
                        .header("auth-header-id", dto.id.toString())
                        .header("user-email", dto.email)
                    chain.filter(exchange)
                }

        }
    }

    class Config

    private fun extractToken(request: ServerHttpRequest): String {
        return request.headers.getFirst("Authorization").orEmpty()
    }

    private fun checkEndpoint(request: ServerHttpRequest): Boolean {
        return permittedEndpoints.any {
            request.path.toString().startsWith(it)
        }
    }
}