package com.sample.core.usecase

import com.sample.core.data.Note
import com.sample.core.repository.NoteRepository

class RemoveNote(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(note: Note) = noteRepository.remove(note)
}