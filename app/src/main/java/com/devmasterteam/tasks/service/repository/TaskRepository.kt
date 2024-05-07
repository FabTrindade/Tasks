package com.devmasterteam.tasks.service.repository

import android.content.Context
import com.devmasterteam.tasks.R
import com.devmasterteam.tasks.service.constants.TaskConstants
import com.devmasterteam.tasks.service.listener.APIListener
import com.devmasterteam.tasks.service.model.TaskModel
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient
import com.devmasterteam.tasks.service.repository.remote.TaskService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskRepository(val context: Context) : BaseRepository() {

    private val remote = RetrofitClient.getRetrofitService(TaskService::class.java)

    fun list (listener: APIListener<List<TaskModel>>) {
        val call = remote.list()
        enqueue(context, call, listener)
    }

    fun listNext (listener: APIListener<List<TaskModel>>) {
        val call = remote.listNext7Days()
        enqueue(context, call, listener)
    }

    fun listOverdue (listener: APIListener<List<TaskModel>>) {
        val call = remote.listOverdue()
        enqueue(context, call, listener)
    }


    fun create(task: TaskModel, listener: APIListener<Boolean>) {
        val call = remote.create(task.priorityId, task.description, task.dueDate, task.complete)
        enqueue(context, call, listener)
    }
}