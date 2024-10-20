package com.dicoding.dicodingevent

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.dicoding.dicodingevent.databinding.ActivityMainBinding
import com.dicoding.dicodingevent.notif.Reminder
import com.dicoding.dicodingevent.ui.setting.SettingViewModel
import com.dicoding.dicodingevent.ui.setting.SettingViewModelFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settingViewModel = ViewModelProvider(this, SettingViewModelFactory.getInstance(applicationContext))[SettingViewModel::class.java]
        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_favorite, R.id.navigation_setting
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        settingViewModel.getNotificationSetting().observe(this) { isNotificationEnabled ->
            if (isNotificationEnabled) {
                setupDailyReminder()
            }
        }
    }

    private fun setupDailyReminder() {
        val workRequest = PeriodicWorkRequestBuilder<Reminder>(1, TimeUnit.DAYS)
            .build()

        WorkManager.getInstance(this).enqueue(workRequest)
    }
}