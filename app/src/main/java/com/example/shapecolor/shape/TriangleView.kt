/**
 * This class will render a triangle shape view with random color and size
 */
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.View
import com.example.shapecolor.shape.ShapeBase

/**
 * @param context The Context the view is running in
 * @param cx The x-coordinate of the center of the triangle to be drawn
 * @param cy The y-coordinate of the center of the triangle to be drawn
 * @param w The width of the triangle to be drawn
 * @param colorHex The color used to fill the triangle
 */
class TriangleView(context: Context?, private val cx: Int, private val cy: Int, private val w: Int, private var colorHex: String) : ShapeBase(
    context
) {
    private val paint: Paint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val halfWidth = w / 2

        paint.style = Paint.Style.FILL
        paint.color = Color.TRANSPARENT
        canvas.drawPaint(paint)
        paint.color = Color.parseColor("#$colorHex")
        val path = Path()
        path.moveTo(cx.toFloat(), (cy - halfWidth).toFloat()); // Top
        path.lineTo((cx - halfWidth).toFloat(), (cy + halfWidth).toFloat()); // Bottom left
        path.lineTo((cx + halfWidth).toFloat(), (cy + halfWidth).toFloat()); // Bottom right
        path.lineTo(cx.toFloat(), (cy - halfWidth).toFloat()); // Back to Top
        path.close();

        canvas.drawPath(path, paint)
    }
    override fun updateColor(colorHex: String){
        this.colorHex = colorHex
        this.invalidate()
    }
}