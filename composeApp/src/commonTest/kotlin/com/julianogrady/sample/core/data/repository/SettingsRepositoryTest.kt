package com.julianogrady.sample.core.data.repository

import com.julianogrady.sample.core.domain.model.Settings
import com.julianogrady.sample.core.domain.model.ThemeOption
import kotlin.test.Test
import kotlin.test.assertEquals

class SettingsRepositoryTest {

    @Test
    fun testSettingsModel() {
        // Test default settings
        val defaultSettings = Settings()
        assertEquals(ThemeOption.SYSTEM, defaultSettings.theme)
        assertEquals(0L, defaultSettings.counter)

        // Test custom settings
        val customSettings = Settings(theme = ThemeOption.DARK, counter = 5)
        assertEquals(ThemeOption.DARK, customSettings.theme)
        assertEquals(5L, customSettings.counter)
    }

    @Test
    fun testThemeOptionEnum() {
        assertEquals(3, ThemeOption.entries.size)

        ThemeOption.entries.forEach { option ->
            assertEquals(option, ThemeOption.valueOf(option.name))
        }
    }
}
