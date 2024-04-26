package com.devmasterteam.tasks.service.repository

import com.devmasterteam.tasks.service.repository.remote.PersonService
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient

class PersonRepository {

    private val remote  = RetrofitClient.getRetrofitService(PersonService::class.java)

    fun login(email: String, password: String) {
        val call = remote.login(email, password)
    }

}