package com.raouf.noteapp.ViewModel

import androidx.compose.ui.graphics.Color
import com.raouf.noteapp.Data.Local.Note
import com.raouf.noteapp.Data.Local.NoteType
import com.raouf.noteapp.Data.Local.Sort
import com.raouf.noteapp.ui.theme.green


data class NoteState(
    val noteList : List<Note> = emptyList(),
    val title: String = "",
    val description : String = "",
    val color : Color = green,
    var type : NoteType = NoteType.JournalEntry,
    val isAddingNote : Boolean = false,
    var Sort : Sort = com.raouf.noteapp.Data.Local.Sort.All
)
