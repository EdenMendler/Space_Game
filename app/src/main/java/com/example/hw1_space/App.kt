package com.example.hw1_space

import android.app.Application
import com.example.hw1_space.utilities.SignalManager

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        SignalManager.init(this)
    }
}