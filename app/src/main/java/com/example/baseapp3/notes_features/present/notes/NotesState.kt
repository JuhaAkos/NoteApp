package com.example.baseapp3.notes_features.present.notes

import com.example.baseapp3.notes_features.domain.model.Note
import com.example.baseapp3.notes_features.domain.util.NoteOrder
import com.example.baseapp3.notes_features.domain.util.OrderType

//data relevant for the UI
data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending), //noteorder default
    val isOrderSectionVisible: Boolean = false //the extra noteorder menu that we don't show by default
)