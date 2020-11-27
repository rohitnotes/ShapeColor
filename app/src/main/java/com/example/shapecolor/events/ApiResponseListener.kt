package com.example.shapecolor.events

interface ApiResponseListener<T> {
    fun onSuccess(data: T)
    fun onFailed()
}