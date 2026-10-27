package com.julianogrady.sample.di

import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import com.julianogrady.sample.AppDatabase
import com.julianogrady.sample.Settings
import com.julianogrady.sample.core.data.repository.KtorPostRepository
import com.julianogrady.sample.core.data.repository.SQLDelightSettingsRepository
import com.julianogrady.sample.core.domain.repository.PostRepository
import com.julianogrady.sample.core.domain.repository.SettingsRepository
import com.julianogrady.sample.home.presentation.homePage.HomePageViewModel
import com.julianogrady.sample.logger.AppLogger
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

// expect declaration for the platform-specific module, this is where you can define any platform-specific dependencies
// ie the SQLDelight driver and the Ktor engine
expect val platformModule: Module

// the shared DI module on all platforms
val sharedModule = module {

    // this single block is used to define the SQLDelight database and
    // relevant column adapters etc
    single {

        // fetch the platform specific driver
        val driver = get<SqlDriver>()

        // init db
        val db = AppDatabase(
            driver,
            // adapter to allow kotlin enum as data type in our DB
            SettingsAdapter = Settings.Adapter(
                themeAdapter = EnumColumnAdapter()
            ),
        )

        // run the settings initialization
        db.appDatabaseQueries.initializeSettingsOrIgnore()

        return@single db
    }

    // the shared, fully configured Ktor client. Each platform's `platformModule`
    // (see Modules.android.kt / Modules.ios.kt / Modules.jvm.kt) supplies the
    // `HttpClientEngine` (OkHttp on Android/JVM, Darwin on iOS) — the same
    // expect/actual split already used above for the SqlDriver.
    single<HttpClient> {
        val engine = get<HttpClientEngine>()

        HttpClient(engine) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    }
                )
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        AppLogger.debug(tag = "Ktor", message = message)
                    }
                }
                level = LogLevel.INFO
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 15_000
                connectTimeoutMillis = 15_000
                socketTimeoutMillis = 15_000
            }
        }
    }

    // bind repositories
    singleOf(::SQLDelightSettingsRepository) bind SettingsRepository::class
    singleOf(::KtorPostRepository) bind PostRepository::class

    // define view models
    viewModelOf(::HomePageViewModel)
    // ... add other view models here
    // viewModelOf(::SetupPageViewModel)
    // viewModelOf(::SettingsPageViewModel)

    // more complex view model definitions can be made, for example
    // this is how you would define a view model that takes parameters (often useful for routing)
    // viewModel {
    //     (envelopeId: Long) ->
    //         InfoPageViewModel(
    //             envelopeId,
    //             get(),
    //             get()
    //         )
    // }
}
