package com.ratepay.bugtracker.tracker.entity.dto

data class ProjectResponse(val id : Long, val name : String,
                           val memberList : MemberResponse,
                            val bugs : List<BugResponse>?)
