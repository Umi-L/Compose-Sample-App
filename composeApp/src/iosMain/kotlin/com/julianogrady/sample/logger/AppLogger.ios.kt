package com.julianogrady.sample.logger

import platform.Foundation.NSLog

actual object AppLogger {
    actual fun error(tag: String, message: String, throwable: Throwable?) {

        if (throwable != null) {
            NSLog("ERROR: [$tag] $message. Throwable: $throwable CAUSE ${throwable.cause}")
        } else {
            NSLog("ERROR: [$tag] $message")
        }
    }

    actual fun debug(tag: String, message: String) {
        NSLog("DEBUG: [$tag] $message")
    }

    actual fun info(tag: String, message: String) {
        NSLog("INFO: [$tag] $message")
    }

}