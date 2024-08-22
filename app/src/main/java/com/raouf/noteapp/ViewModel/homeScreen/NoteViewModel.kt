package com.raouf.noteapp.ViewModel.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raouf.noteapp.Data.Local.Note
import com.raouf.noteapp.Data.Local.NoteDao
import com.raouf.noteapp.Data.Local.Sort
import com.raouf.noteapp.ViewModel.homeScreen.NoteEvent
import com.raouf.noteapp.ViewModel.homeScreen.NoteState
import dagger.hilt.android.lifecycle.HiltViewModel
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
        state.copy (
            noteList = notelist,
            Sort = sortType,
        )
    }.stateIn(viewModelScope , SharingStarted.WhileSubscribed(5000), NoteState())


    fun onEvent(event: NoteEvent){
        when (event){

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
            is NoteEvent.SortType -> {
                _sortType.value = event.sortType
            }

            NoteEvent.CloseDialog -> {
                _state.update {
                    it.copy(
                        isDeletingNote = false,
                        selectedNote = null
                    )
                }
            }
           is  NoteEvent.OpenDialog -> {
                    _state.update {
                        it.copy(
                            isDeletingNote = true,
                            selectedNote = event.note
                        )
                    }
            }

        }
    }
}

