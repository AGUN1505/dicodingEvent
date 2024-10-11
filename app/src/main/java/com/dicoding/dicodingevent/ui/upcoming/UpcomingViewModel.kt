package com.dicoding.dicodingevent.ui.upcoming

import androidx.lifecycle.*
import com.dicoding.dicodingevent.data.response.*
import com.dicoding.dicodingevent.data.retrofit.ApiConfig
import retrofit2.*

class UpcomingViewModel : ViewModel() {
    private val _event = MutableLiveData<List<ListEventsItem>>()
    val event: LiveData<List<ListEventsItem>> = _event

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    companion object {
        private const val EVENT_ID = "1"
    }

    init {
        getEvent()
    }

    fun getEvent(query: String? = null) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvent(EVENT_ID, query)

        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _event.value = response.body()?.listEvents?.filterNotNull() ?: emptyList()
                } else {
//                    Log.e(TAG, "onFailure: ${response.message()}")
                    _errorMessage.value = "Gagal mendapatkan data: ${response.message()}"
                }
        }
            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
//                Log.e(TAG, "onFailure: ${t.message}")
                _errorMessage.value = "Kesalahan: ${t.message}"
            }
        })
    }
}