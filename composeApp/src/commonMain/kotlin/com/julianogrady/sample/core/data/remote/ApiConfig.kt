package com.julianogrady.sample.core.data.remote

import com.julianogrady.sample.BuildKonfig
import com.julianogrady.sample.core.isDebugBuild

// Centralizes the API's base URL so debug builds can talk to each developer's
// own backend and release builds talk to production. DEBUG_BASE_URL comes from
// the gitignored local.properties (key "debug.baseUrl") via BuildKonfig and
// defaults to JSONPlaceholder — see README.md "Configuration".
object ApiConfig {
    private val DEBUG_BASE_URL = BuildKonfig.DEBUG_BASE_URL
    private const val RELEASE_BASE_URL = "https://jsonplaceholder.typicode.com"

    val baseUrl: String = if (isDebugBuild) DEBUG_BASE_URL else RELEASE_BASE_URL
}
