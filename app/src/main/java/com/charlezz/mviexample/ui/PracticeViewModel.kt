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
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel(), ContainerHost<PracticeState, String> {

    override val container: Container<PracticeState, String> = container(PracticeState())

    fun fetchUsers() = intent {
        viewModelScope.launch {
            reduce { state.copy(loading = true) }
            val users = repository.getUsers()
            reduce { state.copy(loading = false, users = users) }
            postSideEffect("${users.size} user(s) loaded")
        }
    }

}