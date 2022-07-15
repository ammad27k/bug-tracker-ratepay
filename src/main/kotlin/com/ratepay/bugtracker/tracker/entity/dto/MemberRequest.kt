package com.ratepay.bugtracker.tracker.entity.dto

import java.math.BigInteger
import java.time.ZonedDateTime

data class MemberRequest (
    var memberId : BigInteger? = null,
    var projectId : BigInteger? = null,
    var joinedAt : ZonedDateTime? = null,
)