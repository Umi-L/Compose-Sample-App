package com.julianogrady.sample.core

import java.lang.management.ManagementFactory

// Desktop/JVM has no Android-style debug/release build variant, so "debug" is
// approximated as "a debugger is attached" (-agentlib:jdwp) — true whenever the
// app is launched via an IDE's Run/Debug button or `./gradlew run --debug-jvm`.
actual val isDebugBuild: Boolean =
    ManagementFactory.getRuntimeMXBean().inputArguments.any { it.contains("jdwp") }
