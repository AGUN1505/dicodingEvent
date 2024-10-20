package com.dicoding.dicodingevent.ui.detail

import android.util.Log
import androidx.lifecycle.*
import com.dicoding.dicodingevent.data.local.entity.EventEntity
import com.dicoding.dicodingevent.repository.DetailRepository
import com.dicoding.dicodingevent.repository.FavoriteRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    private val detailRepository: DetailRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {
    val detailEvent = detailRepository.detailEvent
    fun getDetailEvent(idEvent: Int) = detailRepository.getDetailEvent(idEvent)
    fun getFavoriteEvent(id: String) = favoriteRepository.getFavoriteEvent(id)

    fun insertFavorite(event: EventEntity) {
        if (event.id.isNotEmpty() && event.name.isNotEmpty()) {
            viewModelScope.launch {
                favoriteRepository.insertFavoriteEvent(event)
            }
        }
    }

    fun deleteFavoriteById(id: String) = viewModelScope.launch {
        Log.d("DetailViewModel", "Attempting to delete favorite")
        favoriteRepository.deleteFavorite(id)
        Log.d("DetailViewModel", "Favorite deleted")
    }

}