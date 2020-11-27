package com.example.shapecolor.events

interface AccelerometerListener {
    fun onAccelerationChanged(x: Float, y: Float, z: Float)

    fun onShake(force: Float)
}