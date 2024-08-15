package com.raouf.noteapp.Data.Local

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.raouf.noteapp.ui.theme.green


@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val color : Int,
    val title : String,
    val description : String,
    val type : String
)
