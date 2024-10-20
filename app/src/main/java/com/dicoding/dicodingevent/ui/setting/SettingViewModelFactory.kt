package com.dicoding.dicodingevent.ui.setting

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SettingViewModelFactory(
    private val pref: SettingPref

) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: SettingViewModelFactory? = null
        fun getInstance(context: Context): SettingViewModelFactory =
            instance ?: synchronized(this) {
                val pref = SettingPref.getInstance(context.dataStore)
                instance ?: SettingViewModelFactory(pref)
            }.also { instance = it }
    }
}