package com.sample.memorynotes.framework.db

import androidx.room.*
import com.sample.core.data.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(noteEntity: NoteEntity)

    @Query("SELECT * FROM Note WHERE id=:id")
    suspend fun getNote(id: Long): NoteEntity?

    @Query("SELECT * FROM Note")
    suspend fun getAllNotes(): List<NoteEntity>

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)

}