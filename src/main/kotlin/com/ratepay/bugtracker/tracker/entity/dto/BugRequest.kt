package com.ratepay.bugtracker.tracker.entity.dto

import com.ratepay.bugtracker.tracker.entity.Priority

data class BugRequest(
    val title: String,
    val description: String,
    val priority: Priority = Priority.LOW,
    val isResolved : Boolean = false
)
