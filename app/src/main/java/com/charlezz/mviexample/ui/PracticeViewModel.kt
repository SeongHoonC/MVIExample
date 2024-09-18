package com.charlezz.mviexample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charlezz.mviexample.data.repository.MainRepository
import com.charlezz.mviexample.ui.intent.PracticeEvent
import com.charlezz.mviexample.ui.model.PracticeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val event = Channel<PracticeEvent>()

    val state = event.receiveAsFlow()
        .runningFold(PracticeState(), ::reduceState)
        .stateIn(viewModelScope, SharingStarted.Eagerly, PracticeState())

    private val _sideEffects = Channel<String>()

    val sideEffects = _sideEffects.receiveAsFlow()

    private fun reduceState(current: PracticeState, event: PracticeEvent): PracticeState {
        return when (event) {
            is PracticeEvent.Loading -> current.copy(loading = true)
            is PracticeEvent.Loaded -> current.copy(loading = false, users = event.users)
        }
    }

    fun fetchUsers() {
        viewModelScope.launch {
            event.send(PracticeEvent.Loading)
            val users = repository.getUsers()
            event.send(PracticeEvent.Loaded(users))
            _sideEffects.send("${users.size} user(s) loaded")
        }
    }

}