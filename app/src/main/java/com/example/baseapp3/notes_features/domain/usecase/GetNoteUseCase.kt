package com.example.baseapp3.notes_features.domain.usecase

import com.example.baseapp3.notes_features.domain.model.Note
import com.example.baseapp3.notes_features.domain.repository.NoteRepository

class GetNoteUseCase(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}