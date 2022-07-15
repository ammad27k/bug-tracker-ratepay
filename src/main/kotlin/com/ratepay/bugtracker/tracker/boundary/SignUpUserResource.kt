package com.ratepay.bugtracker.tracker.boundary

import com.ratepay.bugtracker.tracker.entity.dto.SignUpRequest
import com.ratepay.bugtracker.tracker.entity.dto.SignUpResponse
import com.ratepay.bugtracker.tracker.service.SignUpService
import com.ratepay.bugtracker.web.APIResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/signup")
class SignUpUserResource(private val signUpService: SignUpService) {

    @PostMapping(value = ["/user"])
    fun signup(@RequestBody req: SignUpRequest) : APIResult<SignUpResponse> {
        return APIResult(success = true, message = "Signup successfully completed",
            data = signUpService.signUp(req))
    }
}