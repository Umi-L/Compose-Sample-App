package com.julianogrady.sample

import android.app.Application
import com.julianogrady.sample.di.initKoin
import org.koin.android.ext.koin.androidContext

class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()

         initKoin {
             androidContext(this@SampleApplication)
         }
    }
}