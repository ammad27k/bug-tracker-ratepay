package com.ratepay.bugtracker.tracker.entity.dto

import java.math.BigInteger

data class BugResponse(val bugId : BigInteger,
                       val title : String,
                       val description: String,
                       val isResolved : Boolean,
                       val notes : List<NoteResponse>? = null)