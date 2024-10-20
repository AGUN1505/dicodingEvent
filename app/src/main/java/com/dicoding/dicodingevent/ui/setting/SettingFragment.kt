package com.dicoding.dicodingevent.ui.setting

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.dicoding.dicodingevent.R
import com.dicoding.dicodingevent.notif.Reminder
import com.google.android.material.switchmaterial.SwitchMaterial
import java.util.concurrent.TimeUnit

class SettingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val switchTheme = view.findViewById<SwitchMaterial>(R.id.sw_drkmd)
        val switchNotification = view.findViewById<SwitchMaterial>(R.id.sw_ntf)

        val settingViewModel = ViewModelProvider(
            this,
            SettingViewModelFactory.getInstance(requireContext())
        )[SettingViewModel::class.java]
        settingViewModel.getThemeSettings()
            .observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switchTheme.isChecked = false
                }
            }

        switchTheme.setOnCheckedChangeListener { _, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }

        switchNotification.setOnCheckedChangeListener { _, isChecked ->
            settingViewModel.saveNotificationSetting(isChecked)
            if (isChecked) {
                checkAndReqNotifPermission()
                setupDailyReminder(true)
            } else {
                setupDailyReminder(false)
            }
        }

    }

    private fun setupDailyReminder(isEnabled: Boolean) {
        if (isEnabled) {
            val workRequest = PeriodicWorkRequestBuilder<Reminder>(1, TimeUnit.DAYS)
                .build()
            WorkManager.getInstance(requireContext()).enqueue(workRequest)
        } else {
            WorkManager.getInstance(requireContext()).cancelAllWork()
        }
    }

    private fun checkAndReqNotifPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }
        }
    }
}