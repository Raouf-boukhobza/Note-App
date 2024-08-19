package com.raouf.noteapp.ViewModel


import androidx.compose.ui.graphics.Color
import com.raouf.noteapp.Data.Local.Note
import com.raouf.noteapp.Data.Local.NoteType
import com.raouf.noteapp.Data.Local.Sort


sealed interface NoteEvent {


    data class DeleteNote(val note: Note) : NoteEvent
    data class SortType(val sortType : Sort) : NoteEvent
    data object OpenDialog : NoteEvent
    data object CloseDialog : NoteEvent

}