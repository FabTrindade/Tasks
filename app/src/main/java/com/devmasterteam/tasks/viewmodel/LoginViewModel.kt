package com.devmasterteam.tasks.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devmasterteam.tasks.service.constants.TaskConstants
import com.devmasterteam.tasks.service.listener.APIListener
import com.devmasterteam.tasks.service.model.PersonModel
import com.devmasterteam.tasks.service.model.ValidationModel
import com.devmasterteam.tasks.service.repository.PersonRepository
import com.devmasterteam.tasks.service.repository.SecurityPreferences

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val personRepository = PersonRepository(application.applicationContext)

    private val _onLogin = MutableLiveData<ValidationModel>()
    val onLogin: LiveData<ValidationModel> = _onLogin
    private var securityPreferences = SecurityPreferences(application.applicationContext)

    /**
     * Faz login usando API
     */
    fun doLogin(email: String, password: String) {
        personRepository.login(email, password, object : APIListener<PersonModel> {
            override fun onSuccess(response: PersonModel) {
                securityPreferences.store(TaskConstants.SHARED.PERSON_NAME, response.name)
                securityPreferences.store(TaskConstants.SHARED.PERSON_KEY, response.personKey)
                securityPreferences.store(TaskConstants.SHARED.TOKEN_KEY, response.token)
                _onLogin.value = ValidationModel()
            }

            override fun onFailure(message: String) {
                _onLogin.value = ValidationModel(message)
            }
        })
    }

    /**
     * Verifica se usuário está logado
     */
    fun verifyLoggedUser() {
    }
}