package com.julianogrady.sample.core.domain.repository

import com.julianogrady.sample.core.domain.model.Settings

interface SettingsRepository {
    suspend fun getSettings(): Settings
    suspend fun updateSettings(settings: Settings)
}
