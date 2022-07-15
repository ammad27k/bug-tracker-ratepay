package com.ratepay.bugtracker.tracker.entity

import java.time.ZoneId
import java.time.ZonedDateTime
import javax.persistence.*

@MappedSuperclass
abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long = 0

    open lateinit var createdAt: ZonedDateTime

    open lateinit var updatedAt: ZonedDateTime

    @PrePersist
    fun prePersist() {
        createdAt = ZonedDateTime.now(ZoneId.of("UTC"))
        updatedAt = ZonedDateTime.now(ZoneId.of("UTC"))
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = ZonedDateTime.now(ZoneId.of("UTC"))
    }
}