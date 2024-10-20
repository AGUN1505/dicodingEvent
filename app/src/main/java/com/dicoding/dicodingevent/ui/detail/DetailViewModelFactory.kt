package com.dicoding.dicodingevent.ui.detail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.dicodingevent.di.Injection
import com.dicoding.dicodingevent.repository.DetailRepository
import com.dicoding.dicodingevent.repository.FavoriteRepository

class DetailViewModelFactory(
    private val detailRepository: DetailRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(detailRepository, favoriteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: DetailViewModelFactory? = null
        fun getInstance(context: Context): DetailViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: DetailViewModelFactory(
                    Injection.provideDetailRepository(),
                    Injection.provideFavoriteRepository(context)
                )
            }.also { instance = it }
    }
}