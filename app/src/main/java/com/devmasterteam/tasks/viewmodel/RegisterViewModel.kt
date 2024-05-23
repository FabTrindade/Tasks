package com.devmasterteam.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devmasterteam.tasks.service.listener.APIListener
import com.devmasterteam.tasks.service.model.PersonModel
import com.devmasterteam.tasks.service.model.ValidationModel
import com.devmasterteam.tasks.service.repository.RegisterRepository

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    val repository = RegisterRepository(application.applicationContext)

    private val _onRegister = MutableLiveData<ValidationModel>()
    val onRegister : LiveData<ValidationModel> = _onRegister
    fun create(name: String, email: String, password: String) {
        repository.create(name, email, password, object: APIListener<PersonModel> {
            override fun onSuccess(response: PersonModel) {
                _onRegister.value = ValidationModel()
            }

            override fun onFailure(message: String) {
                _onRegister.value = ValidationModel(message)
            }
        })
    }

}