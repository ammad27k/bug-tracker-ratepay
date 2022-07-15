package com.ratepay.bugtracker.common

import com.ratepay.bugtracker.common.exception.ErrorCode

enum class ErrorCodes : ErrorCode {
    USER_ALREADY_EXISTS,
    USER_NOT_FOUND,
    USER_NAME_INTEGRITY,
    USER_PASSWORD_INTEGRITY,
    INVALID_OR_MISSING_TOKEN,
    PROJECT_NOT_FOUND;
    override fun code(): String = this.name
}
