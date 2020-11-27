package com.mykuyademo.network

import com.example.shapecolor.models.RandomColor
import com.example.shapecolor.models.RandomPattern
import retrofit2.http.GET


interface ServicesApi {

    @GET("/api/colors/random?format=json")
    suspend fun getRandomColor(): List<RandomColor>?

    @GET("/api/patterns/random?format=json")
    suspend fun getRandomPattern(): List<RandomPattern>?
}