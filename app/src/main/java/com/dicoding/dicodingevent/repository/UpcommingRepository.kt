package com.dicoding.dicodingevent.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.dicodingevent.data.response.EventResponse
import com.dicoding.dicodingevent.data.response.ListEventsItem
import com.dicoding.dicodingevent.data.retrofit.ApiConfig
import retrofit2.*

class UpcommingRepository private constructor(
) {
    private val _event = MutableLiveData<List<ListEventsItem>>()
    val event: LiveData<List<ListEventsItem>> = _event

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getUpcommingEvent(query: String? = null) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvent("1", query)

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

    companion object {
        @Volatile
        private var INSTANCE: UpcommingRepository? = null

        fun getInstance(
        ): UpcommingRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = UpcommingRepository()
                INSTANCE = instance
                instance
            }
        }
    }
}