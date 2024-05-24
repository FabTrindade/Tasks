package com.devmasterteam.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.devmasterteam.tasks.service.constants.TaskConstants
import com.devmasterteam.tasks.service.repository.SecurityPreferences

class MainViewModel(application: Application) : AndroidViewModel (application) {
    private var securityPreferences = SecurityPreferences(application.applicationContext)

    fun logout() {
        securityPreferences.remove(TaskConstants.SHARED.PERSON_NAME)
        securityPreferences.remove(TaskConstants.SHARED.PERSON_KEY)
        securityPreferences.remove(TaskConstants.SHARED.TOKEN_KEY)
    }

    fun getUserName(): String {
        return securityPreferences.get(TaskConstants.SHARED.PERSON_NAME)
    }

}