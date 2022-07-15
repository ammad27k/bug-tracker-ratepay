package com.ratepay.bugtracker.tracker.service

import com.ratepay.bugtracker.tracker.entity.Bug
import com.ratepay.bugtracker.tracker.entity.dto.BugRequest
import com.ratepay.bugtracker.tracker.entity.dto.BugResponse
import com.ratepay.bugtracker.tracker.repository.BugRepository
import com.ratepay.bugtracker.tracker.repository.ProjectRepository
import com.ratepay.bugtracker.tracker.repository.UserRepository
import org.springframework.stereotype.Service
import java.math.BigInteger

@Service
class BugService(private val bugRepository: BugRepository,
                private val projectRepository: ProjectRepository,
                private val userRepository: UserRepository) {

    fun createBug(bugRequest: BugRequest, userName : String, projectId : BigInteger) : BugResponse {
        val bug = Bug()
        bug.title = bugRequest.title
        bug.description = bugRequest.description
        bug.priority = bugRequest.priority
        bug.project = projectRepository.getById(projectId.toLong())
        bug.createdBy = userRepository.findByUserName(userName)
        bug.isResolved = bugRequest.isResolved
        bug.updateBy = bug.createdBy
        val newBug = bugRepository.save(bug)
        return BugResponse(newBug.id.toBigInteger(), newBug.title!!, newBug.description!!, newBug.isResolved!!)
    }

}