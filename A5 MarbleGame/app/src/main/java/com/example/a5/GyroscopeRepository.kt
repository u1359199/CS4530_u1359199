package com.example.a5


import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

data class GravityReading(val x: Float, val y: Float, val z: Float)

//repository and model
class GravityRepository(private val sensorManager: SensorManager) {

    // Create a listener object to register and collect the flow of gravity from the sensor
    fun getGravityFlow(): Flow<GravityReading> = channelFlow {
        val gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        val sensor = gravity ?: sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        if (sensor == null) {
            return@channelFlow
        }

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                trySendBlocking(GravityReading(event.values[0], event.values[1], event.values[2]))
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
        }

        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI)
        awaitClose { sensorManager.unregisterListener(listener) }
    }
}
