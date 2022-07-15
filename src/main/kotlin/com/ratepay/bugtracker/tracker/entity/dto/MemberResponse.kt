package com.ratepay.bugtracker.tracker.entity.dto

import java.math.BigInteger

data  class MemberResponse(val members : List<MemberInfo?>? = null)


data class MemberInfo(val id : BigInteger, val name : String)