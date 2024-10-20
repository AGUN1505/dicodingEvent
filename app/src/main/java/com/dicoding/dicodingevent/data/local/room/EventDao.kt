package com.dicoding.dicodingevent.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.dicodingevent.data.local.entity.EventEntity

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteEvent(event: EventEntity)

    @Query("SELECT * FROM event WHERE id = :id")
    fun getFavoriteEvent(id: String): LiveData<EventEntity>

    @Query("DELETE  FROM event WHERE id = :id")
    suspend fun deleteFavoriteEvent(id: String)

    @Query("SELECT * FROM event")
    fun getAllFavoriteEvent(): LiveData<List<EventEntity>>

}