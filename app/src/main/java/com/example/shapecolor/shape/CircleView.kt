/**
 * This class will render a circle shape view with random color and size
 */
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import com.example.shapecolor.shape.ShapeBase

/**
 * @param context The Context the view is running in
 * @param cx The x-coordinate of the center of the circle to be drawn
 * @param cy The y-coordinate of the center of the circle to be drawn
 * @param radius The radius of the circle to be drawn
 * @param colorHex The color used to fill the circle
 */
class CircleView(context: Context?, private val cx: Int, private val cy: Int, private val radius: Int, private var colorHex: String) : ShapeBase(context) {
    private val paint: Paint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.style = Paint.Style.FILL
        paint.color = Color.TRANSPARENT
        canvas.drawPaint(paint)
        paint.color = Color.parseColor("#$colorHex")
        canvas.drawCircle(cx.toFloat(), cy.toFloat(), radius.toFloat(), paint)
    }

    override fun updateColor(colorHex: String){
        this.colorHex = colorHex
        this.invalidate()
    }
}