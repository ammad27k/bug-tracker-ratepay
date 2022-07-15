package com.ratepay.bugtracker.tracker.repository

import com.ratepay.bugtracker.tracker.entity.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.math.BigInteger

@Repository
interface ProjectRepository : JpaRepository<Project, Long> {


    @Query(nativeQuery = true, value = "SELECT * from projects p " +
            "inner join members m on m.project_id = p.id where p.id = :projectId and m.member_id  = :memberId"
    )
    fun findByProjectIdAndMemberId(
        @Param("projectId") projectId: BigInteger ,
        @Param("memberId") memberId: BigInteger
    ): Project?
}