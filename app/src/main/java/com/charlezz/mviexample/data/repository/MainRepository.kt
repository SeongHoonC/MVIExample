package com.charlezz.mviexample.data.repository

import com.charlezz.mviexample.data.api.ApiHelper
import com.charlezz.mviexample.data.model.User
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {

    suspend fun getUsers() = List(5) { User(it + 1, "유저$it", "이메일$it") }

}