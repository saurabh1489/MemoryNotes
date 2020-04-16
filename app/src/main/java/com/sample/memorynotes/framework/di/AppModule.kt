package com.sample.memorynotes.framework.di

import android.app.Application
import com.sample.core.repository.NoteRepository
import com.sample.core.usecase.*
import com.sample.memorynotes.framework.RoomNoteDataSource
import com.sample.memorynotes.framework.UseCases
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideRepository(app: Application) = NoteRepository(RoomNoteDataSource(app))

    @Provides
    fun provideUseCases(repository: NoteRepository) = UseCases(
        AddNote(repository),
        GetAllNotes(repository),
        GetNote(repository),
        RemoveNote(repository),
        GetWordCount()
    )
}