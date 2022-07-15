package com.ratepay.bugtracker.tracker.repository

import com.ratepay.bugtracker.tracker.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Long> {

}