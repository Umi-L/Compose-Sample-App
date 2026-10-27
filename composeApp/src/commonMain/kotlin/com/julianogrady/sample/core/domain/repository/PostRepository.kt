package com.julianogrady.sample.core.domain.repository

import com.julianogrady.sample.core.domain.model.Post

interface PostRepository {
    suspend fun getPost(id: Int): Post
}
