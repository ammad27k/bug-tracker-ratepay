package com.ratepay.bugtracker.web.jwt

import com.ratepay.bugtracker.common.ErrorCodes
import com.ratepay.bugtracker.common.exception.ApplicationException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class JwtRequestFilter(private val jsonWebTokenService: JsonWebTokenService,
                       private val jwtUserDetailsService: JwtUserDetailsService) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val requestTokenHeader : String? = request.getHeader("Authorization")

        if (requestTokenHeader == null || !requestTokenHeader?.startsWith("Bearer ")!!) {
            filterChain.doFilter(request, response);
            return
        }

        val token: String? = requestTokenHeader.takeIf { token -> token.startsWith("Bearer ") }
            ?.substring(7)

        token?: throw ApplicationException(ErrorCodes.INVALID_OR_MISSING_TOKEN, "invalid token")

        val userName = jsonWebTokenService.getUsernameFromToken(token)
        userName?: throw ApplicationException(ErrorCodes.USER_NOT_FOUND, "Invalid User")

        if(SecurityContextHolder.getContext().authentication == null) {
            val userDetails =  jwtUserDetailsService.loadUserByUsername(userName)
            if(jsonWebTokenService.validateToken(token, userDetails) == true) {
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }

        }
        filterChain.doFilter(request, response)
    }
}