package com.example.baseapp3.notes_features.domain.usecase

import com.example.baseapp3.notes_features.domain.model.Note
import com.example.baseapp3.notes_features.domain.repository.NoteRepository

class DeleteNoteUseCase (
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}