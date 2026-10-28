package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
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
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.export.PresetExporter
import com.example.model.EffectBlockType
import com.example.ui.MainViewModel
import com.example.ui.components.ValetonFootswitch
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
import com.example.ui.theme.ValetonNavySurface

@Composable
fun HardwareGuideScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val currentLanguage by viewModel.currentLanguage.collectAsState()
    val s = AppStrings.get(currentLanguage)
    val preset by viewModel.currentPreset.collectAsState()
    val scrollState = rememberScrollState()
    val blockScrollState = rememberScrollState()

    var activeHardwareBlock by remember { mutableStateOf(EffectBlockType.AMP) }
    val currentBlock = preset.blocks.find { it.type == activeHardwareBlock } ?: preset.blocks.first()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ValetonNavySuit)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Guide Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = s.hardwareTitle,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp,
                    color = ValetonCyan
                )
                Text(
                    text = s.hardwareSubtitle,
                    fontSize = 11.sp,
                    color = TextSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Physical Valeton GP-180 LCD Screen Mockup
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(Color(0xFF030712)) // Darkest LCD glass
                .border(2.5.dp, ValetonNavyElevated, RoundedCornerShape(14.dp))
                .padding(14.dp)
        ) {
            Column {
                // LCD Top Status Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "VALETON GP-180 PRO",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = ValetonCyan.copy(alpha = 0.8f)
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "PATCH: ${preset.patchNumber}",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = ValetonAmberDrive
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${preset.bpm} BPM",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 10.sp,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // LCD Main Title & Model
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFF0B192E))
                        .border(1.dp, ValetonCyan.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                        .padding(10.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${currentBlock.type.code} : ${currentBlock.selectedModel.uppercase()}",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Black,
                                color = ValetonCyan
                            )

                            Text(
                                text = if (currentBlock.enabled) "[ACTIVE]" else "[BYPASS]",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (currentBlock.enabled) ValetonLimeGreen else Color(0xFFEF4444)
                            )
                        }

                        Text(
                            text = preset.name,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                val paramsList = currentBlock.parameters.entries.toList()

                // Physical Parameter Knobs Display: P1, P2, P3, P4
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (i in 0 until 3) {
                        val entry = paramsList.getOrNull(i)
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF0F1E36))
                                .border(1.dp, Color(0xFF1E3A5F), RoundedCornerShape(8.dp))
                                .padding(vertical = 8.dp, horizontal = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "P${i + 1}",
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ValetonAmberDrive
                                )
                                Text(
                                    text = entry?.key ?: "--",
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 10.sp,
                                    color = TextSecondary,
                                    maxLines = 1
                                )
                                val valText = entry?.value?.let { v ->
                                    if (v % 1f == 0f) "${v.toInt()}" else String.format("%.1f", v)
                                } ?: "--"
                                Text(
                                    text = valText,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Black,
                                    color = ValetonCyan
                                )
                            }
                        }
                    }
                }

                if (paramsList.size > 3) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (i in 3 until (paramsList.size.coerceAtMost(6))) {
                            val entry = paramsList.getOrNull(i)
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF0F1E36))
                                    .border(1.dp, Color(0xFF1E3A5F), RoundedCornerShape(8.dp))
                                    .padding(vertical = 6.dp, horizontal = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "P${i + 1}",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = ValetonAmberDrive
                                    )
                                    Text(
                                        text = entry?.key ?: "--",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 9.sp,
                                        color = TextSecondary,
                                        maxLines = 1
                                    )
                                    val valText = entry?.value?.let { v ->
                                        if (v % 1f == 0f) "${v.toInt()}" else String.format("%.1f", v)
                                    } ?: "--"
                                    Text(
                                        text = valText,
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = ValetonCyan
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Select Block to Inspect Dial-in
        Text(
            text = "Switch Hardware Block:",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = ValetonCyan
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(blockScrollState),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            preset.blocks.forEach { block ->
                val isSelected = block.type == activeHardwareBlock
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) ValetonCyan else ValetonNavyCard)
                        .border(1.dp, if (isSelected) ValetonCyan else ValetonNavyElevated, RoundedCornerShape(8.dp))
                        .clickable { activeHardwareBlock = block.type }
                        .padding(horizontal = 12.dp, vertical = 7.dp)
                ) {
                    Text(
                        text = block.type.code,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) ValetonNavyDark else TextPrimary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Hardware Footswitches Layout Mockup
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(ValetonNavyCard)
                .border(1.dp, ValetonNavyElevated, RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Text(
                text = "Pedal Footswitches (FS1 • FS2 • FS3 / CTRL):",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ValetonFootswitch(
                    label = "FS 1",
                    subLabel = "PATCH ▼",
                    isActive = false,
                    onToggle = {},
                    ledColor = ValetonElectricBlue
                )
                ValetonFootswitch(
                    label = "FS 2",
                    subLabel = "PATCH ▲",
                    isActive = false,
                    onToggle = {},
                    ledColor = ValetonElectricBlue
                )
                ValetonFootswitch(
                    label = "FS 3",
                    subLabel = "TAP / CTRL",
                    isActive = true,
                    onToggle = {},
                    ledColor = ValetonAmberDrive
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Export & Copy Actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    val guide = PresetExporter.generateHardwareDialInSheet(preset)
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    clipboard.setPrimaryClip(ClipData.newPlainText("Valeton GP-180 Dial Guide", guide))
                    val msg = if (currentLanguage == AppLanguage.INDONESIAN) "Panduan dial berhasil disalin!" else "Dial-in sheet copied to clipboard!"
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
                    .testTag("copy_dial_in_button"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ValetonCyan,
                    contentColor = ValetonNavyDark
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Assignment,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (currentLanguage == AppLanguage.INDONESIAN) "SALIN DIAL-IN" else "COPY DIAL-IN",
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                )
            }

            OutlinedButton(
                onClick = {
                    val json = PresetExporter.exportToJson(preset)
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    clipboard.setPrimaryClip(ClipData.newPlainText("Valeton GP-180 JSON", json))
                    val msg = if (currentLanguage == AppLanguage.INDONESIAN) "JSON preset disalin!" else "Preset JSON copied to clipboard!"
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
                    .testTag("export_json_button"),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = ValetonElectricBlue),
                border = androidx.compose.foundation.BorderStroke(1.dp, ValetonElectricBlue),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Code,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (currentLanguage == AppLanguage.INDONESIAN) "EKSPOR JSON" else "EXPORT JSON",
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
