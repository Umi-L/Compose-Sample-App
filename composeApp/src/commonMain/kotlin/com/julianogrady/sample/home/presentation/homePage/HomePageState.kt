package com.julianogrady.sample.home.presentation.homePage

import com.julianogrady.sample.core.domain.model.Post

// the state that is provided to the screen composable,
// should only contain data that is relevant to the ui, and should be immutable.
data class HomePageState(
    val isLoading: Boolean = false,
    val count: Long = 0,
    // the post fetched over the network with Ktor, null until loaded
    val post: Post? = null,
    val postError: String? = null,
)
