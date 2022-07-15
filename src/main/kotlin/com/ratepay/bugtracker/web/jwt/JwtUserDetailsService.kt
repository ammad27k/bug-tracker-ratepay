package com.ratepay.bugtracker.web.jwt

import com.ratepay.bugtracker.common.ErrorCodes
import com.ratepay.bugtracker.common.exception.ApplicationException
import com.ratepay.bugtracker.tracker.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val existingUser = userRepository.findByUserName(username)
        return existingUser?.let {
            User(it.userName, it.userPassword, mutableListOf())
        }?: throw ApplicationException(
            ErrorCodes.USER_NOT_FOUND,
            "Username '${username}' not found")
    }
}