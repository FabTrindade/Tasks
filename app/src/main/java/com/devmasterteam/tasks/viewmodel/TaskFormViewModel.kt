package com.devmasterteam.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devmasterteam.tasks.service.listener.APIListener
import com.devmasterteam.tasks.service.model.PriorityModel
import com.devmasterteam.tasks.service.model.TaskModel
import com.devmasterteam.tasks.service.model.ValidationModel
import com.devmasterteam.tasks.service.repository.PriorityRepository
import com.devmasterteam.tasks.service.repository.TaskRepository

class TaskFormViewModel(application: Application) : AndroidViewModel(application) {
    private val priorityRepository = PriorityRepository(application.applicationContext)
    private val taskRepository = TaskRepository(application.applicationContext)

    private var _priorityList = MutableLiveData<List<PriorityModel>>()
    var priorityList: LiveData<List<PriorityModel>> = _priorityList


    private var _taskCreate = MutableLiveData<ValidationModel>()
    var taskCreate: LiveData<ValidationModel> = _taskCreate

    private var _task = MutableLiveData<TaskModel>()
    var task: LiveData<TaskModel> = _task

    private var _taskLoadError = MutableLiveData<ValidationModel>()
    var taskLoadError: LiveData<ValidationModel> = _taskLoadError


    fun loadPriorities() {
        _priorityList.value = priorityRepository.list()
    }

    fun save(task: TaskModel) {
        taskRepository.create(task, object: APIListener<Boolean>{
            override fun onSuccess(response: Boolean) {
                _taskCreate.value = ValidationModel()
            }

            override fun onFailure(message: String) {
                _taskCreate.value = ValidationModel(message)
            }

        })
    }

    fun loadTask(id: Int) {
        taskRepository.loadTask (id, object: APIListener<TaskModel>{
            override fun onSuccess(response: TaskModel) {
                _task.value = response
            }

            override fun onFailure(message: String) {
                _taskLoadError.value = ValidationModel(message)
            }

        })
    }
}