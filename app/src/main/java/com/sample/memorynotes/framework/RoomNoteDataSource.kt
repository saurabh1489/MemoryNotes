package com.sample.memorynotes.framework

import android.content.Context
import com.sample.core.data.Note
import com.sample.core.repository.NoteDataSource
import com.sample.memorynotes.framework.db.DatabaseService
import com.sample.memorynotes.framework.db.NoteEntity

class RoomNoteDataSource(context: Context) : NoteDataSource {
    val noteDao = DatabaseService.getInstance(context).noteDao()

    override suspend fun add(note: Note) = noteDao.addNote(NoteEntity.fromNote(note))

    override suspend fun get(id: Long): Note? = noteDao.getNote(id)?.toNote()

    override suspend fun getAll(): List<Note> = noteDao.getAllNotes().map { it.toNote() }

    override suspend fun remove(note: Note) = noteDao.deleteNote(NoteEntity.fromNote(note))
}