package com.dicoding.dicodingevent.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.dicodingevent.data.local.entity.EventEntity
import com.dicoding.dicodingevent.data.local.room.EventDao
import kotlinx.coroutines.*

class FavoriteRepository private constructor (
    private val eventDao: EventDao,
) {
    val isLoading = MutableLiveData(true)

    fun getFavoriteEvent(id: String): LiveData<EventEntity> = eventDao.getFavoriteEvent(id)

    suspend fun insertFavoriteEvent(event: EventEntity) {
        withContext(Dispatchers.IO) {
            eventDao.insertFavoriteEvent(event)
        }
    }

    suspend fun deleteFavorite(id: String) {
        withContext(Dispatchers.IO) {
            eventDao.deleteFavoriteEvent(id)
        }
    }

    fun getAllFavorites(): LiveData<List<EventEntity>> = eventDao.getAllFavoriteEvent()

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            eventDao: EventDao,
        ): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(eventDao)
            }.also {
                instance = it
            }
    }
}