package com.ratepay.bugtracker.tracker.entity.mappers

import com.ratepay.bugtracker.tracker.entity.User
import com.ratepay.bugtracker.tracker.entity.dto.SignUpRequest
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
abstract class UserRequestMapper {
    abstract fun formSignUpDtoToUserEntity(signUpRequest: SignUpRequest) : User
}