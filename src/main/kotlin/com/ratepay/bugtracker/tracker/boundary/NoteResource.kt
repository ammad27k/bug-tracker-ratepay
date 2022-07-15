package com.ratepay.bugtracker.tracker.boundary

import com.ratepay.bugtracker.tracker.entity.dto.NoteRequest
import com.ratepay.bugtracker.tracker.entity.dto.NoteResponse
import com.ratepay.bugtracker.tracker.service.NoteService
import com.ratepay.bugtracker.web.APIResult
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigInteger


@RestController
@RequestMapping("/api/bug-tracker")
class NoteResource(private val noteService: NoteService) {
    @RequestMapping("/note/{bugId}")
    fun createNote(@RequestBody noteRequest: NoteRequest,
                   @PathVariable("bugId") bugId : BigInteger) : APIResult<NoteResponse> {
        val user = SecurityContextHolder.getContext().authentication.principal as User
        return APIResult(true, "note created successfully",
            noteService.createNote(noteRequest, bugId, user.username))
    }
}