package com.julianogrady.sample.core.domain.model

enum class ThemeOption {
    LIGHT, DARK, SYSTEM
}

data class Settings(
    val initialSetupRan: Boolean = false,
    val theme: ThemeOption = ThemeOption.SYSTEM,
    val counter: Long = 0,
)
