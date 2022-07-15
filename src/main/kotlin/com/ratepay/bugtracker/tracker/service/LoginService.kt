package com.ratepay.bugtracker.tracker.service

import com.ratepay.bugtracker.common.ErrorCodes
import com.ratepay.bugtracker.common.exception.ApplicationException
import com.ratepay.bugtracker.tracker.entity.dto.LoginRequest
import com.ratepay.bugtracker.tracker.entity.dto.LoginResponse
import com.ratepay.bugtracker.tracker.repository.UserRepository
import com.ratepay.bugtracker.web.jwt.JsonWebTokenService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service

@Service
class LoginService(
    private val userRepository: UserRepository,
    private val authenticationManager: AuthenticationManager,
    private val jsonWebTokenService: JsonWebTokenService) {

    fun login(loginRequest: LoginRequest) : LoginResponse {
        val user = userRepository.findByUserName(loginRequest.userName)
        user?: throw ApplicationException(ErrorCodes.USER_NOT_FOUND, "User not found")

        val authenticate = authenticationManager
            .authenticate(
                 UsernamePasswordAuthenticationToken(
                     loginRequest.userName, loginRequest.password
            )
        )

        authenticate.principal?: throw ApplicationException(ErrorCodes.USER_PASSWORD_INTEGRITY, "Invalid crendentials")
        val principal = authenticate.principal as User
        val token = jsonWebTokenService.generateToken(principal.username)
        return LoginResponse(loginRequest.userName, token)
    }
}