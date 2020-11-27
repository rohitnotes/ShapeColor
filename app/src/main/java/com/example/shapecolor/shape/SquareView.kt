/**
 * This class will render a square shape view with random color and size
 */
import android.content.Context
import android.graphics.*
import android.view.View
import com.example.shapecolor.shape.ShapeBase

/**
 * @param context The Context the view is running in
 * @param leftPos The left-coordinate of the square to be drawn
 * @param topPos The top-coordinate of the square to be drawn
 * @param rightPos The right-coordinate of the square to be drawn
 * @param bottomPos The bottom-coordinate of the square to be drawn
 * @param colorHex The color used to fill the triangle
 */
class SquareView(context: Context?, private var leftPos: Int, private val topPos: Int, private val rightPos: Int, private val bottomPos: Int, private var colorHex: String) : ShapeBase(
    context
) {
    private val paint: Paint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.style = Paint.Style.FILL
        paint.color = Color.TRANSPARENT
        paint.color = Color.parseColor("#$colorHex")
        canvas.drawRect(leftPos.toFloat(), topPos.toFloat(), rightPos.toFloat(),
            bottomPos.toFloat(), paint );
    }

    override fun updateColor(colorHex: String){
        this.colorHex = colorHex
        this.invalidate()
    }
}