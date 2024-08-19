package com.raouf.noteapp.ViewModel

import androidx.compose.ui.graphics.Color
import com.raouf.noteapp.Data.Local.NoteType

sealed interface DetailEvent {

    data object SaveNote : DetailEvent
    data class SavaUpdate(val id : Int) : DetailEvent
    data class AddTitle(val title: String) : DetailEvent
    data class AddDescription(val description : String) : DetailEvent
    data class AddType(val type: NoteType) : DetailEvent
    data class AddColor(val color : Color) : DetailEvent
    data class SelectNote(val id : Int) : DetailEvent
}