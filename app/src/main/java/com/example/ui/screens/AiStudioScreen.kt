package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BassPickupType
import com.example.model.GuitarPickupType
import com.example.model.InstrumentType
import com.example.model.ToneDestination
import com.example.ui.AiStatus
import com.example.ui.MainViewModel
import com.example.ui.components.InstrumentModeSelector
import com.example.ui.components.WaveformVisualizer
import com.example.ui.i18n.AppLanguage
import com.example.ui.i18n.AppStrings
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.ValetonAmberDrive
import com.example.ui.theme.ValetonCyan
import com.example.ui.theme.ValetonElectricBlue
import com.example.ui.theme.ValetonFireRed
import com.example.ui.theme.ValetonLimeGreen
import com.example.ui.theme.ValetonNavyCard
import com.example.ui.theme.ValetonNavyDark
import com.example.ui.theme.ValetonNavyElevated
import com.example.ui.theme.ValetonNavySuit
import com.example.ui.theme.ValetonNavySurface

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AiStudioScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val currentLanguage by viewModel.currentLanguage.collectAsState()
    val s = AppStrings.get(currentLanguage)
    val prompt by viewModel.aiPrompt.collectAsState()
    val instrument by viewModel.selectedInstrument.collectAsState()
    val pickup by viewModel.selectedPickup.collectAsState()
    val bassPickup by viewModel.selectedBassPickup.collectAsState()
    val genre by viewModel.selectedGenre.collectAsState()
    val destination by viewModel.selectedDestination.collectAsState()
    val aiStatus by viewModel.aiStatus.collectAsState()
    val currentPreset by viewModel.currentPreset.collectAsState()
    val isPlaying by viewModel.isAudioPlaying.collectAsState()
    val waveformData by viewModel.waveformData.collectAsState()

    var studioMode by remember { mutableStateOf("Builder") }

    val guitarInspirations = listOf(
        "John Mayer Slow Dancing Strat clean",
        "Polyphia Playing God Acoustic Breakup",
        "Metallica Master Djent Chug",
        "Slash Sweet Child Crunch",
        "Slowdive Shoegaze Lush Ambient",
        "SRV Texas Flood Blues Overdrive",
        "Sheila On 7 / Pop Rock Clean Chime",
        "Ichika Nito Math Tapping Clarity"
    )

    val bassInspirations = listOf(
        "Flea RHCP Slap Funk Punch (Ampeg SVT)",
        "Marcus Miller J-Bass Funk Slap Growl",
        "Darkglass Modern Metal Chug Bass",
        "Ampeg SVT Classic Rock Warm Thump",
        "Motown James Jamerson P-Bass Flatwound",
        "Iron Maiden Steve Harris Gallop Clank",
        "Jaco Pastorius Fretless Chorus Mids",
        "Muse Hysteria Synth Bass Fuzz"
    )

    if (studioMode == "YouTube") {
        Column(modifier = modifier.fillMaxSize()) {
            // Mode Selector Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(ValetonNavyCard)
                    .padding(3.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.Transparent)
                        .clickable { studioMode = "Builder" },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = s.modeBuilder,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextSecondary
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(ValetonCyan)
                        .clickable { studioMode = "YouTube" },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = s.modeMatcher,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = ValetonNavyDark
                    )
                }
            }

            YouTubeToneMatcherScreen(viewModel = viewModel)
        }
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ValetonNavySuit)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Mode Selector Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(ValetonNavyCard)
                .padding(3.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(36.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(ValetonCyan)
                    .clickable { studioMode = "Builder" },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = s.modeBuilder,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = ValetonNavyDark
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(36.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.Transparent)
                    .clickable { studioMode = "YouTube" },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = s.modeMatcher,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))
        // AI Hero Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(ValetonNavyCard, ValetonNavySurface)
                    )
                )
                .border(1.dp, ValetonCyan.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
                .padding(14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(ValetonCyan.copy(alpha = 0.15f))
                        .border(1.dp, ValetonCyan, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.AutoAwesome,
                        contentDescription = "AI",
                        tint = ValetonCyan,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = s.aiStudioTitle,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = s.aiStudioSubtitle,
                        fontSize = 11.sp,
                        color = TextSecondary,
                        lineHeight = 15.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Instrument Mode RadioGroup / Segmented Control
        InstrumentModeSelector(
            selectedInstrument = instrument,
            onInstrumentSelected = { newInstrument ->
                viewModel.setInstrument(newInstrument)
            },
            currentLanguage = currentLanguage
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Prompt Input
        Text(
            text = if (currentLanguage == AppLanguage.INDONESIAN) {
                if (instrument == InstrumentType.BASS) "Target Karakter Nada Bass atau Artis / Lagu:"
                else "Target Karakter Nada Gitar atau Artis / Lagu:"
            } else {
                if (instrument == InstrumentType.BASS) "Target Bass Tone or Artist / Song:"
                else "Target Guitar Tone or Artist / Song:"
            },
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = ValetonCyan
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = prompt,
            onValueChange = { viewModel.aiPrompt.value = it },
            placeholder = {
                Text(
                    text = if (currentLanguage == AppLanguage.INDONESIAN) {
                        if (instrument == InstrumentType.BASS) "contoh: Flea RHCP slap funk punch dengan Ampeg SVT"
                        else "contoh: John Mayer Slow Dancing Strat clean breakup dengan delay lembut"
                    } else {
                        if (instrument == InstrumentType.BASS) "e.g., Flea RHCP slap funk punch with Ampeg SVT"
                        else "e.g., John Mayer scoop clean with lush reverb"
                    },
                    color = TextMuted
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("ai_prompt_input"),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ValetonCyan,
                unfocusedBorderColor = ValetonNavyElevated,
                focusedContainerColor = ValetonNavyCard,
                unfocusedContainerColor = ValetonNavyCard,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
            ),
            shape = RoundedCornerShape(10.dp),
            maxLines = 2
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Quick Suggestion Chips
        Text(
            text = s.quickPromptsTitle,
            fontSize = 11.sp,
            color = TextMuted
        )

        Spacer(modifier = Modifier.height(4.dp))

        val activeInspirations = if (instrument == InstrumentType.BASS) bassInspirations else guitarInspirations

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            activeInspirations.forEach { inspiration ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (prompt == inspiration) ValetonElectricBlue else ValetonNavyCard)
                        .border(
                            1.dp,
                            if (prompt == inspiration) ValetonCyan else ValetonNavyElevated,
                            RoundedCornerShape(16.dp)
                        )
                        .clickable { viewModel.aiPrompt.value = inspiration }
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = inspiration,
                        fontSize = 11.sp,
                        color = if (prompt == inspiration) Color.White else TextSecondary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Pickup Selection (Guitar or Bass)
        Text(
            text = if (instrument == InstrumentType.BASS) s.bassPickupLabel else s.pickupLabel,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = ValetonCyan
        )

        Spacer(modifier = Modifier.height(6.dp))

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            if (instrument == InstrumentType.BASS) {
                BassPickupType.values().forEach { pick ->
                    val isSelected = bassPickup == pick
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) ValetonNavySurface else ValetonNavyCard)
                            .border(
                                1.dp,
                                if (isSelected) ValetonCyan else ValetonNavyElevated,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable { viewModel.selectedBassPickup.value = pick }
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) ValetonCyan else Color.Transparent)
                                .border(1.5.dp, if (isSelected) ValetonCyan else TextMuted, CircleShape)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            Text(
                                text = pick.displayName,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else TextPrimary
                            )
                            Text(
                                text = pick.desc,
                                fontSize = 10.sp,
                                color = TextSecondary
                            )
                        }
                    }
                }
            } else {
                GuitarPickupType.values().forEach { pick ->
                    val isSelected = pickup == pick
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) ValetonNavySurface else ValetonNavyCard)
                            .border(
                                1.dp,
                                if (isSelected) ValetonCyan else ValetonNavyElevated,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable { viewModel.selectedPickup.value = pick }
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) ValetonCyan else Color.Transparent)
                                .border(1.5.dp, if (isSelected) ValetonCyan else TextMuted, CircleShape)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            Text(
                                text = pick.displayName,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else TextPrimary
                            )
                            Text(
                                text = pick.desc,
                                fontSize = 10.sp,
                                color = TextSecondary
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Output Destination (Direct to PA / Amp Front / FX Loop)
        Text(
            text = s.destinationLabel,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = ValetonCyan
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ToneDestination.values().forEach { dest ->
                val isSelected = destination == dest
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) ValetonElectricBlue.copy(alpha = 0.25f) else ValetonNavyCard)
                        .border(
                            1.dp,
                            if (isSelected) ValetonElectricBlue else ValetonNavyElevated,
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { viewModel.selectedDestination.value = dest }
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = when (dest) {
                            ToneDestination.DIRECT_PA -> if (currentLanguage == AppLanguage.INDONESIAN) "PA Langsung (IR ON)" else "Direct PA (IR ON)"
                            ToneDestination.AMP_FRONT -> if (currentLanguage == AppLanguage.INDONESIAN) "Input Depan Amp" else "Amp In (IR OFF)"
                            ToneDestination.FX_LOOP_RETURN -> if (currentLanguage == AppLanguage.INDONESIAN) "Return FX Loop" else "FX Return"
                        },
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) ValetonCyan else TextSecondary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Action Buttons: Generate & Auto-Optimize
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = { viewModel.generateAiPreset() },
                modifier = Modifier
                    .weight(1.2f)
                    .height(48.dp)
                    .testTag("generate_ai_preset_button"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ValetonCyan,
                    contentColor = ValetonNavyDark
                ),
                shape = RoundedCornerShape(10.dp),
                enabled = aiStatus !is AiStatus.Processing
            ) {
                if (aiStatus is AiStatus.Processing) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = ValetonNavyDark,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(s.optimizingText, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                } else {
                    Icon(
                        imageVector = Icons.Filled.AutoAwesome,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (currentLanguage == AppLanguage.INDONESIAN) "HASILKAN PRESET AI" else "SYNTHESIZE PRESET",
                        fontWeight = FontWeight.Black,
                        fontSize = 11.sp
                    )
                }
            }

            OutlinedButton(
                onClick = { viewModel.autoOptimizeCurrentTone() },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("auto_optimize_button"),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = ValetonAmberDrive
                ),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, ValetonAmberDrive),
                shape = RoundedCornerShape(10.dp),
                enabled = aiStatus !is AiStatus.Processing
            ) {
                Icon(
                    imageVector = Icons.Filled.Speed,
                    contentDescription = null,
                    tint = ValetonAmberDrive,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (currentLanguage == AppLanguage.INDONESIAN) "OPTIMASI NADA" else "AUTO-TRIM",
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Real-time Waveform Preview Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(ValetonNavyCard)
                .border(1.dp, ValetonNavyElevated, RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.GraphicEq,
                        contentDescription = null,
                        tint = ValetonCyan,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Tone Simulation Response",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (isPlaying) "SIMULATING DSP" else "READY",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = if (isPlaying) ValetonCyan else TextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            WaveformVisualizer(
                waveformData = waveformData,
                isPlaying = isPlaying,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Riff selector audition buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf("Auto", "Chug", "Solo", "Ambient").forEach { riff ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(6.dp))
                            .background(ValetonNavyDark)
                            .border(1.dp, ValetonNavyElevated, RoundedCornerShape(6.dp))
                            .clickable { viewModel.testAudioPreview(riff) }
                            .padding(vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "▶ $riff",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = ValetonCyan
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // AI Tuning Insights Card
        if (currentPreset.aiOptimizationNotes.isNotBlank()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(ValetonNavyCard.copy(alpha = 0.9f))
                    .border(1.dp, ValetonLimeGreen.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Psychology,
                            contentDescription = null,
                            tint = ValetonLimeGreen,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (currentLanguage == AppLanguage.INDONESIAN) "Telemetri DSP Cloud Valeton GP-180" else "Valeton GP-180 Cloud AI Telemetry",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = ValetonLimeGreen
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = currentPreset.aiOptimizationNotes,
                        fontSize = 11.sp,
                        color = TextPrimary,
                        lineHeight = 16.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
