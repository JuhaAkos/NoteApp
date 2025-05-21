package com.example.baseapp3.notes_features.present.notes.handle_note

import java.text.Format
import java.text.SimpleDateFormat
import java.util.Date


fun convertTime(
    timeData: Long
): String {
    val date: Date = Date(timeData);
    val format: Format = SimpleDateFormat("yyyy MM dd HH:mm");
    return format.format(date);
}