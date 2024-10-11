package com.dicoding.dicodingevent.ui.detail

import androidx.lifecycle.*
import com.dicoding.dicodingevent.data.response.*
import com.dicoding.dicodingevent.data.retrofit.ApiConfig
import retrofit2.*

class DetailViewModel : ViewModel() {
    private val _detailEvent = MutableLiveData<ListEventsItem?>()
    val detailEvent: LiveData<ListEventsItem?> = _detailEvent

    fun getDetailEvent(idEvent: Int) {
        val client = ApiConfig.getApiService().getEvent(id = idEvent.toString())
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    _detailEvent.value =
                        response.body()?.listEvents?.firstOrNull { it?.id == idEvent }
                } else {
                    _detailEvent.value = null
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _detailEvent.value = null
            }
        })
    }
}