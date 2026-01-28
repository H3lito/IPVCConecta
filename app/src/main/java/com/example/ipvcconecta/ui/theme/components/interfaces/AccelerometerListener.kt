package com.example.ipvcconecta.ui.theme.components.interfaces

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener

class AccelerometerListener(
    private val onMotionChanged: (Boolean) -> Unit
) : SensorEventListener {
    override fun onSensorChanged(event: SensorEvent) {
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]
        val magnitude = kotlin.math.sqrt(x * x + y * y + z * z)
        val isMoving = magnitude > 12f
        onMotionChanged(isMoving) //Comunica com o ViewModel
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}