package com.devmasterteam.tasks.service.repository

import android.content.Context
import com.devmasterteam.tasks.R
import com.devmasterteam.tasks.service.constants.TaskConstants
import com.devmasterteam.tasks.service.listener.APIListener
import com.devmasterteam.tasks.service.model.PersonModel
import com.devmasterteam.tasks.service.model.PriorityModel
import com.devmasterteam.tasks.service.repository.local.TaskDatabase
import com.devmasterteam.tasks.service.repository.remote.PriorityService
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PriorityRepository(context: Context) : BaseRepository(context) {

    private val remote = RetrofitClient.getRetrofitService(PriorityService::class.java)
    private val database = TaskDatabase.getDatabase(context).priorityDAO()
    //Cache
    companion object {
        private val cache = mutableMapOf<Int, String>()
        fun getCacheDescription (id: Int): String {
            return cache[id] ?: ""
        }
        fun setCacheDescription (id: Int, description: String) {
            cache[id] = description
        }
    }

    fun getDescription (id: Int): String {
        var description = PriorityRepository.getCacheDescription(id)

        if (description == "") {
            description = database.getDescription(id)
            PriorityRepository.setCacheDescription(id, description)
        }
        return description

    }

    fun list(listener: APIListener<List<PriorityModel>>) {
        val call = remote.list()
        enqueue(call, listener)

    }

    fun list(): List<PriorityModel> { //Without overload means get from DB
        return database.list()
    }


    fun save(data: List<PriorityModel>) {
        database.clear()
        database.save(data)
    }
}