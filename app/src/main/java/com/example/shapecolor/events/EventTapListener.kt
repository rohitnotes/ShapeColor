package com.example.shapecolor.events

import android.util.Log
import android.view.MotionEvent
import android.view.View

open class EventTapListener: View.OnTouchListener {
    private var isRunning = false
    private val resetInTime = 500
    private var counter = 0

    private var listener: EventTapCallback? = null

    fun setDoubleTapCallbackListener(listener: EventTapCallback){
        this.listener = listener
    }
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event != null) {
            if (event.action ==MotionEvent.ACTION_DOWN) {
                listener?.onMoving(v)
                if (isRunning) {
                    if (counter == 1) //<-- makes sure that the callback is triggered on double click
                        listener?.onDoubleClick(v)
                }
                counter++
                if (!isRunning) {
                    isRunning = true
                    Thread {
                        try {
                            Thread.sleep(resetInTime.toLong())
                            isRunning = false
                            counter = 0
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }.start()
                }
            }
        }
        return false
    }

}