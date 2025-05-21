package com.example.baseapp3.notes_features.present.notes

import com.example.baseapp3.notes_features.domain.model.Note
import com.example.baseapp3.notes_features.domain.util.NoteOrder

sealed class NotesEvent {
    //events for radiobutton logic
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
}