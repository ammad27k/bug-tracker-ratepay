package com.ratepay.bugtracker.tracker.repository

import com.ratepay.bugtracker.tracker.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUserName(userName : String) : User?
}