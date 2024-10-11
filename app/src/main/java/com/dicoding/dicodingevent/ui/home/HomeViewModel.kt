package com.dicoding.dicodingevent.ui.home

import androidx.lifecycle.*
import com.dicoding.dicodingevent.data.response.*
import com.dicoding.dicodingevent.data.retrofit.ApiConfig
import retrofit2.*

class HomeViewModel : ViewModel() {

    private val _event = MutableLiveData<List<ListEventsItem>>()
    val event: LiveData<List<ListEventsItem>> = _event

    private val _finish = MutableLiveData<List<ListEventsItem>>()
    val finish: LiveData<List<ListEventsItem>> = _finish

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    companion object {
        private const val EVENT_ID = "1"
        private const val EVENT_ID2 = "0"
    }

    init {
        getEventHome()
        findFinishHome()
    }

    fun getEventHome() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvent(EVENT_ID)

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

    fun findFinishHome() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvent(EVENT_ID2)
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
}