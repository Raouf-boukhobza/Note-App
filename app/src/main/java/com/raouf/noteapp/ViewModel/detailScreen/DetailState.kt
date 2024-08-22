package com.raouf.noteapp.ViewModel.detailScreen

import androidx.compose.ui.graphics.Color
import com.raouf.noteapp.Data.Local.NoteType
import com.raouf.noteapp.ui.theme.green

data class DetailState(
    val title : String = "",
    val description : String = "",
    val color : Color = green,
    var type : NoteType = NoteType.JournalEntry,
    val date : String = "",
)


