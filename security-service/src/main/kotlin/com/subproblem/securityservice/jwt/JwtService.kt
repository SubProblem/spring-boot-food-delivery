package com.subproblem.securityservice.jwt

import com.subproblem.securityservice.config.CustomUserDetailsService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.MacAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException
import java.security.Key
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtService(
    private val customUserDetailsService: CustomUserDetailsService
) {

    @Value("\${jwt.secret}")
    private lateinit var jwtSecret: String
    @Value("\${jwt.duration}")
    private var expiration: Int = 0


    fun generateJwtToken(email: String): String =
        generateToken(email)

    fun extractEmailFromToken(token: String): String? {
        return extractClaim(token) {
            it.subject
        }
    }


    fun generateToken(email: String): String {
        val userDetails = customUserDetailsService.loadUserByUsername(email)
        return Jwts.builder()
            .subject(userDetails.username)
            .issuer("SubProblem")
            .issuedAt(Date())
            .expiration(Date(Date().time + expiration))
            .signWith(getSingInKey())
            .compact()
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(getSingInKey())
            .build()
            .parseSignedClaims(token)
            .payload

    }

    private fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T) : T {
        val claims = extractAllClaims(token)
        return claimsResolver.invoke(claims)
    }


    private fun getSingInKey(): SecretKey {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret))
    }


    fun isTokenValid(token: String, email: String): Boolean {
        val userEmail = extractEmailFromToken(token)
        return userEmail == email && !isTokenExpired(token)
    }


    private fun isTokenExpired(token: String): Boolean =
        extractExpirationDate(token).before(Date())


    private fun extractExpirationDate(token: String): Date {
        return extractClaim(token) {
            it .expiration
        } ?: throw IllegalArgumentException("Expiration date not found in token")
    }
}