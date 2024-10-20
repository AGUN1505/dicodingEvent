package com.dicoding.dicodingevent.ui.home

import androidx.lifecycle.*
import com.dicoding.dicodingevent.repository.HomeRepository

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {
    val getEvent = homeRepository.event
    val getFinish = homeRepository.finish
    val getIsLoading = homeRepository.isLoading
    val getErrorMessage = homeRepository.errorMessage
    fun findFinishHome() = homeRepository.getHomeFinishEvent()
    fun getEventHome() = homeRepository.getHomeUpcommingEvent()
}