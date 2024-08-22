package com.raouf.noteapp.ViewModel.homeScreen


import com.raouf.noteapp.Data.Local.Note
import com.raouf.noteapp.Data.Local.Sort


sealed interface NoteEvent {


    data class DeleteNote(val note: Note) : NoteEvent
    data class SortType(val sortType : Sort) : NoteEvent
    data class OpenDialog(val note: Note) : NoteEvent
    data object CloseDialog : NoteEvent

}