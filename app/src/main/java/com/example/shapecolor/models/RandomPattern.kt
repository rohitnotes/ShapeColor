package com.example.shapecolor.models

import java.io.Serializable

class RandomPattern(
    var id: Int,
    var colors: List<String>,
    var imageUrl: String
): Serializable {
}