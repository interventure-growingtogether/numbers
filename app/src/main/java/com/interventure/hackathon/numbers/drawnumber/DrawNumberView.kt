package com.interventure.hackathon.numbers.drawnumber

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.interventure.hackathon.numbers.FingerPath
import com.interventure.hackathon.numbers.R
import kotlin.math.abs

class DrawNumberView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var mX = 0f
    private var mY = 0f
    private var mPath: Path? = null
    private val mPaint: Paint = Paint()
    private val mBorderPaint: Paint = Paint()
    private val paths: ArrayList<FingerPath> = ArrayList()
    private var currentColor = 0
    private var bgColor = DEFAULT_BG_COLOR
    private var strokeWidth = 0
    private var emboss = false
    private var blur = false
    private var mBitmap: Bitmap? = null
    private var mCanvas: Canvas? = null
    private val mBitmapPaint: Paint = Paint(Paint.DITHER_FLAG)
    private var number: Int = 0
    private var numberPath: Path? = null
    private var region: Region? = null
    private var drawingStarted = false

    constructor(context: Context?) : this(context, null)

    init {
        mBorderPaint.isAntiAlias = true
        mBorderPaint.isDither = true
        mBorderPaint.color = ContextCompat.getColor(context!!, R.color.colorPrimaryDark)
        mBorderPaint.style = Paint.Style.STROKE
        mBorderPaint.strokeJoin = Paint.Join.ROUND
        mBorderPaint.strokeCap = Paint.Cap.ROUND
        mBorderPaint.strokeWidth = 10f
        mBorderPaint.xfermode = null
        mBorderPaint.alpha = 0xff

        mPaint.isAntiAlias = true
        mPaint.isDither = true
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.xfermode = null
        mPaint.alpha = 0xff
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.save()
        mCanvas?.drawColor(bgColor)
        if (numberPath == null) {
            numberPath = getDefPathForNumber()
        }
        numberPath?.let {
            mCanvas?.drawPath(it, mBorderPaint)
            mCanvas?.clipPath(it)
        }
        for (fp in paths) {
            mPaint.color = fp.color
            mPaint.strokeWidth = fp.strokeWidth.toFloat()
            mPaint.maskFilter = null
            mCanvas?.drawPath(fp.path, mPaint)
        }
        mBitmap?.let { bitmap ->
            canvas?.drawBitmap(bitmap, 0f, 0f, mBitmapPaint)
        }
        canvas?.restore()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event?.x
        val y = event?.y
        if (x == null || y == null) return true
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touchUp()
                invalidate()
            }
        }
        return true
    }

    fun init(metrics: DisplayMetrics, number: Int, color: Int) {
        this.number = number
        currentColor = color
        val height = metrics.heightPixels
        val width = metrics.widthPixels
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        mBitmap?.let {
            mCanvas = Canvas(it)
        }
        strokeWidth = BRUSH_SIZE
    }

    fun normal() {
        emboss = false
        blur = false
    }

    fun clear() {
        bgColor = DEFAULT_BG_COLOR
        mX = 0.0f
        mY = 0.0f
        paths.clear()
        normal()
        invalidate()
    }

    private fun touchStart(x: Float, y: Float) {
        val dx = abs(x - mX)
        val dy = abs(y - mY)
        if ((mX == 0f && mY == 0f) || (dx <=  90f && dy <= 90f)){
                if (region?.contains(x.toInt(), y.toInt()) == true) {
                    drawingStarted = true
                    mPath = Path()
                    mPath?.let { path ->
                        val fp = FingerPath(currentColor, emboss, blur, strokeWidth, path)
                        paths.add(fp)
                        mPath?.reset()
                        mPath?.moveTo(x, y)
                    }
                    mX = x
                    mY = y
                }
            }
    }

    private fun touchMove(x: Float, y: Float) {
        val dx = abs(x - mX)
        val dy = abs(y - mY)
        if (drawingStarted && (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) && region?.contains(
                x.toInt(),
                y.toInt()
            ) == true
        ) {
            mPath?.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
        }
    }

    private fun touchUp() {
        mPath?.lineTo(mX, mY)
        drawingStarted = false
    }

    private fun getDefPathForNumber(): Path {
        val textPath = Path()
        val paint = Paint(ANTI_ALIAS_FLAG)
        paint.color = Color.RED
        paint.textSize = 1000f
        val textNumber = number.toString()

        // center number
        val bounds = Rect()
        paint.getTextBounds(textNumber, 0, textNumber.length, bounds)
        val x: Int = width / 2 - bounds.width() / 2
        val y: Int = height / 2 - bounds.height() / 2

        // get number path
        paint.getTextPath(
            textNumber,
            0,
            textNumber.length,
            x.toFloat(),
            y.toFloat() + paint.textSize / 2,
            textPath
        )
        textPath.close()

        val rectF = RectF()
        textPath.computeBounds(rectF, true)
        region = Region()
        region?.setPath(
            textPath,
            Region(rectF.left.toInt(), rectF.top.toInt(), rectF.right.toInt(), rectF.bottom.toInt())
        )

        return textPath
    }

    companion object {
        var BRUSH_SIZE = 200
        const val DEFAULT_BG_COLOR: Int = Color.WHITE
        private const val TOUCH_TOLERANCE = 4f
    }
}