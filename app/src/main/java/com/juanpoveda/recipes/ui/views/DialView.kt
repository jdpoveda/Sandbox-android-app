package com.juanpoveda.recipes.ui.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import com.juanpoveda.recipes.R
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

// ****CustomView s2: Add an Enum with all the possible states of the dial.
private enum class DialState(val label: Int) {
    OFF(R.string.dial_off),
    LOW(R.string.dial_low),
    MEDIUM(R.string.dial_medium),
    HIGH(R.string.dial_high);

    // ****CustomView s9 (to add view interactivity): Add an extension function to change the current dial state to the next state.
    fun next() = when (this) {
        OFF -> LOW
        LOW -> MEDIUM
        MEDIUM -> HIGH
        HIGH -> OFF
    }
}

// ****CustomView s3: Define all the constants you'll need as class-level attributes.
private const val RADIUS_OFFSET_LABEL = 50
private const val RADIUS_OFFSET_INDICATOR = -50

// ****CustomView s1: Create the class for the custom view, it must extend View (or any subclass of View like EditText, TextView, etc).
// Note that you need to pass a context, an AttributeSet and the defStyleAttr.
// The @JvmOverloads annotation instructs the Kotlin compiler to generate overloads for this function that substitute default parameter values.
class DialView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr) {
    // ****CustomView s4: Inside the class, define variables that will be needed to draw the view.
    // NOTE: These values are created and initialized here instead of when the view is actually drawn, to ensure that the actual drawing step runs as fast as possible.
    private var radius = 0.0f // Current radius of the circle. This value is set when the view is drawn on the screen.
    private var dialState = DialState.OFF // The active selection.
    private val pointPosition: PointF = PointF(0.0f, 0.0f) // position variable which will be used to draw label and indicator circle position
    // ****CustomView s5: Initialize a Paint object with a handful of basic styles
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 45.0f
        typeface = Typeface.create( "", Typeface.BOLD)
    }

    // ****CustomView s15: In order to use the attributes in your DialView class, you need to retrieve them. They are stored in an AttributeSet.
    // First, create the variables to save the attribute values
    private var dialLowColor = 0
    private var dialMedColor = 0
    private var dialMaxColor = 0

    init {
        // ****CustomView s10 (to add view interactivity): the isClickable attribute must be set to true
        isClickable = true
        // ****CustomView s16: Initialize the color variables using the previously created styleable in attrs.xml file
        context.withStyledAttributes(attrs, R.styleable.DialView) {
            dialLowColor = getColor(R.styleable.DialView_dialColor1, 0)
            dialMedColor = getColor(R.styleable.DialView_dialColor2, 0)
            dialMaxColor = getColor(R.styleable.DialView_dialColor3, 0)
        }
    }
    // ****CustomView s11 (to add view interactivity): Override the performClick method to change the dialState. The default performClick() method also calls
    // onClickListener(), so you can add your actions to performClick() and leave onClickListener() available for further customization
    override fun performClick(): Boolean {
        if (super.performClick()) return true
        dialState = dialState.next()
        contentDescription = resources.getString(dialState.label)
        // The invalidate() method invalidates the entire view, forcing a call to onDraw() to redraw the view. If something in your custom view changes
        // for any reason, including user interaction, and the change needs to be displayed, call invalidate().
        invalidate()
        return true
    }

    // ****CustomView s6: Override the onSizeChanged() method.
    // This method is called any time the view's size changes, including the first time it is drawn when the layout is inflated. Override
    // it to calculate positions, dimensions, and any other values related to your custom view's size, instead of recalculating them every time
    // you draw. In this case we are using this method to calculate the current radius of the dial's circle element.
    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        radius = (min(width, height) / 2.0 * 0.7).toFloat()
    }

    // ****CustomView s7: Add this extension function for the PointF class. This extension function on the PointF class calculates the (X, Y)
    // coordinates on the screen for the text label and current indicator (0, 1, 2, or 3), given the current DialState position and radius
    // of the dial. It will be used later in the onDraw function.
    private fun PointF.computeXYForDialState(pos: DialState, radius: Float) {
        // Angles are in radians.
        val startAngle = Math.PI
        val angle = startAngle + pos.ordinal * (Math.PI / 4)
        x = (radius * cos(angle)).toFloat() + width / 2
        y = (radius * sin(angle)).toFloat() + height / 2
    }

    // ****CustomView s8: Override the onDraw method. This method will render the view in the screen using Canvas and Paint.
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // ****CustomView s8-1: Set dial background color depending on the state.
        //paint.color = if (dialState == DialState.OFF) Color.GRAY else Color.GREEN
        // ****CustomView s17: Set the color according to each state
        paint.color = when (dialState) {
            DialState.OFF -> Color.GRAY
            DialState.LOW -> dialLowColor
            DialState.MEDIUM -> dialMedColor
            DialState.HIGH -> dialMaxColor
        } as Int
        // ****CustomView s8-2: Draw the dial by using the drawCircle function.
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)
        // ****CustomView s8-3: Draw the indicator inside the dial
        val markerRadius = radius + RADIUS_OFFSET_INDICATOR
        pointPosition.computeXYForDialState(dialState, markerRadius)
        paint.color = Color.BLACK
        canvas.drawCircle(pointPosition.x, pointPosition.y, radius/12, paint)
        // ****CustomView s8-4: Add the text labels.
        val labelRadius = radius + RADIUS_OFFSET_LABEL
        for (i in DialState.values()) {
            pointPosition.computeXYForDialState(i, labelRadius)
            val label = resources.getString(i.label)
            canvas.drawText(label, pointPosition.x, pointPosition.y, paint)
        }

    }
}