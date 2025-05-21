package com.example.baseapp3.notes_features.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.baseapp3.ui.theme.Orange
import com.example.baseapp3.ui.theme.Red
import com.example.baseapp3.ui.theme.Blue
import com.example.baseapp3.ui.theme.Green
import com.example.baseapp3.ui.theme.Yellow

@Entity(tableName = "note")
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors = listOf(Orange, Red, Blue, Green, Yellow)
    }
}

class InvalidNoteException(message: String): Exception(message)
