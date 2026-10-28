package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.KnobMetallic
import com.example.ui.theme.KnobTrack
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.ValetonCyan
import com.example.ui.theme.ValetonNavyCard
import com.example.ui.theme.ValetonNavyDark
import com.example.ui.theme.ValetonNavyElevated
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PedalKnob(
    label: String,
    value: Float,
    range: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    unit: String = "",
    tintColor: Color = ValetonCyan
) {
    var dragAccumulator by remember { mutableFloatStateOf(0f) }

    val normalized = ((value - range.start) / (range.endInclusive - range.start)).coerceIn(0f, 1f)
    val startAngle = 135f
    val sweepAngle = 270f
    val currentAngle = startAngle + (normalized * sweepAngle)

    Column(
        modifier = modifier
            .width(82.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(ValetonNavyCard)
            .border(1.dp, ValetonNavyElevated, RoundedCornerShape(10.dp))
            .padding(vertical = 8.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            maxLines = 1,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .size(54.dp)
                .pointerInput(range) {
                    detectDragGestures(
                        onDragStart = { dragAccumulator = 0f },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            dragAccumulator -= dragAmount.y
                            val step = (range.endInclusive - range.start) / 100f
                            val delta = (dragAmount.y * -0.5f) * step
                            val newValue = (value + delta).coerceIn(range.start, range.endInclusive)
                            onValueChange(newValue)
                        }
                    )
                }
                .testTag("knob_${label.lowercase().replace(" ", "_")}"),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(52.dp)) {
                val strokeWidth = 5.dp.toPx()
                val radius = (size.minDimension - strokeWidth) / 2f
                val center = Offset(size.width / 2f, size.height / 2f)

                // Track Arc (Inactive)
                drawArc(
                    color = KnobTrack,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )

                // Active Arc
                drawArc(
                    brush = Brush.sweepGradient(
                        colors = listOf(tintColor.copy(alpha = 0.6f), tintColor),
                        center = center
                    ),
                    startAngle = startAngle,
                    sweepAngle = normalized * sweepAngle,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )

                // Inner knob circle
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(KnobMetallic, ValetonNavyDark),
                        center = center,
                        radius = radius * 0.75f
                    ),
                    radius = radius * 0.72f,
                    center = center
                )

                // Indicator notch
                val angleRad = Math.toRadians(currentAngle.toDouble())
                val notchStartX = center.x + (radius * 0.35f * cos(angleRad)).toFloat()
                val notchStartY = center.y + (radius * 0.35f * sin(angleRad)).toFloat()
                val notchEndX = center.x + (radius * 0.70f * cos(angleRad)).toFloat()
                val notchEndY = center.y + (radius * 0.70f * sin(angleRad)).toFloat()

                drawLine(
                    color = tintColor,
                    start = Offset(notchStartX, notchStartY),
                    end = Offset(notchEndX, notchEndY),
                    strokeWidth = 3.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        val displayVal = if (value % 1.0f == 0.0f) "${value.toInt()}$unit" else String.format("%.1f$unit", value)
        Text(
            text = displayVal,
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = tintColor
        )
    }
}
