package com.interventure.hackathon.numbers.scratch

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.media.MediaPlayer
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import com.interventure.hackathon.numbers.FingerPath
import com.interventure.hackathon.numbers.R
import kotlin.math.abs

class ScratchView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var mX = 0f
    private var mY = 0f
    private var mPath: Path? = null
    private val mPaint: Paint = Paint()
    private val paths: ArrayList<FingerPath> = ArrayList()
    private var currentColor = 0
    private var bgColor = DEFAULT_BG_COLOR
    private var strokeWidth = 0
    private var emboss = false
    private var blur = false
    private var mBitmap: Bitmap? = null
    private var mCanvas: Canvas? = null
    private val mBitmapPaint: Paint = Paint(Paint.DITHER_FLAG)
    private val porterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    private val scratchSound =  MediaPlayer.create(context, R.raw.scratch)

    constructor(context: Context?) : this(context, null)

    init {
        mPaint.isAntiAlias = true
        mPaint.isDither = true
        mPaint.color = DEFAULT_COLOR
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.xfermode = null
        mPaint.alpha = 0xff
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.save()
        mCanvas?.drawColor(bgColor)
        for (fp in paths) {
            mPaint.color = fp.color
            mPaint.strokeWidth = fp.strokeWidth.toFloat()
            mPaint.maskFilter = null
            mPaint.xfermode = porterDuffXfermode
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
                scratchSound.start()
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(x, y)
                scratchSound.start()
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touchUp()
                scratchSound.pause()
                invalidate()
            }
        }
        return true
    }

    fun init(metrics: DisplayMetrics) {
        val height = metrics.heightPixels
        val width = metrics.widthPixels
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        mBitmap?.let {
            mCanvas = Canvas(it)
        }
        currentColor = DEFAULT_COLOR
        strokeWidth = BRUSH_SIZE
    }

    fun normal() {
        emboss = false
        blur = false
    }

    fun clear() {
        bgColor = DEFAULT_BG_COLOR
        paths.clear()
        normal()
        invalidate()
    }

    private fun touchStart(x: Float, y: Float) {
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

    private fun touchMove(x: Float, y: Float) {
        val dx = abs(x - mX)
        val dy = abs(y - mY)
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath?.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
        }
    }

    private fun touchUp() {
        mPath?.lineTo(mX, mY)
    }

    companion object {
        var BRUSH_SIZE = 160
        const val DEFAULT_COLOR: Int = Color.GRAY
        const val DEFAULT_BG_COLOR: Int = Color.GRAY
        private const val TOUCH_TOLERANCE = 4f
    }
}