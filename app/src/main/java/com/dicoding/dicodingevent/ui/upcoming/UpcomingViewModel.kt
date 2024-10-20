package com.dicoding.dicodingevent.ui.upcoming

import androidx.lifecycle.*
import com.dicoding.dicodingevent.repository.UpcommingRepository

class UpcomingViewModel(private val upcomingRepository: UpcommingRepository) : ViewModel() {
    val getEvent = upcomingRepository.event
    val getIsLoading = upcomingRepository.isLoading
    val getErrorMessage = upcomingRepository.errorMessage
    fun getEvent(query: String? = null) = upcomingRepository.getUpcommingEvent(query)
}