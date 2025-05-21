package com.example.baseapp3.notes_features.domain.usecase

import com.example.baseapp3.notes_features.domain.model.Note
import com.example.baseapp3.notes_features.domain.repository.NoteRepository
import com.example.baseapp3.notes_features.domain.util.NoteOrder
import com.example.baseapp3.notes_features.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesUseCase (
    private val repository: NoteRepository
) {
    operator fun invoke(

        //default ordering of notes
        //options are in util
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)

    ): Flow<List<Note>> {

        //get notes from repo, then map them in order of the given noteorder
        return repository.getNotes().map {

            notes ->

            when(noteOrder.orderType) {


                is OrderType.Ascending -> {
                    when(noteOrder) {
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                        is NoteOrder.Color -> notes.sortedBy { it.color }
                    }
                }


                is OrderType.Descending -> {
                    when(noteOrder) {
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedByDescending { it.timestamp }
                        is NoteOrder.Color -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}