package com.devmasterteam.tasks.service.repository

import android.content.Context
import android.content.DialogInterface.OnClickListener
import com.devmasterteam.tasks.R
import com.devmasterteam.tasks.service.constants.TaskConstants
import com.devmasterteam.tasks.service.listener.APIListener
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class BaseRepository {

    private fun failResponse(str: String): String {
        return Gson().fromJson(str, String::class.java)
    }

    private fun <T> handleResponse(response: Response<T>, listener: APIListener<T>) {
        if (response.code() == TaskConstants.HTTP.SUCCESS) {
            response.body()?.let { listener.onSuccess(it) }
        } else {
            listener.onFailure(failResponse(response.errorBody()!!.string()))
        }
    }

    fun <T> enqueue (context: Context, call: Call<T>, listener: APIListener<T>) {
        call.enqueue(object: Callback<T> {
            override fun onResponse(
                call: Call<T>,
                response: Response<T>
            ) {
                handleResponse(response, listener)
            }
            override fun onFailure(call: Call<T>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }

        })
    }
}
