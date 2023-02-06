package ru.netology.statsview.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import ru.netology.nmedia.R
import ru.netology.statsview.ui.utils.AndroidUtils
import kotlin.math.min
import kotlin.random.Random

class Statsview @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : View(context, attributeSet, defStyleAttr, defStyleRes)
{
    private var textSize = AndroidUtils.dp(context,20).toFloat()
    private var lineWidth = AndroidUtils.dp(context,5)
    private var colors = emptyList<Int>()
    init {
        context.withStyledAttributes(attributeSet, R.styleable.Statsview){
            textSize = getDimension(R.styleable.Statsview_textSize,textSize)
            lineWidth = getDimension(R.styleable.Statsview_lineWidth,lineWidth.toFloat()).toInt()
            colors = listOf(
                getColor(R.styleable.Statsview_color1,generateRandomColor()),
                getColor(R.styleable.Statsview_color2,generateRandomColor()),
                getColor(R.styleable.Statsview_color3,generateRandomColor()),
                getColor(R.styleable.Statsview_color4,generateRandomColor()),
            )
        }
    }
    var data: List<Float> = emptyList()
    set(value){
        field = value
        invalidate() //вызвает fun onDraw
    }
    private var radius = 0F
    private var center = PointF()
    private var oval = RectF()
    private val paint = Paint(
        Paint.ANTI_ALIAS_FLAG //С„Р»Р°Рі СЃРіР»Р°Р¶РёРІР°РЅРёСЏ
        ).apply {
        strokeWidth = lineWidth.toFloat()
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }

    private val textPaint = Paint(
        Paint.ANTI_ALIAS_FLAG // Включить сглаживание
    ).apply {
        textSize = this@Statsview.textSize
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = min(w,h)/2F - lineWidth //делаем отступ 5dp
        center = PointF(w/2F,h/2F)
        oval=RectF(
            center.x-radius,
            center.y - radius,
            center.x + radius,
            center.y + radius
        )
    }

    override fun onDraw(canvas: Canvas) {
        if (data.isEmpty()){
            return
        }
        var startAngle = -90F
        data.forEachIndexed(){index, datum ->
            var angle = datum * 360F
            paint.color = colors.getOrElse(index){generateRandomColor()}
            canvas.drawArc(oval,startAngle,angle,false,paint)
            startAngle+=angle
        }

        canvas.drawText(
            "%.2f%%".format(data.sum()*100),
            center.x,
            center.y + textPaint.textSize/4,
            textPaint
        )
        //invalidate()
    }

    private fun generateRandomColor() = Random.nextInt(0xFF000000.toInt(), 0xFFFFFFFF.toInt())
}