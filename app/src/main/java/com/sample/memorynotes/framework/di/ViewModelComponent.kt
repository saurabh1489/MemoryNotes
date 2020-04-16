package com.sample.memorynotes.framework.di

import android.app.Application
import com.sample.memorynotes.framework.viewmodel.ListViewModel
import com.sample.memorynotes.framework.viewmodel.NoteViewModel
import dagger.BindsInstance
import dagger.Component

@Component(modules = [AppModule::class])
interface ViewModelComponent {
    fun inject(noteViewModel: NoteViewModel)
    fun inject(listViewModel: ListViewModel)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bind(app: Application): Builder
        fun build(): ViewModelComponent
    }
}