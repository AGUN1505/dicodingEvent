package com.dicoding.dicodingevent.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.dicodingevent.data.response.EventResponse
import com.dicoding.dicodingevent.data.response.ListEventsItem
import com.dicoding.dicodingevent.data.retrofit.ApiConfig
import retrofit2.*

class HomeRepository private constructor() {

    private val _event = MutableLiveData<List<ListEventsItem>>()
    val event: LiveData<List<ListEventsItem>> = _event

    private val _finish = MutableLiveData<List<ListEventsItem>>()
    val finish: LiveData<List<ListEventsItem>> = _finish

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getHomeFinishEvent() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvent("1")

        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _event.value = response.body()?.listEvents?.filterNotNull() ?: emptyList()
                } else {
                    _errorMessage.value = "Gagal mendapatkan data: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Kesalahan: ${t.message}"
            }
        })
    }

    fun getHomeUpcommingEvent() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvent("0")
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _finish.value = response.body()?.listEvents?.filterNotNull() ?: emptyList()
                } else {
                    _errorMessage.value = "Gagal mendapatkan data: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Kesalahan: ${t.message}"
            }
        })
    }

    companion object {
        @Volatile
        private var INSTANCE: HomeRepository? = null

        fun getInstance(
        ): HomeRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = HomeRepository()
                INSTANCE = instance
                instance
            }
        }
    }
}