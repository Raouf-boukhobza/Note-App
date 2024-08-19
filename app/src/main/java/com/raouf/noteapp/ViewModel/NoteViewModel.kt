package com.raouf.noteapp

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raouf.noteapp.Data.Local.Note
import com.raouf.noteapp.Data.Local.NoteDao
import com.raouf.noteapp.Data.Local.NoteType
import com.raouf.noteapp.Data.Local.Sort
import com.raouf.noteapp.ViewModel.NoteEvent
import com.raouf.noteapp.ViewModel.NoteState
import com.raouf.noteapp.ui.theme.pink
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class NoteViewModel @Inject constructor(
    private val dao : NoteDao
) : ViewModel() {

    private val _state = MutableStateFlow(NoteState())
    private val _sortType = MutableStateFlow(Sort.All)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _noteList : StateFlow<List<Note>> = _sortType.flatMapLatest{ sorttype ->
         when(sorttype){
             Sort.All -> dao.selectAllNote()
             else -> dao.selectNoteWithType(sorttype)
         }
    }.stateIn(viewModelScope , SharingStarted.WhileSubscribed() , emptyList())


    val state = combine(_state , _noteList , _sortType){ state, notelist, sortType  ->
        state.copy(
            noteList = notelist,
            Sort = sortType,
        )
    }.stateIn(viewModelScope , SharingStarted.WhileSubscribed(5000),NoteState())


    fun onEvent(event: NoteEvent){
        when(event){
            is NoteEvent.AddDescription ->{
                _state.update {
                    it.copy(
                        description = event.description
                    )
                }
            }

            is NoteEvent.AddTitle -> {
                _state.update {
                    it.copy(
                        title = event.title
                    )
                }
            }

            is NoteEvent.AddType -> {
                _state.update {
                    it.copy(
                        type = event.type
                    )
                }
            }

            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    delay(500L)
                    dao.deleteNote(event.note)
                }
                _state.update {
                    it.copy(
                        isDeletingNote = false
                    )
                }
            }



            NoteEvent.SaveNote -> {
                val title = state.value.title
                val description = state.value.description
                val type = state.value.type
                val color = state.value.color

                val note = Note(
                    title = title,
                    description = description,
                    type = type,
                    color = color.toArgb()
                )
                viewModelScope.launch(Dispatchers.IO){
                    dao.addNote(note)
                }
            }

            is NoteEvent.SavaUpdate-> {
                val title = state.value.title
                val description = state.value.description
                val type = state.value.type
                val color = state.value.color

                val note = Note(
                    id = event.id,
                    title = title,
                    description = description,
                    type = type,
                    color = color.toArgb()
                )

                viewModelScope.launch(Dispatchers.IO){
                    dao.addNote(note)
                }

            }


            NoteEvent.closeDetail -> {
                _state.update {
                    it.copy(
                        title = "",
                        description = "",
                        type = NoteType.JournalEntry,
                    )
                }
            }

            NoteEvent.openDetail -> {
                _state.update{
                    it.copy(
                     title = "",
                     description = "",
                     color = pink
                    )
                }
            }

            is NoteEvent.SortType -> {
                _sortType.value = event.sortType
            }

            is NoteEvent.AddColor -> {
                _state.update {
                    it.copy(
                        color = event.color
                    )
                }
            }

            is NoteEvent.SelectNote -> {
                viewModelScope.launch(Dispatchers.IO){
                     val note =  dao.selectNoteWithId(event.id)
                    withContext(Dispatchers.Main){
                        note.let {
                            _state.update {
                                it.copy(
                                    title = note.title,
                                    description = note.description,
                                    color = Color(note.color),
                                    type = note.type
                                )
                            }

                        }
                    }

                }
            }
            NoteEvent.CloseDialog -> {
                _state.update {
                    it.copy(
                        isDeletingNote = false
                    )
                }
            }
            NoteEvent.OpenDialog -> {
                    _state.update {
                        it.copy(
                            isDeletingNote = true
                        )
                    }
            }
        }
    }

}