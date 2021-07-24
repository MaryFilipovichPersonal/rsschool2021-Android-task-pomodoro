package com.rsshool2021.android.pomodoro.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import com.rsshool2021.android.pomodoro.R

class PieProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var periodMs = 0L
    private var currentMs = 0L
    private var color = 0
    private var style = FILL
    private val paint = Paint()

    init {
        if (attrs != null) {
            val styledAttrs = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.PieProgressBar,
                defStyleAttr,
                0
            )
            color = styledAttrs.getColor(R.styleable.PieProgressBar_pie_color, Color.RED)
            style = styledAttrs.getInt(R.styleable.PieProgressBar_pie_style, FILL)
            styledAttrs.recycle()
        }

        paint.color = color
        paint.style = if (style == FILL) Paint.Style.FILL else Paint.Style.STROKE
        paint.strokeWidth = 5f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var startAngel = -90f
        if (periodMs <= 0L || currentMs < 0L) return
        if (currentMs == periodMs) startAngel = 360f
        else
            startAngel = (((currentMs % periodMs).toFloat() / periodMs) * 360)

        canvas.drawArc(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            -90f,
            startAngel,
            true,
            paint
        )
    }

    fun setProgress(progress: Long) {
        currentMs = progress
        invalidate()
    }

    fun getProgress() = currentMs

    fun setMax(period: Long) {
        periodMs = period
    }

    fun getMax() = periodMs

    private companion object {
        private const val FILL = 0
    }

}