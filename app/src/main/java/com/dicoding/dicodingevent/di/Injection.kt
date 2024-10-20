package com.dicoding.dicodingevent.di

import android.content.Context
import com.dicoding.dicodingevent.repository.FinishRepository
import com.dicoding.dicodingevent.data.local.room.EventDatabase
import com.dicoding.dicodingevent.repository.DetailRepository
import com.dicoding.dicodingevent.repository.FavoriteRepository
import com.dicoding.dicodingevent.repository.HomeRepository
import com.dicoding.dicodingevent.repository.UpcommingRepository

object Injection {
    fun provideHomeRepository(): HomeRepository {
        return HomeRepository.getInstance()
    }

    fun provideUpcommingRepository(): UpcommingRepository {
        return UpcommingRepository.getInstance()
    }

    fun provideFinishRepository(): FinishRepository {
        return FinishRepository.getInstance()
    }

    fun provideDetailRepository(): DetailRepository {
        return DetailRepository.getInstance()
    }

    fun provideFavoriteRepository(context: Context): FavoriteRepository {
        val database = EventDatabase.getInstance(context)
        val dao = database.eventDao()
        return FavoriteRepository.getInstance(dao)
    }

}