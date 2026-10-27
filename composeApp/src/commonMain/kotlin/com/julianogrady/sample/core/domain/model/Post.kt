package com.julianogrady.sample.core.domain.model

import kotlinx.serialization.Serializable

// a subset of JSONPlaceholder's /posts/{id} response. Fields the API returns
// that aren't declared here (ie userId) are dropped by `ignoreUnknownKeys`.
@Serializable
data class Post(
    val id: Int,
    val title: String,
    val body: String,
)
