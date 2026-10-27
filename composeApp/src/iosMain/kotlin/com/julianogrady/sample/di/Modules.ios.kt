package com.julianogrady.sample.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.julianogrady.sample.AppDatabase
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<SqlDriver> {
            NativeSqliteDriver(AppDatabase.Schema, "sample.db")
        }

        single<HttpClientEngine> { Darwin.create() }
    }
