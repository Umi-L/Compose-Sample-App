package com.julianogrady.sample.core.data.repository

import com.julianogrady.sample.core.data.remote.ApiConfig
import com.julianogrady.sample.core.data.remote.bodyOrThrow
import com.julianogrady.sample.core.domain.model.Post
import com.julianogrady.sample.core.domain.repository.PostRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get

// Ktor implementation of PostRepository. The HttpClient is the shared, fully
// configured client from di/Modules.kt (JSON, logging, timeouts), so each
// call only needs a URL. bodyOrThrow() deserializes a 2xx body into the
// return type, or throws ApiException for any other status.
class KtorPostRepository(
    private val client: HttpClient,
) : PostRepository {

    override suspend fun getPost(id: Int): Post =
        client.get("${ApiConfig.baseUrl}/posts/$id").bodyOrThrow()
}
