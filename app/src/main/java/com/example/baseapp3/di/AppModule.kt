package com.example.baseapp3.di

import android.app.Application
import androidx.room.Room
import com.example.baseapp3.notes_features.data.data_source.NoteDatabase
import com.example.baseapp3.notes_features.data.repository.NoteRepositoryImpl
import com.example.baseapp3.notes_features.domain.repository.NoteRepository
import com.example.baseapp3.notes_features.domain.usecase.AddNoteUseCase
import com.example.baseapp3.notes_features.domain.usecase.DeleteNoteUseCase
import com.example.baseapp3.notes_features.domain.usecase.GetNoteUseCase
import com.example.baseapp3.notes_features.domain.usecase.GetNotesUseCase
import com.example.baseapp3.notes_features.domain.usecase.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotesUseCase(repository),
            deleteNote = DeleteNoteUseCase(repository),
            addNote = AddNoteUseCase(repository),
            getNote = GetNoteUseCase(repository)
        )
    }
}