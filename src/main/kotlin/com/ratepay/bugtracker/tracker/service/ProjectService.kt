package com.ratepay.bugtracker.tracker.service

import com.ratepay.bugtracker.common.ErrorCodes
import com.ratepay.bugtracker.common.exception.ApplicationException
import com.ratepay.bugtracker.tracker.entity.Project
import com.ratepay.bugtracker.tracker.entity.dto.*
import com.ratepay.bugtracker.tracker.repository.ProjectRepository
import com.ratepay.bugtracker.tracker.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.math.BigInteger

@Service
class ProjectService(private val projectRepository: ProjectRepository,
                     private val userRepository: UserRepository,
                    private val memberService: MemberService) {

    fun getProject(id : BigInteger, userName : String ) : ProjectResponse {
        val memberUser = userRepository.findByUserName(userName)
        val existingProject = projectRepository.findByProjectIdAndMemberId(id, memberUser?.id?.toBigInteger()!!)
        existingProject?: throw ApplicationException(ErrorCodes.PROJECT_NOT_FOUND, "Project not found or you are not member of that project")
        return getProjectResponse(existingProject)
    }

    fun createProject(projectRequest: ProjectRequest, userName : String) : ProjectResponse {

        val project = Project()
        project.name = projectRequest.name
        project.createdBy = userRepository.findByUserName(userName)
        val newProject = projectRepository.save(project)

        val members = projectRequest.memberIds?.map {
            MemberRequest(it, newProject.id.toBigInteger())
        }
        members?.let { memberService.createMembers(members) }

        val existingProject = projectRepository.getById(newProject.id)

        return getProjectResponse(existingProject)
    }

    private fun getProjectResponse(existingProject: Project): ProjectResponse {
        val memberList = existingProject.members?.map {
            it.memberId?.toLong()
        }
        val memberInfo = memberList?.let {
            val user = userRepository.findAllById(it)
            val res = user.map { mem -> MemberInfo(mem.id.toBigInteger(), mem.userName!!) }
            res
        }
        val bugs = existingProject.bugs?.map {
            val notes = it.notes?.map {note -> NoteResponse(note.id.toBigInteger(), note.body!!)}
            BugResponse(it.id.toBigInteger(),it.title!!, it.description!!, it.isResolved!!, notes)
        }
        return ProjectResponse(existingProject.id, existingProject.name!!, MemberResponse(memberInfo), bugs)
    }
}