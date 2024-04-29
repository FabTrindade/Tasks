package com.devmasterteam.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.devmasterteam.tasks.service.listener.APIListener
import com.devmasterteam.tasks.service.model.PersonModel
import com.devmasterteam.tasks.service.repository.PersonRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val personRepository = PersonRepository(application.applicationContext)

    /**
     * Faz login usando API
     */
    fun doLogin(email: String, password: String) {
        personRepository.login(email, password, object: APIListener<PersonModel>{
            override fun onSuccess(response: PersonModel) {
                TODO("Not yet implemented")
            }

            override fun onFailure(message: String) {
                TODO("Not yet implemented")
            }
        }
        })
    }

    /**
     * Verifica se usuário está logado
     */
    fun verifyLoggedUser() {
    }

}