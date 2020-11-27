package com.example.shapecolor

import androidx.lifecycle.ViewModel
import com.example.shapecolor.events.ApiResponseListener
import com.example.shapecolor.models.RandomColor
import com.example.shapecolor.models.RandomPattern
import com.mykuyademo.network.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {
    /**
     * @param listener the callback listener after api completed
     */
    fun getRandomColor(listener: ApiResponseListener<List<RandomColor>>){
        val servicesApi= RetrofitService.newInstance().servicesApi
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = servicesApi.getRandomColor()


                withContext(Dispatchers.Main) {
                    if(result!=null && result.isNotEmpty()){
                        listener.onSuccess(result)
                    }else{
                        listener.onFailed()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    listener.onFailed()
                }
            }

        }
    }

    /**
     * @param listener the callback listener after api completed
     */
    fun getRandomPattern(listener: ApiResponseListener<List<RandomPattern>>){
        val servicesApi= RetrofitService.newInstance().servicesApi
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = servicesApi.getRandomPattern()


                withContext(Dispatchers.Main) {
                    if(result!=null && result.isNotEmpty()){
                        listener.onSuccess(result)
                    }else{
                        listener.onFailed()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    listener.onFailed()
                }
            }

        }
    }

}