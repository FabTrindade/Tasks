package com.devmasterteam.tasks.service.repository

import android.content.Context
import android.widget.Toast
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

class TaskRepository(context: Context) : BaseRepository(context) {

    private val remote = RetrofitClient.getRetrofitService(TaskService::class.java)

    fun list (listener: APIListener<List<TaskModel>>) {
        if(!isConnectionAvaleible()){
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call = remote.list()
        enqueue(call, listener)
    }

    fun listNext (listener: APIListener<List<TaskModel>>) {
        if(!isConnectionAvaleible()){
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call = remote.listNext7Days()
        enqueue(call, listener)
    }

    fun listOverdue (listener: APIListener<List<TaskModel>>) {
        if(!isConnectionAvaleible()){
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call = remote.listOverdue()
        enqueue(call, listener)
    }


    fun create(task: TaskModel, listener: APIListener<Boolean>) {
        if(!isConnectionAvaleible()){
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call = remote.create(task.priorityId, task.description, task.dueDate, task.complete)
        enqueue(call, listener)
    }

    fun delete (id: Int, listener: APIListener<Boolean>) {
        if(!isConnectionAvaleible()){
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call = remote.delete(id)
        enqueue(call, listener)
    }

    fun complete (id: Int, listener: APIListener<Boolean>) {
        if(!isConnectionAvaleible()){
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call = remote.complete (id)
        enqueue(call, listener)
    }
    fun undo (id: Int, listener: APIListener<Boolean>) {
        if(!isConnectionAvaleible()){
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call = remote.undo (id)
        enqueue(call, listener)
    }

    fun loadTask(id: Int, listener: APIListener<TaskModel>) {
        if(!isConnectionAvaleible()){
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        enqueue(remote.listById (id), listener)
    }

    fun update(task: TaskModel, listener: APIListener<Boolean>) {
        if(!isConnectionAvaleible()){
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call = remote.update (
            task.id,
            task.priorityId,
            task.description,
            task.dueDate,
            task.complete)
        enqueue(call, listener)
    }
}