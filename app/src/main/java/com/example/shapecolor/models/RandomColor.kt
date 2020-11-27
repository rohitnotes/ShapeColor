package com.example.shapecolor.models

import java.io.Serializable

class RandomColor(
    var id: Int,
    var hex: String,
    var imageUrl: String
): Serializable {
}