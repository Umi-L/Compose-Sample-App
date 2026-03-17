package com.julianogrady.sample.home.presentation.homePage

// sealed interface HomePageAction is a sealed interface that defines the possible
// actions that can be taken on the HomePage.
//
// This is used to handle user interactions and update the state accordingly.
// By using a sealed interface, we can ensure that all possible actions are handled.
sealed interface HomePageAction {
    data class OnIncrementCounter(val amount: Int) : HomePageAction
    object OnResetCounter : HomePageAction
}