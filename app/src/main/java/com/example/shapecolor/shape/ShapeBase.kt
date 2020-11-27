package com.example.shapecolor.shape

import android.content.Context
import android.view.View

abstract class ShapeBase(context: Context?) : View(context) {
    abstract fun updateColor(colorHex: String)
}