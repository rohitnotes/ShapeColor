package com.example.shapecolor

import CircleView
import SquareView
import TriangleView
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.shapecolor.databinding.ActivityMainBinding
import com.example.shapecolor.events.*
import com.example.shapecolor.models.RandomColor
import com.example.shapecolor.models.RandomPattern
import com.example.shapecolor.shape.ShapeBase
import com.example.shapecolor.shape.ShapeType
import com.example.shapecolor.utils.Utils
import com.google.android.material.tabs.TabLayout
import kotlin.random.Random


class MainActivity : AppCompatActivity(), AccelerometerListener, EventTapCallback {
    private var tabSelected = ShapeType.Square

    private var maxLimitPercent = 0.45
    private var minLimitPercent = 0.1
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private var isDragging = false
    private var shapeMoving: View? = null
    lateinit var accelerometerManager: AccelerometerManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = MainViewModel()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setEventListener()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setEventListener() {
        binding.headerTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    when (tab.position) {
                        0 -> setTabSelected(ShapeType.Square)
                        1 -> setTabSelected(ShapeType.Circle)
                        2 -> setTabSelected(ShapeType.Triangle)
                        3 -> setTabSelected(ShapeType.All)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        binding.frmContent.setOnTouchListener { v, event ->
            if (event != null) {
                if (event.action == MotionEvent.ACTION_UP) {
                    if(shapeMoving==null) {
                        if (!isDragging) {
                            binding.loadingProgress.visibility = View.VISIBLE
                            val xPos = event.x.toInt()
                            val yPos = event.y.toInt()
                            var compareShareType = tabSelected
                            if (compareShareType == ShapeType.All) {
                                compareShareType = ShapeType.values()[Random.nextInt(0, 3)]
                            }
                            val intColor: Int = Color.rgb(
                                Random.nextInt(256),
                                Random.nextInt(255),
                                Random.nextInt(255)
                            )
                            val randomHex =
                                java.lang.String.format("%06X", 0xFFFFFF and intColor)
                            if (Utils.isInternetOn(this@MainActivity)) {
                                if (compareShareType == ShapeType.Square) {
                                    mainViewModel.getRandomPattern(object :
                                        ApiResponseListener<List<RandomPattern>> {
                                        override fun onSuccess(data: List<RandomPattern>) {
                                            showShape(
                                                compareShareType,
                                                xPos,
                                                yPos,
                                                randomHex,
                                                data[0].imageUrl
                                            )
                                        }

                                        override fun onFailed() {
                                            showShape(compareShareType, xPos, yPos, randomHex, null)
                                        }

                                    })
                                } else {
                                    mainViewModel.getRandomColor(object :
                                        ApiResponseListener<List<RandomColor>> {
                                        override fun onSuccess(data: List<RandomColor>) {
                                            showShape(
                                                compareShareType,
                                                xPos,
                                                yPos,
                                                data[0].hex,
                                                null
                                            )
                                        }

                                        override fun onFailed() {
                                            showShape(compareShareType, xPos, yPos, randomHex, null)
                                        }

                                    })
                                }
                            } else {
                                showShape(compareShareType, xPos, yPos, randomHex, null)
                            }
                        } else {
                            isDragging = false
                        }
                    }else{
                        shapeMoving = null
                    }
                } else if (event.action == MotionEvent.ACTION_MOVE) {
                    isDragging = true
                    if (shapeMoving != null) {
                        var layoutParams = shapeMoving!!.layoutParams as FrameLayout.LayoutParams
                        val xPos = event.x.toInt()
                        val yPos = event.y.toInt()
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            layoutParams.marginStart = xPos - (layoutParams.width / 2)
                            layoutParams.topMargin = yPos - (layoutParams.width / 2)
                        }
                        shapeMoving!!.layoutParams = layoutParams
                    }
                }
            }
            true
        }
    }


    /**
     * function: render a shape
     * @param x The x-coordinate of the center of point of touch event
     * @param y The y-coordinate of the center of point of touch event
     * @param hexColor The color will be filled into shape
     * @param imageUrl The imageUrl will be filled into shape
     */
    @SuppressLint("ClickableViewAccessibility")
    fun showShape(
        compareShareType: ShapeType,
        x: Int,
        y: Int,
        hexColor: String,
        imageUrl: String?
    ) {
        binding.loadingProgress.visibility = View.INVISIBLE
        var randomX = Random.nextInt(
            (window.decorView.width * minLimitPercent).toInt(),
            (window.decorView.width * maxLimitPercent).toInt()
        )
        var shapeView: View? = null
        if (compareShareType == ShapeType.Circle) {
            shapeView =
                CircleView(this@MainActivity, randomX / 2, randomX / 2, randomX / 2, hexColor)
        } else if (compareShareType == ShapeType.Triangle) {
            shapeView =
                TriangleView(this@MainActivity, randomX / 2, randomX / 2, randomX, hexColor)
        } else if (compareShareType == ShapeType.Square) {
            if (imageUrl == null) {
                shapeView =
                    SquareView(this@MainActivity, 0, 0, randomX, randomX, hexColor)
            } else {
                shapeView = ImageView(this)
            }

        }

        if (shapeView != null) {
            var layoutParams = FrameLayout.LayoutParams(
                randomX,
                randomX
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.marginStart = x - (randomX / 2)
                layoutParams.topMargin = y - (randomX / 2)
            }
            val fade_in = ScaleAnimation(
                0f,
                1f,
                0f,
                1f,
                Animation.ABSOLUTE,
                0.5f,
                Animation.ABSOLUTE,
                0.5f
            )
            fade_in.duration = 300
            fade_in.fillAfter = true
            shapeView!!.startAnimation(fade_in)

            binding.frmContent.addView(shapeView, layoutParams)
            val doubleTapListener = EventTapListener()
            doubleTapListener.setDoubleTapCallbackListener(this)
            shapeView?.setOnTouchListener(doubleTapListener)
            if (compareShareType == ShapeType.Square) {
                if(imageUrl!=null) {
                    shapeView?.setBackgroundResource(R.drawable.shape_bg)
                    Glide.with(this.applicationContext).load(imageUrl).centerCrop()
                        .into(shapeView as ImageView)
                }
            }
        }
    }

    private fun removeShape() {
        try {
            binding.frmContent.removeAllViews()
        } catch (e: Exception) {
        }
    }

    private fun setTabSelected(shape: ShapeType) {
        this.tabSelected = shape
        this.shapeMoving = null
        this.isDragging = false
        Log.d("setTabSelected", shape.name)
    }

    override fun onResume() {
        super.onResume()
        accelerometerManager = AccelerometerManager()
        if (accelerometerManager.isSupported(this)) {
            accelerometerManager.startListening(this);
        }
    }

    override fun onPause() {
        super.onPause()
        if (accelerometerManager.isListening()) {
            accelerometerManager.stopListening();
        }
    }

    override fun onAccelerationChanged(x: Float, y: Float, z: Float) {

    }

    override fun onShake(force: Float) {
        removeShape()
    }

    override fun onDoubleClick(v: View?) {
        binding.loadingProgress.visibility = View.VISIBLE
        val intColor: Int = Color.rgb(
            Random.nextInt(256),
            Random.nextInt(255),
            Random.nextInt(255)
        )
        val randomHex =
            java.lang.String.format("%06X", 0xFFFFFF and intColor)

        if(v is ImageView){
            mainViewModel.getRandomPattern(object :
                ApiResponseListener<List<RandomPattern>> {
                override fun onSuccess(data: List<RandomPattern>) {
                    binding.loadingProgress.visibility = View.INVISIBLE
                    Glide.with(this@MainActivity.applicationContext).load(data[0].imageUrl).centerCrop().into(v)
                }

                override fun onFailed() {
                    try{
                        v.setBackgroundColor(intColor)
                    }catch (e: java.lang.Exception){}
                    binding.loadingProgress.visibility = View.INVISIBLE
                }

            })
        }else if(v is ShapeBase){
            mainViewModel.getRandomColor(object :
                ApiResponseListener<List<RandomColor>> {
                override fun onSuccess(data: List<RandomColor>) {
                    binding.loadingProgress.visibility = View.INVISIBLE
                    v.updateColor(data[0].hex)
                }

                override fun onFailed() {
                    binding.loadingProgress.visibility = View.INVISIBLE
                    v.updateColor(randomHex)
                }

            })
        }
    }

    override fun onMoving(v: View?) {
        this.shapeMoving = v
    }
}