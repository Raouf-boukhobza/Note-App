package com.raouf.noteapp.Data.Local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val color: Int,
    val title: String,
    val description: String,
    val type: NoteType,
    val date : String
)
