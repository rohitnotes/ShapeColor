package com.mykuyademo.network

import java.io.Serializable

data class ErrorResponse(val status: Int, val message: String, val code: String?):
    Serializable