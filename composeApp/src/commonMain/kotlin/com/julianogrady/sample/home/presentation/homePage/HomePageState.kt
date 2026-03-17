package com.julianogrady.sample.home.presentation.homePage

import kotlin.time.ExperimentalTime

// the state that is provided to the screen composable,
// should only contain data that is relevant to the ui, and should be immutable.
@OptIn(ExperimentalTime::class)
data class HomePageState(
    val isLoading: Boolean = false,
    val count: Long = 0,
)
