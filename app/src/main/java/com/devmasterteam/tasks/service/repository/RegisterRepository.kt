package com.devmasterteam.tasks.service.repository

import android.content.Context
import com.devmasterteam.tasks.service.listener.APIListener
import com.devmasterteam.tasks.service.model.PersonModel
import com.devmasterteam.tasks.service.repository.remote.PersonService
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient

class RegisterRepository (context: Context) : BaseRepository(context) {
    private val remote = RetrofitClient.getRetrofitService(PersonService::class.java)

    fun create(name: String, email: String, password: String, listener: APIListener<PersonModel>) {
        val call = remote.create(name, email, password)
        enqueue(call, listener)
    }
}