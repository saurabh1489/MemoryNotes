package com.sample.core.usecase

import com.sample.core.data.Note
import com.sample.core.repository.NoteRepository

class AddNote(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(note: Note) = noteRepository.add(note)
}