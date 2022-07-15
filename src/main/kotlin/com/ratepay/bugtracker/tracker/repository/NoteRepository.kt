package com.ratepay.bugtracker.tracker.repository

import com.ratepay.bugtracker.tracker.entity.Note
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NoteRepository : JpaRepository<Note, Long>