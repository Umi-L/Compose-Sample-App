package com.julianogrady.sample.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.julianogrady.sample.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<SqlDriver> {
            AndroidSqliteDriver(
                schema = AppDatabase.Schema,
                context = androidContext(),
                name = "sample.db"
            )
        }
    }