package com.example.shapecolor.events

import android.view.View

interface EventTapCallback {
    fun onDoubleClick(v: View?)
    fun onMoving(v: View?)
}