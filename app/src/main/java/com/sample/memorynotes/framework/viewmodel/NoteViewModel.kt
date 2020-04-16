package com.sample.memorynotes.framework.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sample.core.data.Note
import com.sample.core.repository.NoteRepository
import com.sample.core.usecase.AddNote
import com.sample.core.usecase.GetAllNotes
import com.sample.core.usecase.GetNote
import com.sample.core.usecase.RemoveNote
import com.sample.memorynotes.framework.RoomNoteDataSource
import com.sample.memorynotes.framework.UseCases
import com.sample.memorynotes.framework.di.DaggerViewModelComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteViewModel(application: Application): AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val repository = NoteRepository(RoomNoteDataSource(application))

    @Inject
    lateinit var useCases: UseCases

    init {
        DaggerViewModelComponent.builder().bind(application).build().inject(this)
    }

    val saved = MutableLiveData<Boolean>()
    val currentNote = MutableLiveData<Note?>()

    fun addNote(note: Note) {
        coroutineScope.launch {
            useCases.addNote(note)
            saved.postValue(true)
        }
    }

    fun getNode(id: Long) {
        coroutineScope.launch {
            val note = useCases.getNote(id)
            currentNote.postValue(note)
        }
    }

    fun deleteNode(note: Note) {
        coroutineScope.launch {
            useCases.removeNote(note)
            saved.postValue(true)
        }
    }

}