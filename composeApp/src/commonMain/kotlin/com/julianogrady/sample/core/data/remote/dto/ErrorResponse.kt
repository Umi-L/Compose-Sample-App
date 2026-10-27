package com.julianogrady.sample.core.data.remote.dto

import kotlinx.serialization.Serializable

// the error body a backend returns on a non-2xx status. Field names are
// snake_case to match the JSON exactly; adjust to your own API's shape.
@Serializable
data class ErrorResponse(
    val error: String,
    val error_description: String = "",
)
