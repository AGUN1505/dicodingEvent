package com.dicoding.dicodingevent.ui.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.dicodingevent.data.local.entity.EventEntity
import com.dicoding.dicodingevent.repository.DetailRepository
import com.dicoding.dicodingevent.repository.FavoriteRepository
import com.dicoding.dicodingevent.repository.FinishRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val favoriteRepository: FavoriteRepository,
    private val finishRepository: FinishRepository,
    private val detailRepository: DetailRepository
    ) : ViewModel() {
    val getIsLoading = favoriteRepository.isLoading
    fun getAllFavEvent() = favoriteRepository.getAllFavorites()
//    fun deleteFavoriteById(id: String) = viewModelScope.launch {
//        favoriteRepository.deleteFavorite(id)
//    }

    fun findFinish(query: String? = null) = finishRepository.getFinishEvent(query)
    val getFinish = finishRepository.finish
//    val getIsLoading = finishRepository.isLoading
    val getErrorMessage = finishRepository.errorMessage

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

//class FinishViewModel(private val finishRepository: FinishRepository) : ViewModel() {
//    fun findFinish(query: String? = null) = finishRepository.getFinishEvent(query)
//    val getFinish = finishRepository.finish
//    val getIsLoading = finishRepository.isLoading
//    val getErrorMessage = finishRepository.errorMessage
//}