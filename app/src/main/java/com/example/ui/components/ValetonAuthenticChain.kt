package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.EffectBlock
import com.example.model.EffectBlockType
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.ValetonCyan
import com.example.ui.theme.ValetonElectricBlue
import com.example.ui.theme.ValetonNavyCard
import com.example.ui.theme.ValetonNavyDark

@Composable
fun ValetonAuthenticChain(
    blocks: List<EffectBlock>,
    selectedType: EffectBlockType,
    onSelectBlock: (EffectBlockType) -> Unit,
    modifier: Modifier = Modifier
) {
    val topRowTypes = listOf(
        EffectBlockType.NR,
        EffectBlockType.PRE,
        EffectBlockType.WAH,
        EffectBlockType.DST,
        EffectBlockType.NS,
        EffectBlockType.AMP
    )

    val bottomRowTypes = listOf(
        EffectBlockType.CAB,
        EffectBlockType.EQ,
        EffectBlockType.MOD,
        EffectBlockType.DLY,
        EffectBlockType.RVB,
        EffectBlockType.VOL
    )

    val blockMap = blocks.associateBy { it.type }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF0F172A)) // Valeton dark chassis background
            .padding(vertical = 12.dp, horizontal = 8.dp)
    ) {
        // Background Blue Connecting Cable
        Canvas(modifier = Modifier.fillMaxWidth().height(160.dp)) {
            val width = size.width
            val height = size.height
            val stroke = Stroke(width = 2.5.dp.toPx(), cap = StrokeCap.Round)
            val cableColor = Color(0xFF2563EB) // Bright electric blue cable

            val topY = 36.dp.toPx()
            val bottomY = 124.dp.toPx()

            // Line 1: through top row
            drawLine(
                color = cableColor,
                start = Offset(0f, topY),
                end = Offset(width - 16.dp.toPx(), topY),
                strokeWidth = stroke.width
            )

            // Loop curve from top to bottom on the right edge
            val curvePath = Path().apply {
                moveTo(width - 16.dp.toPx(), topY)
                cubicTo(
                    width + 12.dp.toPx(), topY,
                    width + 12.dp.toPx(), bottomY,
                    width - 16.dp.toPx(), bottomY
                )
            }
            drawPath(path = curvePath, color = cableColor, style = stroke)

            // Line 2: through bottom row
            drawLine(
                color = cableColor,
                start = Offset(width - 16.dp.toPx(), bottomY),
                end = Offset(0f, bottomY),
                strokeWidth = stroke.width
            )
        }

        // Foreground Pedal Icons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Row 1: NR, PRE, WAH, DST, N->S, AMP
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                topRowTypes.forEach { type ->
                    val block = blockMap[type]
                    val isSelected = type == selectedType
                    ValetonBlockPedal(
                        type = type,
                        isEnabled = block?.enabled == true,
                        isSelected = isSelected,
                        onClick = { onSelectBlock(type) }
                    )
                }
            }

            // Row 2: CAB, EQ, MOD, DLY, RVB, VOL
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                bottomRowTypes.forEach { type ->
                    val block = blockMap[type]
                    val isSelected = type == selectedType
                    ValetonBlockPedal(
                        type = type,
                        isEnabled = block?.enabled == true,
                        isSelected = isSelected,
                        onClick = { onSelectBlock(type) }
                    )
                }
            }
        }
    }
}

@Composable
fun ValetonBlockPedal(
    type: EffectBlockType,
    isEnabled: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Authentic Valeton colors from user's screenshot:
    val pedalColor = when (type) {
        EffectBlockType.NR -> if (isEnabled) Color(0xFFF1F5F9) else Color(0xFF334155) // White
        EffectBlockType.PRE -> if (isEnabled) Color(0xFFD946EF) else Color(0xFF334155) // Purple/Magenta
        EffectBlockType.WAH -> if (isEnabled) Color(0xFF64748B) else Color(0xFF334155) // Grey Expression
        EffectBlockType.DST -> if (isEnabled) Color(0xFF64748B) else Color(0xFF334155) // Dark Grey
        EffectBlockType.NS -> if (isEnabled) Color(0xFF64748B) else Color(0xFF334155) // Grey Dual Box
        EffectBlockType.AMP -> if (isEnabled) Color(0xFFF59E0B) else Color(0xFF334155) // Orange Amp Head
        EffectBlockType.CAB -> if (isEnabled) Color(0xFFF97316) else Color(0xFF334155) // Orange Cab
        EffectBlockType.EQ -> if (isEnabled) Color(0xFFFEF08A) else Color(0xFF334155) // Cream Equalizer
        EffectBlockType.MOD -> if (isEnabled) Color(0xFF06B6D4) else Color(0xFF334155) // Cyan
        EffectBlockType.DLY -> if (isEnabled) Color(0xFF64748B) else Color(0xFF334155) // Grey Delay
        EffectBlockType.RVB -> if (isEnabled) Color(0xFF2563EB) else Color(0xFF334155) // Royal Blue
        EffectBlockType.VOL -> if (isEnabled) Color(0xFF10B981) else Color(0xFF334155) // Green Expression
    }

    val labelTextColor = when {
        !isEnabled -> Color(0xFF94A3B8)
        type == EffectBlockType.NR || type == EffectBlockType.EQ -> Color(0xFF0F172A)
        else -> Color.White
    }

    Box(
        modifier = modifier
            .size(width = 52.dp, height = 66.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) Color(0xFF1E3A8A) else Color.Transparent)
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) ValetonCyan else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(2.dp)
            .testTag("valeton_block_${type.name.lowercase()}"),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(46.dp, 58.dp)) {
            val w = size.width
            val h = size.height

            when (type) {
                EffectBlockType.AMP -> {
                    // Vintage Amp Head Shape
                    drawRoundRect(
                        color = pedalColor,
                        topLeft = Offset(0f, 0f),
                        size = Size(w, h),
                        cornerRadius = CornerRadius(6f, 6f)
                    )
                    // Grill cloth
                    drawRoundRect(
                        color = Color(0xFF1E293B),
                        topLeft = Offset(4f, 4f),
                        size = Size(w - 8f, h * 0.45f),
                        cornerRadius = CornerRadius(3f, 3f)
                    )
                    // White knob dots
                    for (k in 0 until 5) {
                        drawCircle(
                            color = Color.White,
                            radius = 1.5.dp.toPx(),
                            center = Offset(8f + k * (w - 16f) / 4f, 10f)
                        )
                    }
                }
                EffectBlockType.CAB -> {
                    // Speaker Cabinet Shape
                    drawRoundRect(
                        color = pedalColor,
                        topLeft = Offset(0f, 0f),
                        size = Size(w, h),
                        cornerRadius = CornerRadius(6f, 6f)
                    )
                    // Speaker cone circle
                    drawCircle(
                        color = Color(0xFF1E293B),
                        radius = (w - 12f) / 2f,
                        center = Offset(w / 2f, h * 0.42f)
                    )
                    drawCircle(
                        color = Color(0xFF475569),
                        radius = (w - 24f) / 2f,
                        center = Offset(w / 2f, h * 0.42f)
                    )
                }
                EffectBlockType.WAH, EffectBlockType.VOL -> {
                    // Expression pedal treadle
                    drawRoundRect(
                        color = pedalColor,
                        topLeft = Offset(0f, 0f),
                        size = Size(w, h),
                        cornerRadius = CornerRadius(6f, 6f)
                    )
                    // Tread lines
                    for (t in 0 until 4) {
                        drawLine(
                            color = Color(0xFF1E293B),
                            start = Offset(6f, 10f + t * 7f),
                            end = Offset(w - 6f, 10f + t * 7f),
                            strokeWidth = 2.dp.toPx()
                        )
                    }
                }
                EffectBlockType.EQ -> {
                    // Equalizer pedal with sliders
                    drawRoundRect(
                        color = pedalColor,
                        topLeft = Offset(0f, 0f),
                        size = Size(w, h),
                        cornerRadius = CornerRadius(6f, 6f)
                    )
                    // Vertical slider slots
                    for (s in 0 until 5) {
                        val sx = 8f + s * (w - 16f) / 4f
                        drawLine(
                            color = Color(0xFF1E293B),
                            start = Offset(sx, 6f),
                            end = Offset(sx, h * 0.52f),
                            strokeWidth = 1.5.dp.toPx()
                        )
                        // White slider cap
                        drawCircle(
                            color = Color.White,
                            radius = 2.dp.toPx(),
                            center = Offset(sx, 12f + (s % 3) * 6f)
                        )
                    }
                }
                EffectBlockType.NS -> {
                    // Routing dual node
                    drawRoundRect(
                        color = pedalColor,
                        topLeft = Offset(0f, 0f),
                        size = Size(w, h),
                        cornerRadius = CornerRadius(6f, 6f)
                    )
                    // Routing node circles
                    for (c in 0 until 3) {
                        drawCircle(
                            color = Color(0xFF94A3B8),
                            radius = 3.dp.toPx(),
                            center = Offset(10f + c * 13f, 12f)
                        )
                        drawCircle(
                            color = Color(0xFF94A3B8),
                            radius = 3.dp.toPx(),
                            center = Offset(10f + c * 13f, 22f)
                        )
                    }
                }
                else -> {
                    // Standard Stompbox (NR, PRE, DST, MOD, DLY, RVB)
                    drawRoundRect(
                        color = pedalColor,
                        topLeft = Offset(0f, 0f),
                        size = Size(w, h),
                        cornerRadius = CornerRadius(6f, 6f)
                    )
                    // Rotary knobs
                    val knobCount = if (type == EffectBlockType.PRE || type == EffectBlockType.NR) 1 else if (type == EffectBlockType.RVB) 4 else 3
                    if (knobCount == 1) {
                        drawCircle(
                            color = if (type == EffectBlockType.NR) Color(0xFF1E293B) else Color.White,
                            radius = 6.dp.toPx(),
                            center = Offset(w / 2f, 16f)
                        )
                    } else if (knobCount == 3) {
                        drawCircle(color = Color(0xFF1E293B), radius = 3.5.dp.toPx(), center = Offset(12f, 12f))
                        drawCircle(color = Color(0xFF1E293B), radius = 3.5.dp.toPx(), center = Offset(w - 12f, 12f))
                        drawCircle(color = Color(0xFF1E293B), radius = 3.5.dp.toPx(), center = Offset(w / 2f, 22f))
                    } else {
                        // 4 knobs for RVB
                        drawCircle(color = Color.White, radius = 3.dp.toPx(), center = Offset(12f, 10f))
                        drawCircle(color = Color.White, radius = 3.dp.toPx(), center = Offset(w - 12f, 10f))
                        drawCircle(color = Color.White, radius = 3.dp.toPx(), center = Offset(12f, 20f))
                        drawCircle(color = Color.White, radius = 3.dp.toPx(), center = Offset(w - 12f, 20f))
                    }
                }
            }
        }

        // Label on pedal faceplate (bottom)
        Text(
            text = type.code,
            fontSize = 11.sp,
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily.Monospace,
            color = labelTextColor,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 6.dp)
        )
    }
}
