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
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val personRepository = PersonRepository(application.applicationContext)

    private val _onLogin = MutableLiveData<ValidationModel>()
    val onLogin: LiveData<ValidationModel> = _onLogin

    private val _loggedUser = MutableLiveData<Boolean>()
    val loggedUser: LiveData<Boolean> = _loggedUser

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
                RetrofitClient.addHeaders(response.token, response.personKey)
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

        val token = securityPreferences.get(TaskConstants.SHARED.TOKEN_KEY)
        val personKey = securityPreferences.get(TaskConstants.SHARED.PERSON_KEY)

        RetrofitClient.addHeaders(token, personKey)

        _loggedUser.value = ((token != "") && (personKey != ""))

    }
}