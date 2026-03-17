package com.julianogrady.sample.logger

// AppLogger is a Simple cross-platform way to handle logging
// can be extended to support different log levels, or to log to different places (e.g. file, network, etc.)
expect object AppLogger {
    fun error(tag: String, message: String, throwable: Throwable? = null)
    fun debug(tag: String, message: String)
    fun info(tag: String, message: String)
}