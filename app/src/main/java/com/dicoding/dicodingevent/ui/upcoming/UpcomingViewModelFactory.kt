package com.dicoding.dicodingevent.ui.upcoming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.dicodingevent.di.Injection
import com.dicoding.dicodingevent.repository.UpcommingRepository

class UpcomingViewModelFactory(
    private val upcomingRepository: UpcommingRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpcomingViewModel::class.java)) {
            return UpcomingViewModel(upcomingRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: UpcomingViewModelFactory? = null
        fun getInstance(): UpcomingViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: UpcomingViewModelFactory(Injection.provideUpcommingRepository())
            }.also { instance = it }
    }
}