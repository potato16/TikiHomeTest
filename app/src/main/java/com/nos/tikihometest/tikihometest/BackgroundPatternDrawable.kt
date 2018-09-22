package com.nos.tikihometest.tikihometest

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import java.util.*
import android.graphics.BitmapFactory
import kotlin.collections.ArrayList


class BackgroundPatternDrawable(private val context: Context?) : Drawable() {
    var mPaint: Paint
    var mPath: Path
    var mRect: RectF
    var mBorderRadius: Float
    var mBitmapShader: BitmapShader
    private var pathPattern: Path

    init {
        val bitmap = BitmapFactory.decodeResource(context?.getResources(),
                R.drawable.pattern01)
        mBitmapShader = BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)

        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        //mPaint.setShader(mBitmapShader)
        val random = Random()
        mPaint.color = Color.argb(255, random.nextInt(210), random.nextInt(210), random.nextInt(210))
        mPath = Path()
        mPath.fillType = Path.FillType.EVEN_ODD
        mRect = RectF()
        mBorderRadius = 23f

        pathPattern = Path()
        pathPattern.fillType = Path.FillType.EVEN_ODD
        pathPattern.addRect(0f, 0f, 1f, 1f, Path.Direction.CW)// dot pattern


    }

    override fun draw(canvas: Canvas) {
        canvas.drawPath(mPath, mPaint)
       // canvas.drawRoundRect(mRect,mBorderRadius,mBorderRadius,mPaint)
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }

    override fun onBoundsChange(bounds: Rect?) {
        mPath.reset()
        if (bounds == null) return
        mRect.set((bounds.left).toFloat(), (bounds.top).toFloat(), (bounds.right).toFloat(), (bounds.bottom).toFloat())
        mPath.addRoundRect(mRect, mBorderRadius.toFloat(), mBorderRadius.toFloat(), Path.Direction.CW)

        // mPath.addPath(path,100f,100f)
        for (dx in bounds.left..bounds.right step 20) {
            for (dy in bounds.top..bounds.bottom step 20) {
                // dx< mBorderRadius and dy < mBorderRadius
                // width - dx - pathwidth < mBorderRadius and dy < mBorderRadius
                // dx < mBorderRadius and height - dy  - path_height < mBorderRadius
                // width - dx - path_width < mBorderRadius and  height - dy - path_height< mBorderRadius
                val d_left_top = Math.sqrt((Math.pow((dx - mBorderRadius).toDouble(), 2.0) + Math.pow(((dy - mBorderRadius).toDouble()), 2.0)))
                val d_right_top = Math.sqrt((Math.pow(((dx - bounds.right + bounds.left + mBorderRadius).toDouble()), 2.0) + Math.pow(((dy - bounds.bottom + bounds.top + mBorderRadius).toDouble()), 2.0)))
                val d_left_bot = Math.sqrt((Math.pow((dx - mBorderRadius).toDouble(), 2.0) + Math.pow(((dy - mBorderRadius).toDouble()), 2.0)))
                val d_right_bot = Math.sqrt((Math.pow(((dx - bounds.right + bounds.left + mBorderRadius).toDouble()), 2.0) + Math.pow(((dy - bounds.bottom + bounds.top + mBorderRadius).toDouble()), 2.0)))
                if (!(((dx < mBorderRadius && dy < mBorderRadius) && d_left_top > mBorderRadius) ||
                                ((bounds.right - bounds.left - dx < mBorderRadius && dy < mBorderRadius) && d_right_top > mBorderRadius) ||
                                ((dx < mBorderRadius && bounds.bottom - bounds.top - dy < mBorderRadius) && d_left_bot > mBorderRadius) ||
                                ((bounds.right - bounds.left - dx < mBorderRadius && bounds.bottom - bounds.top - dy < mBorderRadius) && d_right_bot > mBorderRadius)
                                )) {
                    mPath.addPath(pathPattern, dx.toFloat(), dy.toFloat())
                }
            }
        }
    }
}