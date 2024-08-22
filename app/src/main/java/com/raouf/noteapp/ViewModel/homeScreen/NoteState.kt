package com.raouf.noteapp.ViewModel.homeScreen

import androidx.compose.ui.graphics.Color
import com.raouf.noteapp.Data.Local.Note
import com.raouf.noteapp.Data.Local.NoteType
import com.raouf.noteapp.Data.Local.Sort
import com.raouf.noteapp.ui.theme.green



data class NoteState(
    val noteList : List<Note> = emptyList(),
    val isDeletingNote : Boolean =false,
    var Sort : Sort = com.raouf.noteapp.Data.Local.Sort.All,
    val selectedNote : Note? = null
)
