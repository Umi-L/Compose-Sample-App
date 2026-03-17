package com.julianogrady.sample.home.presentation.homePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julianogrady.sample.core.domain.repository.SettingsRepository
import com.julianogrady.sample.logger.AppLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// view model for the page, should contain all logic for handling events and updating state,
// and should be the only place that updates the state.
class HomePageViewModel(
    private val settingsRepository: SettingsRepository,
): ViewModel() {
    // _state is the mutable version of our state and is only accessible in this file
    private val _state = MutableStateFlow(HomePageState())

    // the state we actually pass around must be immutable.
    val state = _state.asStateFlow()

    // on initalization, we want to load any data we need for the page,
    // in this case we want to load the settings from the repository to get the initial counter value.
    // (note, `init` is called directly after the constructor)
    init {
        load()
    }

    // onAction is the only handler passed from the viewModel to the composable,
    // and is used to handle all events from the composable.
    // This way we can keep all logic in the viewModel and the composable focused on just ui.
    fun onAction(action: HomePageAction) {

        AppLogger.debug(
            tag = "HomePageViewModel",
            message = "OnAction: $action"
        )

        // exhaustive when statement to handle all possible actions, and update the state accordingly.
        when (action) {
            is HomePageAction.OnIncrementCounter -> {
                val newCount = state.value.count + action.amount
                _state.update { it.copy(count = newCount) }

                updateSettingsCounter(newCount)
            }
            HomePageAction.OnResetCounter -> {
                _state.update { it.copy(count = 0) }

                updateSettingsCounter(0L)
            }
        }
    }

    // helpers
    fun updateSettingsCounter(counterValue: Long) {
        viewModelScope.launch {
            runCatching {
                settingsRepository.updateSettings(
                    settings = settingsRepository.getSettings().copy(counter = counterValue)
                )
            }.onFailure {
                AppLogger.error(
                    tag = "HomePageViewModel",
                    message = "Error updating settings",
                    throwable = it,
                )
            }
        }
    }

    // the load function is used to asynchronously load from repositories (ie SQLDelight)
    private fun load() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        runCatching {
            settingsRepository.getSettings()
        }.onSuccess { settings ->

            _state.update {
                it.copy(
                    count = settings.counter,
                    isLoading = false,
                )
            }
        }.onFailure {
            AppLogger.error(
                tag = "HomePageViewModel",
                message = "Error loading settings",
                throwable = it,
            )
        }
    }
}