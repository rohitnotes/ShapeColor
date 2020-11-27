package com.mykuyademo.network

class NetworkException(message: String, throwable: Throwable) : Exception(message, throwable){
    var networkCode: Int = 0
    var code: String? = null
    var status: Int =0
    var messageResponse: String =""

}