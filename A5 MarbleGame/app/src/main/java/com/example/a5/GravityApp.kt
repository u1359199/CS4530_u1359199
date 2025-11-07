package com.example.a5

import android.app.Application
import android.content.Context
import android.hardware.SensorManager

// Gravity sensor manager and repository
class GravityApp : Application() {

    val sensorManager: SensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    val gravityRepository by lazy {
        GravityRepository(sensorManager)
    }
}
