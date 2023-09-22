package com.viona.noteapp.feature_note.presentation.notes

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viona.noteapp.feature_note.domain.model.Note
import com.viona.noteapp.feature_note.domain.usecase.NoteUseCase
import com.viona.noteapp.feature_note.domain.util.NoteOrder
import com.viona.noteapp.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase
) : ViewModel() {

    private val _state = mutableStateOf(NoteState())
    val state: State<NoteState> = _state

    private var recentDeletedNote: Note? = null

    private var getNoteJob: Job? = null

    init {

        getNote(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.Order -> {
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType){
                    return
                }
                getNote(event.noteOrder)

            }

            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCase.deleteNote(event.note)
                    recentDeletedNote = event.note
                }

            }

            is NoteEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCase.addNote(recentDeletedNote ?: return@launch)
                    recentDeletedNote = null

                }

            }

            is NoteEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )

            }
        }

    }

    private fun getNote(noteOrder: NoteOrder) {
        getNoteJob?.cancel()

        getNoteJob = noteUseCase.getNotes(noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)

    }

}