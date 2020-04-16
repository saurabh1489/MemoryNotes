package com.sample.memorynotes.framework.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sample.core.data.Note

@Entity(tableName = "Note")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val content: String,
    @ColumnInfo(name = "creation_date")
    val creationTime: Long,
    @ColumnInfo(name = "update_time")
    val updateTime: Long
) {
    companion object {
        fun fromNote(note: Note) =
            NoteEntity(
                id = note.id,
                title = note.title,
                content = note.content,
                creationTime = note.creationTime,
                updateTime = note.updateTime
            )
    }

    fun toNote() = Note(id, title, content, creationTime, updateTime)
}