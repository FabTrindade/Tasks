package com.devmasterteam.tasks.service.repository

import android.content.Context
import com.devmasterteam.tasks.service.repository.remote.PriorityService
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient

class PriorityRepository(val context: Context) {

    val remote = RetrofitClient.getRetrofitService(PriorityService::class.java)

    fun list() {
        val call = remote.list()

    }

}