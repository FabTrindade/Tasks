package com.devmasterteam.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devmasterteam.tasks.enums.TaskStatus
import com.devmasterteam.tasks.service.constants.TaskConstants
import com.devmasterteam.tasks.service.listener.APIListener
import com.devmasterteam.tasks.service.model.TaskModel
import com.devmasterteam.tasks.service.model.ValidationModel
import com.devmasterteam.tasks.service.repository.PriorityRepository
import com.devmasterteam.tasks.service.repository.TaskRepository

class TaskListViewModel(application: Application) : AndroidViewModel(application) {

    private val taskRepository = TaskRepository(application.applicationContext)
    private val priorityRepository = PriorityRepository(application.applicationContext)

    private val _tasks = MutableLiveData<List<TaskModel>>()
    val tasks: LiveData<List<TaskModel>> = _tasks

    private val _delete = MutableLiveData<ValidationModel>()
    val delete: LiveData<ValidationModel> = _delete

    private val _status = MutableLiveData<ValidationModel>()
    val status: LiveData<ValidationModel> = _status

    private var taskFilter: Int = 0

    fun list(filter: Int) {
        taskFilter = filter
        val listener = object : APIListener<List<TaskModel>> {
            override fun onSuccess(response: List<TaskModel>) {
                response.forEach {
                    it.priorityDescription = priorityRepository.getDescription(it.priorityId)
                }
                _tasks.value = response
            }

            override fun onFailure(message: String) {
            }

        }

        when (filter) {
            TaskConstants.FILTER.ALL -> taskRepository.list(listener)
            TaskConstants.FILTER.NEXT -> taskRepository.listNext(listener)
            TaskConstants.FILTER.EXPIRED -> taskRepository.listOverdue(listener)
        }
    }

    fun delete(id: Int) {
        taskRepository.delete(id, object : APIListener<Boolean> {
            override fun onSuccess(response: Boolean) {
                list(taskFilter)
            }

            override fun onFailure(message: String) {
                _delete.value = ValidationModel (message)
            }


        })
    }

    fun setStatus (id: Int, status: TaskStatus) {
        val listener = object : APIListener<Boolean> {
            override fun onSuccess(response: Boolean) {
                list(taskFilter)
            }
            override fun onFailure(message: String) {
                _status.value = ValidationModel (message)
            }
        }
        when (status) {
            TaskStatus.COMPLETE -> taskRepository.complete (id, listener)

            TaskStatus.UNDO -> taskRepository.undo (id, listener)
        }
    }
}