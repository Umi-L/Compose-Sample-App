package com.julianogrady.sample.di

import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import com.julianogrady.sample.AppDatabase
import com.julianogrady.sample.Settings
import com.julianogrady.sample.core.data.repository.SQLDelightSettingsRepository
import com.julianogrady.sample.core.domain.repository.SettingsRepository
import com.julianogrady.sample.home.presentation.homePage.HomePageViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

// expect declaration for the platform-specific module, this is where you can define any platform-specific dependencies
// ie the SQLDelight driver
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

    // bind repositories
    singleOf(::SQLDelightSettingsRepository) bind SettingsRepository::class

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
