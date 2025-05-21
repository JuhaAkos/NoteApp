package com.example.baseapp3.notes_features.domain.usecase

import com.example.baseapp3.notes_features.domain.model.InvalidNoteException
import com.example.baseapp3.notes_features.domain.model.Note
import com.example.baseapp3.notes_features.domain.repository.NoteRepository

class AddNoteUseCase(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if(note.title.isBlank()) {
            throw InvalidNoteException("The title of the note must not be empty.")
        }
        if(note.content.isBlank()) {
            throw InvalidNoteException("The content of the note must not be empty.")
        }
        repository.insertNote(note)
    }
}