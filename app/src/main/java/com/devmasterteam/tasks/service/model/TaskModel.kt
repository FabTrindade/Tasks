package com.devmasterteam.tasks.service.model

import com.google.gson.annotations.SerializedName

class TaskModel {
    @SerializedName("Id")
    val id: Int = 0
    @SerializedName("PriorityId")
    val priorityId: Int = 0
    @SerializedName("Description")
    val description: String = ""
    @SerializedName("DueDate")
    val dueDate: String = ""
    @SerializedName("Complete")
    val complete: Boolean = false
}