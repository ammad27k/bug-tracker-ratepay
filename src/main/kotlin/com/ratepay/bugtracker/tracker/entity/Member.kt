package com.ratepay.bugtracker.tracker.entity

import java.math.BigInteger
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "members")
class Member : BaseEntity() {

    @ManyToOne
    @JoinColumn(name = "project_id", insertable = false, updatable = false)
    var project : Project? = null
    @Column(name = "project_id")
    var projectId : BigInteger? = null

    @ManyToOne
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    var member: User? = null
    @Column(name = "member_id")
    var memberId : BigInteger? = null

    @Column(name = "joined_at")
    var joinedAt : ZonedDateTime? = null
}