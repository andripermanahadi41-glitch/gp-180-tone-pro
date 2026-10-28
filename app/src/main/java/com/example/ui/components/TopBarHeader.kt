package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.ValetonPreset
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.ValetonAmberDrive
import com.example.ui.theme.ValetonCyan
import com.example.ui.theme.ValetonElectricBlue
import com.example.ui.theme.ValetonNavyCard
import com.example.ui.theme.ValetonNavyDark
import com.example.ui.theme.ValetonNavyElevated
import com.example.ui.theme.ValetonNavySuit

import com.example.ui.i18n.AppLanguage
import com.example.ui.i18n.AppStrings

@Composable
fun TopBarHeader(
    currentPreset: ValetonPreset,
    isPlaying: Boolean,
    onTogglePlay: () -> Unit,
    currentLanguage: AppLanguage = AppLanguage.INDONESIAN,
    onToggleLanguage: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val s = AppStrings.get(currentLanguage)
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(ValetonNavyDark, ValetonNavySuit)
                )
            )
            .border(
                width = 1.dp,
                color = ValetonNavyElevated,
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Logo & Title
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(ValetonNavyCard)
                        .border(1.5.dp, ValetonCyan, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.gp180_pro_icon_1793169994236),
                        contentDescription = "GP180 Pro Logo",
                        modifier = Modifier.size(42.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "GP180 PRO",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(ValetonCyan)
                                .padding(horizontal = 5.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = s.aiCloudTag,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = ValetonNavyDark
                            )
                        }
                    }
                    Text(
                        text = s.suiteSubtitle,
                        fontSize = 11.sp,
                        color = ValetonCyan.copy(alpha = 0.85f),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Right Action Controls: Language Pill + Audio Preview Button
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Language Switcher Pill (ID / EN)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(ValetonNavyCard)
                        .border(
                            1.2.dp,
                            ValetonCyan.copy(alpha = 0.6f),
                            RoundedCornerShape(16.dp)
                        )
                        .clickable { onToggleLanguage() }
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                        .testTag("language_toggle_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = currentLanguage.flag,
                            fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (currentLanguage == AppLanguage.INDONESIAN) "ID" else "EN",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = ValetonCyan
                        )
                    }
                }

                // Quick Play/Audition Tone Button
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isPlaying) ValetonAmberDrive else ValetonNavyCard)
                        .border(
                            1.5.dp,
                            if (isPlaying) ValetonAmberDrive else ValetonCyan,
                            RoundedCornerShape(20.dp)
                        )
                        .clickable { onTogglePlay() }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                        .testTag("preview_audio_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Filled.Stop else Icons.Filled.PlayArrow,
                            contentDescription = if (isPlaying) s.stopTone else s.testTone,
                            tint = if (isPlaying) ValetonNavyDark else ValetonCyan,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isPlaying) s.stopTone else s.testTone,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isPlaying) ValetonNavyDark else TextPrimary
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Active Patch HUD Card
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(ValetonNavyCard.copy(alpha = 0.85f))
                .border(1.dp, ValetonNavyElevated, RoundedCornerShape(10.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Patch number badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(ValetonElectricBlue)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = currentPreset.patchNumber,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = currentPreset.name,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        maxLines = 1
                    )
                    Text(
                        text = "${currentPreset.genre} • ${currentPreset.guitarType}",
                        fontSize = 10.sp,
                        color = TextMuted,
                        maxLines = 1
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (currentPreset.aiOptimized) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(ValetonCyan.copy(alpha = 0.15f))
                            .border(1.dp, ValetonCyan.copy(alpha = pulseAlpha), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = s.aiOptimized,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = ValetonCyan
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                }

                Text(
                    text = "${currentPreset.bpm} BPM",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    color = ValetonAmberDrive,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun WaveformVisualizer(
    waveformData: FloatArray,
    isPlaying: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(34.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(ValetonNavyDark)
            .border(1.dp, ValetonNavyElevated, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxWidth().height(26.dp)) {
            val barCount = waveformData.size
            val barWidth = (size.width / barCount) * 0.65f
            val spacing = (size.width / barCount) * 0.35f

            for (i in 0 until barCount) {
                val value = if (isPlaying) waveformData[i] else 0.12f
                val barHeight = (size.height * value).coerceIn(4f, size.height)
                val x = i * (barWidth + spacing)
                val y = (size.height - barHeight) / 2f

                drawRoundRect(
                    brush = Brush.verticalGradient(
                        colors = if (isPlaying) listOf(ValetonCyan, ValetonElectricBlue)
                        else listOf(ValetonNavyElevated, ValetonNavyCard)
                    ),
                    topLeft = Offset(x, y),
                    size = Size(barWidth, barHeight),
                    cornerRadius = CornerRadius(2f, 2f)
                )
            }
        }
    }
}
