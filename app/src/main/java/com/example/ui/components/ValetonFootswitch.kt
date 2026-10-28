package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.KnobMetallic
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.ValetonCyan
import com.example.ui.theme.ValetonNavyDark

@Composable
fun ValetonFootswitch(
    label: String,
    subLabel: String,
    isActive: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
    ledColor: Color = ValetonCyan
) {
    Column(
        modifier = modifier
            .clickable { onToggle() }
            .padding(6.dp)
            .testTag("footswitch_${label.lowercase()}"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Footswitch Button with LED Ring
        Box(
            modifier = Modifier.size(56.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(54.dp)) {
                val center = Offset(size.width / 2f, size.height / 2f)
                val ringRadius = size.minDimension / 2f - 4.dp.toPx()

                // Glow ring
                if (isActive) {
                    drawCircle(
                        color = ledColor.copy(alpha = 0.35f),
                        radius = ringRadius + 3.dp.toPx()
                    )
                }

                // LED Ring
                drawCircle(
                    color = if (isActive) ledColor else Color(0xFF1E293B),
                    radius = ringRadius,
                    style = Stroke(width = 3.dp.toPx())
                )

                // Stainless steel actuator cap
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = if (isActive) listOf(Color(0xFFE2E8F0), Color(0xFF64748B), ValetonNavyDark)
                        else listOf(Color(0xFF94A3B8), Color(0xFF334155), ValetonNavyDark),
                        center = center,
                        radius = ringRadius * 0.75f
                    ),
                    radius = ringRadius * 0.72f,
                    center = center
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = if (isActive) TextPrimary else TextMuted,
            fontFamily = FontFamily.Monospace
        )

        Text(
            text = subLabel,
            fontSize = 9.sp,
            color = if (isActive) ledColor else TextMuted
        )
    }
}
