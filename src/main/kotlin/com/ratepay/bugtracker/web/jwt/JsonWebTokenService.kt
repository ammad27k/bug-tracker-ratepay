package com.ratepay.bugtracker.web.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.ZonedDateTime
import java.util.*


@Service
class JsonWebTokenService(
    @Value("jwt.secret:rate-pay") private val secret : String,
) {

    private val JWT_TOKEN_VALIDITY = (5 * 60 * 60).toLong()

    private fun doGenerateToken(claims: Map<String, Any>, subject: String): String {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
            .setExpiration(Date.from(ZonedDateTime.now().toInstant().plusMillis(JWT_TOKEN_VALIDITY * 1000)))
            .signWith(SignatureAlgorithm.HS512, secret).compact()
    }

    fun generateToken(userName : String): String {
        val claims: Map<String, Any> = HashMap()
        return doGenerateToken(claims, userName)
    }

    //retrieve username from jwt token
    fun getUsernameFromToken(token: String?): String? {
        return getClaimFromToken(token, Claims::getSubject)
    }

    //retrieve expiration date from jwt token
    fun getExpirationDateFromToken(token: String?): Date {
        return getClaimFromToken(token, Claims::getExpiration)
    }

    fun <T> getClaimFromToken(token: String?, claimsResolver: java.util.function.Function<Claims, T>): T {
        val claims = getAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    //for retrieving any information from token we will need the secret key
    private fun getAllClaimsFromToken(token: String?): Claims {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
    }

    //check if the token has expired
    private fun isTokenExpired(token: String): Boolean? {
        val expiration = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }
    fun validateToken(token: String?, userDetails: UserDetails): Boolean? {
        val username: String? = getUsernameFromToken(token)
        return username == userDetails.username && !isTokenExpired(token!!)!!
    }

    fun getToken(authorizationHeader : String) : String? {
        return authorizationHeader.takeIf { token -> token.startsWith("Bearer ") }
            ?.substring(7)
    }
}