package com.example.baseapp3.notes_features.present.notes.handle_note

data class NoteTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)