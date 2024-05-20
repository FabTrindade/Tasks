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


    private var _taskSave = MutableLiveData<ValidationModel>()
    var taskSave: LiveData<ValidationModel> = _taskSave

    private var _task = MutableLiveData<TaskModel>()
    var task: LiveData<TaskModel> = _task

    private var _taskLoadError = MutableLiveData<ValidationModel>()
    var taskLoadError: LiveData<ValidationModel> = _taskLoadError


    fun loadPriorities() {
        _priorityList.value = priorityRepository.list()
    }

    fun save(task: TaskModel) {

        val listener = object: APIListener<Boolean>{
            override fun onSuccess(response: Boolean) {
                _taskSave.value = ValidationModel()
            }

            override fun onFailure(message: String) {
                _taskSave.value = ValidationModel(message)
            }
        }

        if (task.id == 0) {
            taskRepository.create(task, listener)
        } else {
            taskRepository.update(task, listener)
        }
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