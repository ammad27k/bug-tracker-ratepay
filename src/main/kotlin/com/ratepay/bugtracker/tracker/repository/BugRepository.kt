package com.ratepay.bugtracker.tracker.repository

import com.ratepay.bugtracker.tracker.entity.Bug
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BugRepository : JpaRepository<Bug, Long>