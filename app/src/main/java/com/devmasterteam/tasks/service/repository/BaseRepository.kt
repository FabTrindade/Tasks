package com.devmasterteam.tasks.service.repository

import android.content.Context
import android.content.DialogInterface.OnClickListener
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.devmasterteam.tasks.R
import com.devmasterteam.tasks.service.constants.TaskConstants
import com.devmasterteam.tasks.service.listener.APIListener
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class BaseRepository(val context: Context) {

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

    fun <T> enqueue(call: Call<T>, listener: APIListener<T>) {
        call.enqueue(object : Callback<T> {
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

   fun isConnectionAvaleible(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNet = cm.activeNetwork ?: return false
        val networkCapabilities = cm.getNetworkCapabilities(activeNet) ?: return false

        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}
