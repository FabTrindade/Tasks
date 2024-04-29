package com.devmasterteam.tasks.service.repository

import com.devmasterteam.tasks.service.model.PersonModel
import com.devmasterteam.tasks.service.repository.remote.PersonService
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonRepository {

    private val remote  = RetrofitClient.getRetrofitService(PersonService::class.java)

    fun login(email: String, password: String) {

        val call = remote.login(email, password)
        call.enqueue(object: Callback<PersonModel> { //Note: async call!!!
            override fun onResponse(call: Call<PersonModel>, response: Response<PersonModel>) {
                val s = ""
            }

            override fun onFailure(call: Call<PersonModel>, t: Throwable) {
                val s = ""
            }
        })
    }

}