package com.raouf.noteapp.ViewModel

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raouf.noteapp.Data.Local.Note
import com.raouf.noteapp.Data.Local.NoteDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val dao : NoteDao
)  : ViewModel(){

    private val _state = MutableStateFlow(DetailState())

    val state : StateFlow<DetailState> = _state.asStateFlow()

    fun onEvent(event: DetailEvent){

        when(event){

            is DetailEvent.SelectNote-> {
                viewModelScope.launch(Dispatchers.IO){
                    val note =  dao.selectNoteWithId(event.id)
                    withContext(Dispatchers.Main){
                        note.let {
                            _state.update {
                                it.copy(
                                    title = note.title,
                                    description = note.description,
                                    color = Color(note.color),
                                    type = note.type,
                                    date = note.date
                                )
                            }

                        }
                    }

                }
            }

            is DetailEvent.AddDescription ->{
                _state.update {
                    it.copy(
                        description = event.description
                    )
                }
            }

            is DetailEvent.AddTitle -> {
                _state.update {
                    it.copy(
                        title = event.title
                    )
                }
            }

            is DetailEvent.AddType -> {
                _state.update {
                    it.copy(
                        type = event.type
                    )
                }
            }
            DetailEvent.SaveNote -> {
                val title = state.value.title
                val description = state.value.description
                val type = state.value.type
                val color = state.value.color
                val date = getCurrentTime()

                val note = Note(
                    title = title,
                    description = description,
                    type = type,
                    color = color.toArgb(),
                    date = date

                )
                viewModelScope.launch(Dispatchers.IO){
                    dao.addNote(note)
                }
            }

            is DetailEvent.SavaUpdate-> {
                val title = state.value.title
                val description = state.value.description
                val type = state.value.type
                val color = state.value.color
                val date = getCurrentTime()

                val note = Note(
                    id = event.id,
                    title = title,
                    description = description,
                    type = type,
                    color = color.toArgb(),
                    date = date
                )
                viewModelScope.launch(Dispatchers.IO){
                    dao.addNote(note)
                }
            }

            is DetailEvent.AddColor -> {
                _state.update {
                    it.copy(
                        color = event.color
                    )
                }
            }

        }

    }
    private fun getCurrentTime() : String {
        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return currentTime.format(formatter)
    }


}