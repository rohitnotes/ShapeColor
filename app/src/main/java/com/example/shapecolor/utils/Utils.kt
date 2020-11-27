package com.example.shapecolor.utils

import android.content.Context
import android.net.ConnectivityManager

class Utils {
    companion object {
        fun isInternetOn(context: Context): Boolean {
            val connMgr =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetworkInfo = connMgr.activeNetworkInfo

            if (activeNetworkInfo != null) { // connected to the internet
                if (activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    return true
                } else if (activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                    // connected to the mobile provider's data plan
                    return true
                }
            }
            return false
        }
    }

}