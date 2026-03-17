package com.julianogrady.sample.core

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform