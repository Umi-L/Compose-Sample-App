package com.julianogrady.sample.home.presentation.homePage

import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
data class HomePageState(
    val isLoading: Boolean = false,
    val count: Long = 0,
)
