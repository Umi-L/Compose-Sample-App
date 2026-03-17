package com.julianogrady.sample.logger

import java.util.logging.Level
import java.util.logging.Logger

actual object AppLogger {
    private val logger: Logger = Logger.getLogger(AppLogger::class.java.name)

    init {
        logger.level = Level.FINE
    }

    actual fun error(
        tag: String,
        message: String,
        throwable: Throwable?
    ) {
        if (throwable != null) {
            logger.log(Level.SEVERE, "ERROR: [$tag] $message", throwable)
        } else {
            logger.severe("ERROR: [$tag] $message")
        }
    }

    actual fun debug(tag: String, message: String) {
        logger.info("DEBUG: [$tag] $message")
    }

    actual fun info(tag: String, message: String) {
        logger.info("INFO: [$tag] $message")
    }
}