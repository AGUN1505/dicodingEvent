package com.dicoding.dicodingevent.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.dicodingevent.repository.FavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {
    val getIsLoading = favoriteRepository.isLoading
    fun getAllFavEvent() = favoriteRepository.getAllFavorites()
    fun deleteFavoriteById(id: String) = viewModelScope.launch {
        favoriteRepository.deleteFavorite(id)
    }
}