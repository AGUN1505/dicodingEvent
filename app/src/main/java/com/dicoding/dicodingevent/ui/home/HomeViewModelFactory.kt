package com.dicoding.dicodingevent.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.dicodingevent.di.Injection
import com.dicoding.dicodingevent.repository.HomeRepository

class HomeViewModelFactory(
    private val homeRepository: HomeRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(homeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: HomeViewModelFactory? = null
        fun getInstance(): HomeViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: HomeViewModelFactory(Injection.provideHomeRepository())
            }.also { instance = it }
    }
}