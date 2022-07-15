package com.ratepay.bugtracker.tracker.service.validators

import com.ratepay.bugtracker.common.ErrorCodes
import com.ratepay.bugtracker.common.exception.ApplicationException
import com.ratepay.bugtracker.tracker.entity.dto.SignUpRequest
import org.springframework.stereotype.Service

@Service
class SignUpServiceValidator {

    fun validateSigUpUser(signupRequest: SignUpRequest) {
        if(signupRequest.userName.isEmpty() ||
            (signupRequest.userName.length > 20 || signupRequest.userName.length < 3)) {
            throw ApplicationException(ErrorCodes.USER_NAME_INTEGRITY,
                "Minimum length should be 3 and maximum should be 20")
        }

        if(!signupRequest.userName.matches(Regex("^[a-zA-Z0-9-_]*"))) {
            throw ApplicationException(ErrorCodes.USER_NAME_INTEGRITY,
                "Username must have alphanumeric characters only.")
        }

        if(signupRequest.userPassword.length < 6) {
            throw ApplicationException(ErrorCodes.USER_PASSWORD_INTEGRITY,
                "Password must be atleast 6 characters long.")
        }
    }
}