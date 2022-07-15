package com.ratepay.bugtracker.tracker.boundary

import com.ratepay.bugtracker.tracker.entity.dto.LoginRequest
import com.ratepay.bugtracker.tracker.entity.dto.LoginResponse
import com.ratepay.bugtracker.tracker.service.LoginService
import com.ratepay.bugtracker.web.APIResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/login")
class LoginResource(private val loginService: LoginService) {

    @PostMapping(value = ["/"])
    fun login(@RequestBody loginRequest: LoginRequest) : APIResult<LoginResponse> {
        return APIResult(success = true, message = "Login successfull",
            data = loginService.login(loginRequest))
    }
}

