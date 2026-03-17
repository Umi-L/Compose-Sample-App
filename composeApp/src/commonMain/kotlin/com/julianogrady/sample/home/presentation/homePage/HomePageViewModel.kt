package com.julianogrady.sample.home.presentation.homePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julianogrady.sample.core.domain.repository.SettingsRepository
import com.julianogrady.sample.logger.AppLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomePageViewModel(
    private val settingsRepository: SettingsRepository,
): ViewModel() {
    private val _state = MutableStateFlow(HomePageState())
    val state = _state.asStateFlow()

    init {
        load()
    }

    fun onAction(action: HomePageAction) {

        AppLogger.debug(
            tag = "HomePageViewModel",
            message = "OnAction: $action"
        )

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

    // the load function is used to asynchronously load from repositories (ie sqldelight)
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