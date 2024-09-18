package com.charlezz.mviexample.ui.intent

import com.charlezz.mviexample.data.model.User

sealed interface PracticeEvent {
    object Loading : PracticeEvent
    class Loaded(val users: List<User>) : PracticeEvent
}