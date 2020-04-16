package com.sample.memorynotes.framework.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sample.core.data.Note
import com.sample.memorynotes.framework.UseCases
import com.sample.memorynotes.framework.di.DaggerViewModelComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListViewModel(application: Application) : AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val notes = MutableLiveData<List<Note>>()

    @Inject
    lateinit var useCases: UseCases

    init {
        DaggerViewModelComponent.builder().bind(application).build().inject(this)
    }

    fun getNotes() = coroutineScope.launch {
        val noteList = useCases.getAllNotes()
        noteList.forEach { it.count = useCases.getWordCount(it) }
        notes.postValue(noteList)
    }

}