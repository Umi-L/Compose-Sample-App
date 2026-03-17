package com.julianogrady.sample

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.julianogrady.sample.core.App
import com.julianogrady.sample.di.initKoin

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Sample",
    ) {
        initKoin()

        App()
    }
}