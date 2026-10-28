package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.EffectBlockType
import com.example.model.ValetonCatalog
import com.example.ui.MainViewModel
import com.example.ui.components.ValetonAuthenticChain
import com.example.ui.components.ValetonSliderRow
import com.example.ui.i18n.AppLanguage
import com.example.ui.i18n.AppStrings
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.ValetonAmberDrive
import com.example.ui.theme.ValetonCyan
import com.example.ui.theme.ValetonElectricBlue
import com.example.ui.theme.ValetonLimeGreen
import com.example.ui.theme.ValetonNavyCard
import com.example.ui.theme.ValetonNavyDark
import com.example.ui.theme.ValetonNavyElevated
import com.example.ui.theme.ValetonNavySuit

@Composable
fun ChainEditorScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val currentLanguage by viewModel.currentLanguage.collectAsState()
    val s = AppStrings.get(currentLanguage)
    val preset by viewModel.currentPreset.collectAsState()
    val localPresets by viewModel.localPresets.collectAsState()
    val selectedType by viewModel.selectedBlockType.collectAsState()
    val isPlaying by viewModel.isAudioPlaying.collectAsState()

    var showModelPicker by remember { mutableStateOf(false) }
    var activeSubTab by remember { mutableStateOf("Edit") }

    val currentBlock = preset.blocks.find { it.type == selectedType } ?: preset.blocks.first()
    val catalogModels = ValetonCatalog.getModelsForType(currentBlock.type)
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0A1220)) // Authentic Valeton deep chassis color
            .verticalScroll(scrollState)
            .padding(14.dp)
    ) {
        // Top Valeton Header Bar: [192 | terbang] [<] [>] [Save]
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Preset Indicator Pill (Matching user's screenshot: 192 | terbang)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(42.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(Color(0xFF1E293B))
                    .border(1.dp, Color(0xFF334155), RoundedCornerShape(22.dp))
                    .padding(horizontal = 14.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = preset.patchNumber,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "  |  ",
                        fontSize = 15.sp,
                        color = Color(0xFF64748B)
                    )
                    Text(
                        text = preset.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 1
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Navigation Arrows [<] [>]
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = {
                        val currentIndex = localPresets.indexOfFirst { it.id == preset.id }
                        if (currentIndex > 0) {
                            viewModel.loadPreset(localPresets[currentIndex - 1])
                        }
                    },
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF1E293B))
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "Previous Preset",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                IconButton(
                    onClick = {
                        val currentIndex = localPresets.indexOfFirst { it.id == preset.id }
                        if (currentIndex != -1 && currentIndex < localPresets.size - 1) {
                            viewModel.loadPreset(localPresets[currentIndex + 1])
                        }
                    },
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF1E293B))
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowForwardIos,
                        contentDescription = "Next Preset",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                IconButton(
                    onClick = { viewModel.saveCurrentPreset() },
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF1E293B))
                ) {
                    Icon(
                        imageVector = Icons.Filled.Save,
                        contentDescription = "Save Preset",
                        tint = ValetonCyan,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Segmented Tabs: [Edit] | [Patch Settings / Specs]
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF1E293B))
                .padding(3.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(38.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(if (activeSubTab == "Edit") Color(0xFF2563EB) else Color.Transparent)
                    .clickable { activeSubTab = "Edit" },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = s.chainEditorSubtabEdit,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(38.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(if (activeSubTab == "Patch Settings") Color(0xFF2563EB) else Color.Transparent)
                    .clickable { activeSubTab = "Patch Settings" },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = s.chainEditorSubtabPatch,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (activeSubTab == "Patch Settings") Color.White else Color(0xFF94A3B8)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (activeSubTab == "Patch Settings") {
            // Full Official 12-Module Documentation & Guide
            ValetonModuleOfficialGuide()
            return
        }

        // Authentic 2-Row Signal Chain with Curved Blue Connecting Cable
        ValetonAuthenticChain(
            blocks = preset.blocks,
            selectedType = selectedType,
            onSelectBlock = { type -> viewModel.selectBlock(type) }
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Selected Block Header Card (Matching screenshot: [NR] [Toggle Switch] | [Model Card >])
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Card: Block Code + Toggle Switch
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(58.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF1E293B))
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = currentBlock.type.code,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace,
                            color = Color.White
                        )
                        if (currentBlock.type == EffectBlockType.NS) {
                            Text(
                                text = "SnapTone NAM",
                                fontSize = 9.sp,
                                color = ValetonCyan
                            )
                        }
                    }

                    Switch(
                        checked = currentBlock.enabled,
                        onCheckedChange = { viewModel.toggleBlock(currentBlock.type) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Color(0xFF2563EB),
                            uncheckedThumbColor = Color(0xFF94A3B8),
                            uncheckedTrackColor = Color(0xFF0F172A)
                        ),
                        modifier = Modifier.testTag("switch_${currentBlock.type.code.lowercase()}")
                    )
                }
            }

            // Right Card: Model Name + Variant + Chevron Arrow (Matching user screenshot)
            Box(
                modifier = Modifier
                    .weight(1.8f)
                    .height(58.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF1E293B))
                    .clickable { showModelPicker = !showModelPicker }
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Small icon
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF0F172A)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "•",
                                color = ValetonCyan,
                                fontSize = 16.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            Text(
                                text = if (currentBlock.type == EffectBlockType.NS) "SnapTone (NAM)" else currentBlock.type.fullName.split("/").first().trim(),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = currentBlock.selectedModel,
                                fontSize = 11.sp,
                                color = Color(0xFF94A3B8),
                                maxLines = 1
                            )
                        }
                    }

                    Icon(
                        imageVector = if (showModelPicker) Icons.Filled.ExpandLess else Icons.Filled.ChevronRight,
                        contentDescription = "Select Model",
                        tint = Color(0xFF94A3B8),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // Dropdown / Model Selector from Official Valeton Catalog
        if (showModelPicker) {
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF172033))
                    .border(1.dp, Color(0xFF334155), RoundedCornerShape(10.dp))
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Official Models for ${currentBlock.type.code}:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = ValetonCyan
                    )
                    Text(
                        text = "${catalogModels.size} Models",
                        fontSize = 10.sp,
                        color = TextSecondary
                    )
                }

                catalogModels.forEach { item ->
                    val isSelected = item.name == currentBlock.selectedModel
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) Color(0xFF2563EB) else Color(0xFF1E293B))
                            .clickable {
                                viewModel.updateBlockModel(currentBlock.type, item.name, item.defaultParams)
                                showModelPicker = false
                            }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = item.name,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else TextPrimary
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(if (isSelected) Color(0xFF1E40AF) else Color(0xFF0F172A))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = item.category,
                                    fontSize = 9.sp,
                                    color = if (isSelected) Color.White else ValetonCyan
                                )
                            }
                        }

                        Text(
                            text = item.emulatedGear,
                            fontSize = 10.sp,
                            color = if (isSelected) Color.White.copy(alpha = 0.85f) else ValetonAmberDrive
                        )

                        Text(
                            text = item.description,
                            fontSize = 10.sp,
                            color = if (isSelected) Color.White.copy(alpha = 0.7f) else TextSecondary,
                            lineHeight = 13.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Authentic Parameter Slider Rows (e.g. Threshold: 20 in screenshot)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            currentBlock.parameters.forEach { (paramName, paramVal) ->
                val range = when (paramName) {
                    "Time" -> 20f..1500f
                    "High Cut" -> 4000f..12000f
                    "Low Cut" -> 40f..200f
                    "100Hz", "200Hz", "350Hz", "400Hz", "800Hz", "1kHz", "1.6kHz", "3.2kHz", "6.4kHz", "8kHz" -> -12f..12f
                    "Pitch" -> -24f..24f
                    else -> 0f..100f
                }
                val unit = when (paramName) {
                    "Time" -> "ms"
                    "High Cut", "Low Cut" -> "Hz"
                    "100Hz", "200Hz", "350Hz", "400Hz", "800Hz", "1kHz", "1.6kHz", "3.2kHz", "6.4kHz", "8kHz" -> "dB"
                    "Pitch" -> "st"
                    else -> ""
                }

                ValetonSliderRow(
                    label = paramName,
                    value = paramVal,
                    range = range,
                    unit = unit,
                    onValueChange = { newVal ->
                        viewModel.updateBlockParameter(currentBlock.type, paramName, newVal)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Quick Audition & AI Auto-Optimize Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { viewModel.autoOptimizeCurrentTone() },
                modifier = Modifier
                    .weight(1.2f)
                    .height(44.dp)
                    .testTag("chain_auto_optimize_button"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ValetonCyan,
                    contentColor = ValetonNavyDark
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.AutoAwesome,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (currentLanguage == AppLanguage.INDONESIAN) "OPTIMASI NADA" else "AI AUTO-TRIM",
                    fontWeight = FontWeight.Black,
                    fontSize = 11.sp
                )
            }

            OutlinedButton(
                onClick = { viewModel.testAudioPreview("Auto") },
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
                    .testTag("chain_test_audio_button"),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = if (isPlaying) ValetonAmberDrive else Color.White
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isPlaying) ValetonAmberDrive else Color(0xFF334155)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Filled.Stop else Icons.Filled.PlayArrow,
                    contentDescription = null,
                    tint = if (isPlaying) ValetonAmberDrive else ValetonCyan,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (isPlaying) s.stopTone else s.testTone,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun ValetonModuleOfficialGuide() {
    val modules = listOf(
        "1. NR (Noise Reduction)" to "Smart Noise Gate digital untuk memotong hum elektrik.\n• Parameter: Thre (Threshold), Attack, Release.",
        "2. PRE (Pre-effects)" to "Dinamika awal sebelum ampli & distorsi.\n• Isi: S-Comp (Ross), Keeley C4, Dyna Comp, Limiter, Micro Boost, Xotic BB Pre, Fortin 14 Boost, Acoustic Sim.\n• Parameter: Sustn/Thre, Attack, Volume, Tone.",
        "3. WAH (Wah-wah & Pitch FX)" to "Filter injak & manipulasi nada.\n• Isi: CryBaby, Vox V847, Petrucci Wah, Whammy Pitch Bend, Octaver, Detune.\n• Parameter: Position, Range, Pitch, Mix.",
        "4. DST (Distortion / Overdrive)" to "26 emulasi pedal drive analog dunia.\n• Overdrive: Green OD (TS808), OD 9 (TS9), Yellow OD (SD-1), Penesas (Klon Centaur), Force (OCD), TaiChi (Zendrive).\n• Distortion & Fuzz: Darktale (RAT), Chief (Guv'nor), Plustortion (MXR Dist+), Lazaro (Big Muff), Red Haze (Fuzz Face).\n• Bass: Black Bass (Darkglass B7K), Bass Hammer.\n• Parameter: Gain/Drive, Tone/EQ, Volume.",
        "5. SnapTone (N➔S)" to "Modul Neural Amp Modeler (NAM) generasi baru.\n• Isi: 50 Factory NAM (EVH 5150 III, Revv Generator 120, Victory Kraken, King of Tone) + slot untuk 100 User NAM.\n• Parameter: Input Gain, Output Vol, Bass, Middle, Treble.",
        "6. AMP (Amplifier Modeling)" to "58 model ampli HD Gen-2 (52 gitar, 5 bass, 1 akustik).\n• Clean/Crunch: '65 Twin Reverb, Tweed Deluxe, Vox AC30, Matchless, Roland JC-120.\n• High-Gain: JCM800, Mesa Dual Rectifier, Peavey 5150, Friedman BE-50, Diezel VH4.\n• Bass: Ampeg SVT, Bassman, AC Preamp.\n• Parameter: Gain, Master, Bass, Middle, Treble, Presence.",
        "7. CAB (Cabinet Simulation)" to "Speaker sim & penempatan mic + 20 Slot User IR (1024/2048 pts).\n• Model: 1x10, 1x12, 2x12 US Jensen, 4x12 Marshall Greenback, 4x12 Mesa Recto V30.\n• Parameter: Mic Type (SM57/U87/MD421), Distance, Center, Low Cut, High Cut.",
        "8. EQ (Equalizer)" to "Pembersih dan pembentuk frekuensi suara.\n• Isi: 5-Band Guitar EQ, 7-Band Graphic, Parametric Studio EQ dengan Q factor.\n• Parameter: Band Gains, Q, Low Cut, High Cut, Level.",
        "9. MOD (Modulation)" to "Dimensi dan pergerakan modulasi.\n• Isi: Analog Chorus, Stereo Chorus, Flanger, Phase 90, Tremolo, Vibrato, Rotary, Uni-Vibe.\n• Parameter: Rate/Speed, Depth, Tone, Mix.",
        "10. DLY (Delay)" to "Efek gema dan pantulan nada.\n• Isi: Digital Delay, Analog BBD, Tape Echo, Ping Pong Stereo, Reverse Delay, Dual Delay.\n• Parameter: Time (sync Tap Tempo), Feedback, Tone, Mix.",
        "11. RVB (Reverb)" to "Atmosfer dan ruang gema.\n• Isi: Room, Hall, Church, Plate, Spring 63, Shimmer Reverb, Cloud Space.\n• Parameter: Decay, Pre-delay, Tone, Mix.",
        "12. VOL (Volume)" to "Kendali level ekspresi linier / logaritmik.\n• Parameter: Min Volume, Max Volume, Curve."
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF1E293B))
            .border(1.dp, Color(0xFF334155), RoundedCornerShape(12.dp))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = null,
                tint = ValetonCyan,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "Valeton GP-180 Architecture (12 Modul)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Lebih dari 200+ efek HD & Neural Amp Modeler (NAM)",
                    fontSize = 11.sp,
                    color = ValetonCyan
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        modules.forEach { (title, desc) ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF0F172A))
                    .padding(10.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = ValetonAmberDrive
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = desc,
                    fontSize = 11.sp,
                    color = TextPrimary,
                    lineHeight = 15.sp
                )
            }
        }
    }
}
