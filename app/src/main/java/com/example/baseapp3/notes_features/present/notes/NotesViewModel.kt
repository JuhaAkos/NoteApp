package com.example.baseapp3.notes_features.present.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseapp3.notes_features.domain.model.Note
import com.example.baseapp3.notes_features.domain.usecase.NoteUseCases
import com.example.baseapp3.notes_features.domain.util.NoteOrder
import com.example.baseapp3.notes_features.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor (
    //get access to the usecases
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var recentlyDeletedNote: Note? = null

    //whats a job?
    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    //upon something rolling in from notesevent
    //when is for all event type
    fun onEvent(event: NotesEvent) {
        when (event) {


            is NotesEvent.Order -> {
                //if noteorder is the same we want to change to -> we do nothing
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }
                getNotes(event.noteOrder)
            }


            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note //save the last note for it to be restorable
                }
            }


            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }


            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }


    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel() //so getnotes doesn't create a new flow every time
        getNotesJob =
        noteUseCases.getNotes(noteOrder) //get notes in a given order //this is a FLOW
            .onEach {
                notes //on each list of notes, NOT each individual note
                ->
                _state.value = state.value.copy( //notestate adatok másolása (a 3. notestate itt nem kell azért no copy)
                    notes = notes,
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)
    }

}