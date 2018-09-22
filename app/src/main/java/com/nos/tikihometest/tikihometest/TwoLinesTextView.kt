package com.nos.tikihometest.tikihometest

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import java.lang.StringBuilder


class TwoLinesTextView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0

) : android.support.v7.widget.AppCompatTextView(context, attrs, defStyleAttr) {


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val result = super.onTouchEvent(event)
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                background.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY)
                invalidate()
                return true
            }
            MotionEvent.ACTION_UP -> {
                background.clearColorFilter()
                invalidate()
                return false
            }
            MotionEvent.ACTION_CANCEL -> {
                background.clearColorFilter()
                invalidate()
                return false
            }
        }
        return result
    }

    override fun setText(text: CharSequence?, type: BufferType?) {

        if (text == null) return
        var str = text.toString()
        val words = str.split(" ")
        Log.d(C.TAG, "Text count: ${words.size} of $str")

        if (words.size > 2) {
            val textwidth = paint.measureText(str)
            var firstLineWidth = 0f
            var firstLine: StringBuilder = StringBuilder()
            var secondLine: StringBuilder = StringBuilder()
            var difWidth = -1
            for (word in words) {
                if (firstLineWidth < textwidth / 2f) {
                    firstLineWidth += paint.measureText(word)
                    Log.d(C.TAG, "-------------------------------------------")
                    Log.d(C.TAG, "Dif width: $textwidth/2 - $firstLineWidth = ${textwidth / 2 - firstLineWidth}")
                    Log.d(C.TAG, "Text break: $firstLine ## $secondLine")
                    Log.d(C.TAG, "-------------------------------------------")
                    if (difWidth != -1) {
                        val tmpDif = Math.abs(textwidth / 2 - firstLineWidth).toInt()
                        if (difWidth > tmpDif) {
                            firstLine.append("$word ")
                        } else {
                            secondLine.append("$word ")
                        }
                    } else {
                        difWidth = Math.abs(textwidth / 2 - firstLineWidth).toInt()
                        firstLine.append("$word ")
                    }

                } else {
                    secondLine.append("$word ")
                }
            }
            str = "${firstLine.trim()}\n" +
                    "${secondLine.trim()}"


        }
        super.setText(str, type)
    }
}
