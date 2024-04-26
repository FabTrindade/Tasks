package com.devmasterteam.tasks.service.model

import com.google.gson.annotations.SerializedName

class PersonModel {

    @SerializedName("token") //Json key
    lateinit var token: String

    @SerializedName("personKey") //Json key
    lateinit var personKey: String

    @SerializedName("name") //Json key
    lateinit var name: String

}