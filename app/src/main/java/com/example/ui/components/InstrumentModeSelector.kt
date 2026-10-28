package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.InstrumentType
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.ValetonAmberDrive
import com.example.ui.theme.ValetonCyan
import com.example.ui.theme.ValetonElectricBlue
import com.example.ui.theme.ValetonNavyCard
import com.example.ui.theme.ValetonNavyDark
import com.example.ui.theme.ValetonNavyElevated
import com.example.ui.theme.ValetonNavySurface

import com.example.ui.i18n.AppLanguage
import com.example.ui.i18n.AppStrings

/**
 * Segmented Control & RadioGroup for switching between Guitar and Bass modes
 * in the AI Tone Studio / Preset Builder.
 */
@Composable
fun InstrumentModeSelector(
    selectedInstrument: InstrumentType,
    onInstrumentSelected: (InstrumentType) -> Unit,
    currentLanguage: AppLanguage = AppLanguage.INDONESIAN,
    modifier: Modifier = Modifier
) {
    val s = AppStrings.get(currentLanguage)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("instrument_radio_group")
    ) {
        // Label header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.MusicNote,
                    contentDescription = null,
                    tint = ValetonCyan,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Mode Instrumen (Input Source):",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ValetonCyan
                )
            }

            // Status chip
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(if (selectedInstrument == InstrumentType.BASS) ValetonAmberDrive.copy(alpha = 0.2f) else ValetonCyan.copy(alpha = 0.2f))
                    .border(
                        1.dp,
                        if (selectedInstrument == InstrumentType.BASS) ValetonAmberDrive.copy(alpha = 0.6f) else ValetonCyan.copy(alpha = 0.6f),
                        RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = if (selectedInstrument == InstrumentType.BASS) "DSP BASS ENGINE" else "DSP GUITAR ENGINE",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    color = if (selectedInstrument == InstrumentType.BASS) ValetonAmberDrive else ValetonCyan
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Segmented Control Row with RadioGroup Semantics
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(ValetonNavyCard)
                .border(1.5.dp, ValetonNavyElevated, RoundedCornerShape(12.dp))
                .padding(4.dp)
                .selectableGroup()
                .testTag("instrument_segmented_control"),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Option 1: GUITAR
            val isGuitarSelected = selectedInstrument == InstrumentType.GUITAR
            val guitarBgColor by animateColorAsState(
                targetValue = if (isGuitarSelected) ValetonCyan else Color.Transparent,
                animationSpec = tween(200),
                label = "guitarBg"
            )

            Row(
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .clip(RoundedCornerShape(9.dp))
                    .background(guitarBgColor)
                    .border(
                        width = if (isGuitarSelected) 1.5.dp else 0.dp,
                        color = if (isGuitarSelected) ValetonCyan else Color.Transparent,
                        shape = RoundedCornerShape(9.dp)
                    )
                    .selectable(
                        selected = isGuitarSelected,
                        onClick = { onInstrumentSelected(InstrumentType.GUITAR) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 10.dp)
                    .testTag("instrument_toggle_guitar")
                    .testTag("instrument_select_guitar")
                    .testTag("instrument_mode_guitar"),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                // Radio indicator
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .clip(CircleShape)
                        .background(if (isGuitarSelected) ValetonNavyDark else Color.Transparent)
                        .border(
                            2.dp,
                            if (isGuitarSelected) ValetonNavyDark else ValetonNavyElevated,
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isGuitarSelected) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(ValetonCyan)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "🎸 GITAR",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black,
                            color = if (isGuitarSelected) ValetonNavyDark else TextPrimary
                        )
                    }
                    Text(
                        text = "Electric / Acoustic",
                        fontSize = 10.sp,
                        fontWeight = if (isGuitarSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isGuitarSelected) ValetonNavyDark.copy(alpha = 0.85f) else TextSecondary
                    )
                }
            }

            // Option 2: BASS
            val isBassSelected = selectedInstrument == InstrumentType.BASS
            val bassBgColor by animateColorAsState(
                targetValue = if (isBassSelected) ValetonCyan else Color.Transparent,
                animationSpec = tween(200),
                label = "bassBg"
            )

            Row(
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .clip(RoundedCornerShape(9.dp))
                    .background(bassBgColor)
                    .border(
                        width = if (isBassSelected) 1.5.dp else 0.dp,
                        color = if (isBassSelected) ValetonCyan else Color.Transparent,
                        shape = RoundedCornerShape(9.dp)
                    )
                    .selectable(
                        selected = isBassSelected,
                        onClick = { onInstrumentSelected(InstrumentType.BASS) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 10.dp)
                    .testTag("instrument_toggle_bass")
                    .testTag("instrument_select_bass")
                    .testTag("instrument_mode_bass"),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                // Radio indicator
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .clip(CircleShape)
                        .background(if (isBassSelected) ValetonNavyDark else Color.Transparent)
                        .border(
                            2.dp,
                            if (isBassSelected) ValetonNavyDark else ValetonNavyElevated,
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isBassSelected) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(ValetonCyan)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "🎸 BASS",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black,
                            color = if (isBassSelected) ValetonNavyDark else TextPrimary
                        )
                    }
                    Text(
                        text = "4/5-String / Active",
                        fontSize = 10.sp,
                        fontWeight = if (isBassSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isBassSelected) ValetonNavyDark.copy(alpha = 0.85f) else TextSecondary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Context helper text explaining the optimization difference
        Text(
            text = if (selectedInstrument == InstrumentType.BASS) {
                "⚡ Mode Bass: Mengoptimalkan modul Ampeg SVT, Darkglass B7K drive, kompresi transient slap, dan cut low-end 35Hz untuk dentuman bass yang punchy."
            } else {
                "⚡ Mode Gitar: Mengoptimalkan headroom preamp, stack kabinet IR Celestion/Jensen, gain staging overdrive, dan scoop frekuensi 350Hz."
            },
            fontSize = 11.sp,
            color = if (selectedInstrument == InstrumentType.BASS) ValetonAmberDrive else TextSecondary,
            lineHeight = 15.sp
        )
    }
}
