package com.raouf.noteapp.ViewModel


import androidx.compose.ui.graphics.Color
import com.raouf.noteapp.Data.Local.Note
import com.raouf.noteapp.Data.Local.NoteType
import com.raouf.noteapp.Data.Local.Sort


sealed interface NoteEvent {

    data object SaveNote : NoteEvent
    data class SavaUpdate(val id : Int) : NoteEvent
    data class AddTitle(val title: String) : NoteEvent
    data class AddDescription(val description : String) : NoteEvent
    data class AddType(val type: NoteType) : NoteEvent
    data class AddColor(val color : Color) : NoteEvent
    data class DeleteNote(val note: Note) : NoteEvent
    data class SelectNote(val id : Int) : NoteEvent
    data object openDetail : NoteEvent
    data object closeDetail : NoteEvent
    data class SortType(val sortType : Sort) : NoteEvent
    data object OpenDialog : NoteEvent
    data object CloseDialog : NoteEvent

}