package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.TextPrimary
import kotlin.math.roundToInt

@Composable
fun ValetonSliderRow(
    label: String,
    value: Float,
    range: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    unit: String = ""
) {
    val normalized = ((value - range.start) / (range.endInclusive - range.start)).coerceIn(0f, 1f)

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(46.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF1E293B)) // Authentic dark grey bar from screenshot
            .pointerInput(range) {
                detectTapGestures { offset ->
                    val ratio = (offset.x / size.width).coerceIn(0f, 1f)
                    val newValue = range.start + ratio * (range.endInclusive - range.start)
                    onValueChange(newValue)
                }
            }
            .pointerInput(range) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    val deltaRatio = dragAmount.x / size.width
                    val deltaVal = deltaRatio * (range.endInclusive - range.start)
                    val newValue = (value + deltaVal).coerceIn(range.start, range.endInclusive)
                    onValueChange(newValue)
                }
            }
            .testTag("valeton_slider_${label.lowercase()}"),
        contentAlignment = Alignment.CenterStart
    ) {
        val maxAvailableWidth = maxWidth
        // White filled pill indicating current value position
        val pillWidth = 140.dp
        val maxSlideOffset = (maxAvailableWidth - pillWidth).coerceAtLeast(0.dp)
        val pillOffsetXDp = maxSlideOffset * normalized

        // The Sliding White Pill with the Parameter Label (Matching user screenshot)
        Box(
            modifier = Modifier
                .offset { IntOffset((pillOffsetXDp.toPx()).roundToInt(), 0) }
                .width(pillWidth)
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFE2E8F0)) // Clean white pill from screenshot
                .padding(horizontal = 14.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF0F172A), // Dark text on white pill
                maxLines = 1
            )
        }

        // Numeric value on the right
        val displayVal = if (value % 1.0f == 0.0f) "${value.toInt()}$unit" else String.format("%.1f$unit", value)
        Text(
            text = displayVal,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
        )
    }
}
