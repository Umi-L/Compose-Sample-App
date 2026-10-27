package com.julianogrady.sample.core

// true when this binary was built in debug/development mode. Backed by
// BuildConfig.DEBUG on Android, Platform.isDebugBinary on iOS, and a
// debugger-attached check on JVM/desktop (see the platform actuals).
expect val isDebugBuild: Boolean
