package com.ratepay.bugtracker.tracker.service

import com.ratepay.bugtracker.common.ErrorCodes
import com.ratepay.bugtracker.common.exception.ApplicationException
import com.ratepay.bugtracker.tracker.entity.dto.SignUpRequest
import com.ratepay.bugtracker.tracker.entity.dto.SignUpResponse
import com.ratepay.bugtracker.tracker.entity.mappers.UserRequestMapper
import com.ratepay.bugtracker.tracker.repository.UserRepository
import com.ratepay.bugtracker.tracker.service.validators.SignUpServiceValidator
import com.ratepay.bugtracker.web.jwt.JsonWebTokenService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class SignUpService(
    private val signUpServiceValidator: SignUpServiceValidator,
    private val userRepository: UserRepository,
    private val passwordEncoder : BCryptPasswordEncoder,
    private val userRequestMapper: UserRequestMapper,
    private val jsonWebTokenService: JsonWebTokenService
) {

    fun signUp(signupRequest: SignUpRequest) : SignUpResponse {
        signUpServiceValidator.validateSigUpUser(signupRequest)

        val existingUser = userRepository.findByUserName(signupRequest.userName)
        existingUser?.let {
            throw ApplicationException(ErrorCodes.USER_ALREADY_EXISTS,
                "Username '${signupRequest.userName}' is already taken.")
        }

        val passwordHash = passwordEncoder.encode(signupRequest.userPassword)
        val newUser = userRequestMapper.formSignUpDtoToUserEntity(signupRequest)
        newUser.userPassword = passwordHash

        userRepository.save(newUser)

        val token = jsonWebTokenService.generateToken(signupRequest.userName)
        return SignUpResponse(signupRequest.userName, token)
    }

}