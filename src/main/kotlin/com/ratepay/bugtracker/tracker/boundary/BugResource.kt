package com.ratepay.bugtracker.tracker.boundary

import com.ratepay.bugtracker.tracker.entity.dto.BugRequest
import com.ratepay.bugtracker.tracker.entity.dto.BugResponse
import com.ratepay.bugtracker.tracker.service.BugService
import com.ratepay.bugtracker.web.APIResult
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigInteger

@RestController
@RequestMapping("/api/bug-tracker")
class BugResource(private val bugService: BugService) {

    @PostMapping("/bug/{projectId}")
    fun createBug(@RequestBody bugRequest: BugRequest,
                  @PathVariable("projectId") projectId : BigInteger) : APIResult<BugResponse> {
        val user = SecurityContextHolder.getContext().authentication.principal as User
        return APIResult(true, "bug created successfully",
            bugService.createBug(bugRequest, user.username, projectId))
    }
}