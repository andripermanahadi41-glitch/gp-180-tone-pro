package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SmartDisplay
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ai.YouTubeToneAnalyzer
import com.example.model.GuitarPickupType
import com.example.model.InstrumentType
import com.example.model.PlayingTechnique
import com.example.model.SpectralAnalysisData
import com.example.ui.AppTab
import com.example.ui.MainViewModel
import com.example.ui.components.InstrumentModeSelector
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

@Composable
fun YouTubeToneMatcherScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val currentLanguage by viewModel.currentLanguage.collectAsState()
    val s = AppStrings.get(currentLanguage)
    val instrument by viewModel.selectedInstrument.collectAsState()
    val pickup by viewModel.selectedPickup.collectAsState()

    val availableReferences = YouTubeToneAnalyzer.getReferencesForInstrument(instrument)
    var customUrlOrQuery by remember { mutableStateOf("") }
    var selectedReference by remember(instrument) { mutableStateOf(availableReferences.first()) }
    var selectedTechnique by remember { mutableStateOf(PlayingTechnique.VINTAGE_BLUES_BEND) }
    var isAnalyzing by remember { mutableStateOf(false) }
    var showAlgorithmDetails by remember { mutableStateOf(true) }

    val userSpectrum = YouTubeToneAnalyzer.calculatePickupSpectralProfile(pickup, selectedTechnique)
    val targetSpectrum = selectedReference.targetSpectrum

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ValetonNavySuit)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Modern Minimalist GP180 Pro Logo Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(ValetonNavyCard)
                .border(1.5.dp, ValetonCyan.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.gp180_pro_header_1793171181700),
                contentDescription = "GP180 Pro Modern Minimalist Banner",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Gradient Overlay for readability
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, ValetonNavyDark.copy(alpha = 0.85f))
                        )
                    )
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = "YOUTUBE TONE MATCHER AI",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp,
                            color = Color.White
                        )
                        Text(
                            text = "Acoustic Spectral Delta Engine for Valeton GP-180",
                            fontSize = 10.sp,
                            color = ValetonCyan
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(ValetonCyan)
                            .padding(horizontal = 6.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "DSP ALGO v2.4",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = ValetonNavyDark
                        )
                    }
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

        // YouTube Search or Paste URL
        Text(
            text = s.ytSelectPrompt,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = ValetonCyan
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = customUrlOrQuery,
            onValueChange = { customUrlOrQuery = it },
            placeholder = {
                Text(
                    text = if (currentLanguage == AppLanguage.INDONESIAN) {
                        if (instrument == InstrumentType.BASS) "Tempel URL YouTube atau cari (mis. Flea RHCP, Marcus Miller, Darkglass)"
                        else "Tempel URL YouTube atau cari (mis. Paul Davids, Ola Englund, John Mayer)"
                    } else {
                        if (instrument == InstrumentType.BASS) "Paste YouTube URL or search (e.g. Flea RHCP, Marcus Miller, Darkglass)"
                        else "Paste YouTube URL or search (e.g. Paul Davids, Ola Englund)"
                    },
                    color = TextMuted
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.SmartDisplay,
                    contentDescription = null,
                    tint = ValetonFireRed
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("youtube_search_input"),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ValetonCyan,
                unfocusedBorderColor = ValetonNavyElevated,
                focusedContainerColor = ValetonNavyCard,
                unfocusedContainerColor = ValetonNavyCard,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
            ),
            shape = RoundedCornerShape(10.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Curated YouTube Tone References
        Text(
            text = if (instrument == InstrumentType.BASS) "Curated YouTube Bass Tone References:" else "Curated YouTube Guitar Tone References:",
            fontSize = 11.sp,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(6.dp))

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            availableReferences.forEach { ref ->
                val isSelected = selectedReference.id == ref.id
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isSelected) ValetonNavySurface else ValetonNavyCard)
                        .border(
                            1.5.dp,
                            if (isSelected) ValetonCyan else ValetonNavyElevated,
                            RoundedCornerShape(10.dp)
                        )
                        .clickable {
                            selectedReference = ref
                            when (ref.id) {
                                "yt_01" -> {
                                    viewModel.selectedPickup.value = GuitarPickupType.SINGLE_COIL_STRAT
                                    selectedTechnique = PlayingTechnique.VINTAGE_BLUES_BEND
                                }
                                "yt_02" -> {
                                    viewModel.selectedPickup.value = GuitarPickupType.SINGLE_COIL_STRAT
                                    selectedTechnique = PlayingTechnique.HYBRID_PICKING
                                }
                                "yt_03" -> {
                                    viewModel.selectedPickup.value = GuitarPickupType.HUMBUCKER_LP
                                    selectedTechnique = PlayingTechnique.PALM_MUTING_CHUG
                                }
                                "yt_04" -> {
                                    viewModel.selectedPickup.value = GuitarPickupType.SINGLE_COIL_STRAT
                                    selectedTechnique = PlayingTechnique.VINTAGE_BLUES_BEND
                                }
                                "yt_05" -> {
                                    viewModel.selectedPickup.value = GuitarPickupType.HUMBUCKER_LP
                                    selectedTechnique = PlayingTechnique.LEAD_SHRED_LEGATO
                                }
                                "yt_bass_01" -> {
                                    viewModel.selectedBassPickup.value = com.example.model.BassPickupType.STINGRAY_ACTIVE
                                    selectedTechnique = PlayingTechnique.HYBRID_PICKING
                                }
                                "yt_bass_02" -> {
                                    viewModel.selectedBassPickup.value = com.example.model.BassPickupType.J_BASS
                                    selectedTechnique = PlayingTechnique.DYNAMIC_FINGERSTYLE
                                }
                                "yt_bass_03" -> {
                                    viewModel.selectedBassPickup.value = com.example.model.BassPickupType.MODERN_5STRING
                                    selectedTechnique = PlayingTechnique.PALM_MUTING_CHUG
                                }
                            }
                        }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) ValetonCyan else ValetonNavyDark),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = null,
                            tint = if (isSelected) ValetonNavyDark else ValetonCyan,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = ref.title,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else TextPrimary,
                            maxLines = 1
                        )
                        Text(
                            text = "${ref.channel} • ${ref.genre}",
                            fontSize = 10.sp,
                            color = ValetonCyan
                        )
                        Text(
                            text = ref.keyRigBreakdown,
                            fontSize = 10.sp,
                            color = TextMuted,
                            maxLines = 1
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // User's Playing Technique Selection
        Text(
            text = "Your Guitar Playing Technique:",
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = ValetonCyan
        )

        Spacer(modifier = Modifier.height(6.dp))

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            PlayingTechnique.values().forEach { tech ->
                val isSelected = selectedTechnique == tech
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) ValetonNavySurface else ValetonNavyCard)
                        .border(
                            1.dp,
                            if (isSelected) ValetonElectricBlue else ValetonNavyElevated,
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { selectedTechnique = tech }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) ValetonElectricBlue else Color.Transparent)
                            .border(1.5.dp, if (isSelected) ValetonElectricBlue else TextMuted, CircleShape)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = tech.displayName,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else TextPrimary
                        )
                        Text(
                            text = tech.description,
                            fontSize = 10.sp,
                            color = TextSecondary
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Visual Acoustic Spectral Delta Comparison
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(ValetonNavyCard)
                .border(1.dp, ValetonNavyElevated, RoundedCornerShape(12.dp))
                .padding(14.dp)
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
                        text = "Acoustic Spectral Analysis",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(ValetonCyan)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Target YT", fontSize = 10.sp, color = ValetonCyan)

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(ValetonElectricBlue)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Your Guitar", fontSize = 10.sp, color = ValetonElectricBlue)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Canvas Spectrum Chart
            DualSpectrumVisualizer(
                targetSpectrum = targetSpectrum,
                userSpectrum = userSpectrum,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "AI Delta: ${selectedReference.aiInsight}",
                fontSize = 11.sp,
                color = ValetonAmberDrive,
                fontFamily = FontFamily.Monospace,
                lineHeight = 15.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Action Button: MATCH & LOAD INTO VALETON GP-180
        Button(
            onClick = {
                isAnalyzing = true
                val matchedPreset = YouTubeToneAnalyzer.matchYouTubeTone(
                    reference = selectedReference,
                    userPickup = pickup,
                    technique = selectedTechnique
                )
                viewModel.loadPreset(matchedPreset)
                viewModel.saveCurrentPreset()
                isAnalyzing = false
                viewModel.selectTab(AppTab.CHAIN_EDITOR)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("match_youtube_tone_button"),
            colors = ButtonDefaults.buttonColors(
                containerColor = ValetonCyan,
                contentColor = ValetonNavyDark
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            if (isAnalyzing) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = ValetonNavyDark,
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (currentLanguage == AppLanguage.INDONESIAN) "Menganalisis Spektrum Nada YouTube..." else "Analyzing YouTube Spectrum...",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.AutoAwesome,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (currentLanguage == AppLanguage.INDONESIAN) "ANALISIS & TERAPKAN KE DSP GP-180" else "ANALYZE & LOAD INTO GP-180 DSP",
                    fontWeight = FontWeight.Black,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Technical Algorithm Explanation
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(ValetonNavyDark)
                .border(1.dp, ValetonNavyElevated, RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showAlgorithmDetails = !showAlgorithmDetails },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = null,
                        tint = ValetonCyan,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Bagaimana Algoritma AI Bekerja?",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = ValetonCyan
                    )
                }

                Text(
                    text = if (showAlgorithmDetails) "Tutup ▲" else "Lihat Detail ▼",
                    fontSize = 10.sp,
                    color = TextSecondary
                )
            }

            AnimatedVisibility(visible = showAlgorithmDetails) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    Text(
                        text = "1. Dekomposisi Spektral FFT:\n" +
                               "Algoritma mengekstraksi kurva frekuensi dari rekaman YouTube (Low-cut 80Hz, mud cut 350Hz, vocal mid punch 1kHz, glass snap 3.2kHz, dan air filter 8kHz).",
                        fontSize = 11.sp,
                        color = TextPrimary,
                        lineHeight = 15.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "2. Kompensasi Karakteristik Pickup & Teknik Bermain:\n" +
                               "Pickup Single-Coil diberi notch filter untuk meredam resonansi boom 350Hz; Humbucker diberi dorongan 1kHz untuk kejernihan; Palm-Muting Chug diberi Noise Gate threshold tinggi (62%) dan low-cut ketat 90Hz pada Cab IR.",
                        fontSize = 11.sp,
                        color = TextPrimary,
                        lineHeight = 15.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "3. Pemetaan Presisi ke Blok Hardware Valeton GP-180:\n" +
                               "Setiap parameter dikonversi langsung ke skala P1–P4 modul PRE, DST, AMP, CAB, NR, EQ, MOD, DLY, dan REV yang siap dimuat ke pedalboard fisik Anda.",
                        fontSize = 11.sp,
                        color = ValetonLimeGreen,
                        lineHeight = 15.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun DualSpectrumVisualizer(
    targetSpectrum: SpectralAnalysisData,
    userSpectrum: SpectralAnalysisData,
    modifier: Modifier = Modifier
) {
    val bands = listOf("Sub", "LowMid", "350Hz", "1kHz", "3.2kHz", "Air")
    val targetValues = listOf(
        targetSpectrum.subBass,
        targetSpectrum.lowMids,
        targetSpectrum.bodyMud,
        targetSpectrum.vocalPunch,
        targetSpectrum.presenceSnap,
        targetSpectrum.airFizz
    )
    val userValues = listOf(
        userSpectrum.subBass,
        userSpectrum.lowMids,
        userSpectrum.bodyMud,
        userSpectrum.vocalPunch,
        userSpectrum.presenceSnap,
        userSpectrum.airFizz
    )

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val barGroupWidth = width / bands.size
        val individualBarWidth = barGroupWidth * 0.32f
        val spacing = barGroupWidth * 0.08f

        for (i in bands.indices) {
            val groupStartX = i * barGroupWidth + (barGroupWidth - (individualBarWidth * 2 + spacing)) / 2f

            // Target YouTube Bar (Cyan)
            val targetHeight = (height * targetValues[i] * 0.85f).coerceIn(6f, height)
            drawRoundRect(
                brush = Brush.verticalGradient(
                    colors = listOf(ValetonCyan, ValetonElectricBlue)
                ),
                topLeft = Offset(groupStartX, height - targetHeight),
                size = Size(individualBarWidth, targetHeight),
                cornerRadius = CornerRadius(2f, 2f)
            )

            // User Guitar Bar (Electric Blue)
            val userHeight = (height * userValues[i] * 0.85f).coerceIn(6f, height)
            drawRoundRect(
                brush = Brush.verticalGradient(
                    colors = listOf(ValetonElectricBlue, ValetonNavyElevated)
                ),
                topLeft = Offset(groupStartX + individualBarWidth + spacing, height - userHeight),
                size = Size(individualBarWidth, userHeight),
                cornerRadius = CornerRadius(2f, 2f)
            )
        }
    }
}
