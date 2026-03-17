package com.julianogrady.sample.home.presentation.homePage

import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
sealed interface HomePageAction {
    data class OnIncrementCounter(val amount: Int) : HomePageAction
    object OnResetCounter : HomePageAction
}