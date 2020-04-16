package com.sample.core.repository

import com.sample.core.data.Note

class NoteRepository(private val noteDataSource: NoteDataSource) {
    suspend fun add(note: Note) = noteDataSource.add(note)
    suspend fun remove(note: Note) = noteDataSource.remove(note)
    suspend fun get(id: Long) = noteDataSource.get(id)
    suspend fun getAll() = noteDataSource.getAll()
}