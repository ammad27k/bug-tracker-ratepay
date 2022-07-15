package com.ratepay.bugtracker.tracker.service

import com.ratepay.bugtracker.tracker.entity.Member
import com.ratepay.bugtracker.tracker.entity.dto.MemberInfo
import com.ratepay.bugtracker.tracker.entity.dto.MemberRequest
import com.ratepay.bugtracker.tracker.entity.dto.MemberResponse
import com.ratepay.bugtracker.tracker.repository.MemberRepository
import com.ratepay.bugtracker.tracker.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class MemberService (private val memberRepository: MemberRepository, private val userRepository: UserRepository){

    fun createMembers(memberRequests: List<MemberRequest>) : MemberResponse {

        val memberRequests = memberRequests.map {
            val member = Member()
            member.memberId= it.memberId
            member.projectId = it.projectId
            member.joinedAt= ZonedDateTime.now()
            member
        }
        val members = memberRepository.saveAll(memberRequests)
        val memberIds = members.map { it.memberId?.toLong() }

        val memberInfo = memberIds.let {
            val user = userRepository.findAllById(it)
            val res = user.map { mem -> MemberInfo(mem.id.toBigInteger(), mem.userName!!) }
            res
        }
        return MemberResponse(memberInfo)
    }
}