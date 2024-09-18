package com.charlezz.mviexample.data.repository

import com.charlezz.mviexample.data.api.ApiHelper
import com.charlezz.mviexample.data.model.User
import kotlinx.coroutines.delay
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {
    private var users = mutableListOf<User>()
    private var id = 0

    suspend fun getUsers(): List<User> {
        delay(300L)
        id++
        users.add(User(id + 1, "유저$id", "이메일$id"))
        return users.toList()
    }
}