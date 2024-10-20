package com.dicoding.dicodingevent.ui.finish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.dicodingevent.di.Injection
import com.dicoding.dicodingevent.repository.FinishRepository

class FinishViewModelFactory(
    private val finishRepository: FinishRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FinishViewModel::class.java)) {
            return FinishViewModel(finishRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: FinishViewModelFactory? = null
        fun getInstance(): FinishViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: FinishViewModelFactory(Injection.provideFinishRepository())
            }.also { instance = it }
    }
}