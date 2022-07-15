package com.ratepay.bugtracker.tracker.boundary

import com.ratepay.bugtracker.tracker.entity.dto.ProjectRequest
import com.ratepay.bugtracker.tracker.entity.dto.ProjectResponse
import com.ratepay.bugtracker.tracker.service.ProjectService
import com.ratepay.bugtracker.web.APIResult
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*
import java.math.BigInteger


@RestController
@RequestMapping("/api/bug-tracker")
class ProjectResource(private val projectService: ProjectService) {

    @GetMapping("/project/{id}")
    fun getProject(@PathVariable id: BigInteger) : APIResult<ProjectResponse> {
        val user = SecurityContextHolder.getContext().authentication.principal as User
        return APIResult(true, "Project created successfully",
            projectService.getProject(id, user.username))
    }

    @PostMapping("/project")
    fun createProject(@RequestBody projectRequest: ProjectRequest) : APIResult<ProjectResponse> {
        val user = SecurityContextHolder.getContext().authentication.principal as User
        return APIResult(true, "Project info fetched successfully",
            projectService.createProject(projectRequest, user.username))
    }
}