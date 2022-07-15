package com.ratepay.bugtracker.tracker.service

import com.ratepay.bugtracker.tracker.entity.Note
import com.ratepay.bugtracker.tracker.entity.dto.NoteRequest
import com.ratepay.bugtracker.tracker.entity.dto.NoteResponse
import com.ratepay.bugtracker.tracker.repository.BugRepository
import com.ratepay.bugtracker.tracker.repository.NoteRepository
import com.ratepay.bugtracker.tracker.repository.UserRepository
import org.springframework.stereotype.Service
import java.math.BigInteger

@Service
class NoteService(private val noteRepository: NoteRepository,
                private val userRepository: UserRepository,
                private val bugRepository: BugRepository) {

    fun createNote(noteRequest: NoteRequest, bugId : BigInteger, userName : String) : NoteResponse {
        val note = Note()
        note.body = noteRequest.body
        note.author = userRepository.findByUserName(userName)
        note.bug = bugRepository.getById(bugId.toLong())

        val newNote = noteRepository.save(note)
        return NoteResponse(newNote.id.toBigInteger(), newNote.body!!)
    }
}