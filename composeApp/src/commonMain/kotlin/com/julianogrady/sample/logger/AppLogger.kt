package com.julianogrady.sample.logger

expect object AppLogger {
    fun error(tag: String, message: String, throwable: Throwable? = null)
    fun debug(tag: String, message: String)
    fun info(tag: String, message: String)
}