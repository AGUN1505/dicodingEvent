package com.dicoding.dicodingevent.ui.finish

import androidx.lifecycle.*
import com.dicoding.dicodingevent.repository.FinishRepository

class FinishViewModel(private val finishRepository: FinishRepository) : ViewModel() {
    fun findFinish(query: String? = null) = finishRepository.getFinishEvent(query)
    val getFinish = finishRepository.finish
    val getIsLoading = finishRepository.isLoading
    val getErrorMessage = finishRepository.errorMessage
}