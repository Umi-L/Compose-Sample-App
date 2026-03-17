package com.julianogrady.sample.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.julianogrady.sample.AppDatabase
import org.koin.dsl.module
import java.util.Properties

actual val platformModule: Module
    get() = module {
        single<SqlDriver> {
            JdbcSqliteDriver("jdbc:sqlite:sample.db", Properties(), AppDatabase.Schema)
        }
    }