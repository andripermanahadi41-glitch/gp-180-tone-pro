package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.model.ValetonPreset
import com.example.ui.AppTab
import com.example.ui.MainViewModel
import com.example.ui.i18n.AppLanguage
import com.example.ui.i18n.AppStrings
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.ValetonAmberDrive
import com.example.ui.theme.ValetonCyan
import com.example.ui.theme.ValetonCyanMuted
import com.example.ui.theme.ValetonElectricBlue
import com.example.ui.theme.ValetonFireRed
import com.example.ui.theme.ValetonLimeGreen
import com.example.ui.theme.ValetonNavyCard
import com.example.ui.theme.ValetonNavyDark
import com.example.ui.theme.ValetonNavyElevated
import com.example.ui.theme.ValetonNavySuit
import com.example.ui.theme.ValetonNavySurface

@Composable
fun MyPresetsScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val currentLanguage by viewModel.currentLanguage.collectAsState()
    val s = AppStrings.get(currentLanguage)
    val localPresets by viewModel.localPresets.collectAsState()
    val currentPreset by viewModel.currentPreset.collectAsState()

    var presetToRename by remember { mutableStateOf<ValetonPreset?>(null) }
    var newPresetName by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ValetonNavySuit)
            .padding(16.dp)
    ) {
        // Bank Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = s.myPresetsTitle,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp,
                    color = ValetonCyan
                )
                Text(
                    text = "${localPresets.size} preset tersimpan di memori DSP GP-180",
                    fontSize = 11.sp,
                    color = TextSecondary
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(ValetonNavyCard)
                    .border(1.dp, ValetonNavyElevated, RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "ACTIVE: ${currentPreset.patchNumber}",
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = ValetonElectricBlue
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Preset List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(localPresets) { preset ->
                val isActive = preset.id == currentPreset.id
                LocalPresetCard(
                    preset = preset,
                    isActive = isActive,
                    onSelect = {
                        viewModel.loadPreset(preset)
                        viewModel.selectTab(AppTab.CHAIN_EDITOR)
                    },
                    onRename = {
                        presetToRename = preset
                        newPresetName = preset.name
                    },
                    onDuplicate = {
                        viewModel.loadPreset(preset)
                        viewModel.duplicateCurrentPreset()
                    },
                    onUploadCloud = {
                        viewModel.loadPreset(preset)
                        viewModel.uploadCurrentToCloud()
                    },
                    onDelete = {
                        viewModel.deletePreset(preset.id)
                    }
                )
            }
        }
    }

    // Rename Dialog
    presetToRename?.let { target ->
        AlertDialog(
            onDismissRequest = { presetToRename = null },
            title = {
                Text(
                    text = "Rename Patch",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            },
            text = {
                Column {
                    Text(
                        text = "Enter new name for slot ${target.patchNumber}:",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newPresetName,
                        onValueChange = { newPresetName = it },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ValetonCyan,
                            unfocusedBorderColor = ValetonNavyElevated,
                            focusedContainerColor = ValetonNavyDark,
                            unfocusedContainerColor = ValetonNavyDark,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newPresetName.isNotBlank()) {
                            viewModel.saveCurrentPreset(newPresetName)
                        }
                        presetToRename = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ValetonCyan, contentColor = ValetonNavyDark)
                ) {
                    Text("SAVE")
                }
            },
            dismissButton = {
                TextButton(onClick = { presetToRename = null }) {
                    Text("CANCEL", color = TextSecondary)
                }
            },
            containerColor = ValetonNavyCard,
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Composable
fun LocalPresetCard(
    preset: ValetonPreset,
    isActive: Boolean,
    onSelect: () -> Unit,
    onRename: () -> Unit,
    onDuplicate: () -> Unit,
    onUploadCloud: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (isActive) ValetonNavySurface else ValetonNavyCard)
            .border(
                width = if (isActive) 2.dp else 1.dp,
                color = if (isActive) ValetonCyan else ValetonNavyElevated,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onSelect() }
            .padding(12.dp)
            .testTag("preset_card_${preset.id}")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Patch Number Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isActive) ValetonCyan else ValetonElectricBlue)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = preset.patchNumber,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = if (isActive) ValetonNavyDark else Color.White
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = preset.name,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        if (isActive) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(ValetonCyan.copy(alpha = 0.2f))
                                    .padding(horizontal = 5.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "ENGAGED",
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Black,
                                    color = ValetonCyan
                                )
                            }
                        }
                    }

                    Text(
                        text = "${preset.genre} • ${preset.bpm} BPM",
                        fontSize = 11.sp,
                        color = ValetonCyanMuted
                    )
                }
            }

            // Quick actions
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { onRename() },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Rename",
                        tint = TextSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                }

                IconButton(
                    onClick = { onDuplicate() },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ContentCopy,
                        contentDescription = "Duplicate",
                        tint = TextSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                }

                IconButton(
                    onClick = { onUploadCloud() },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.CloudUpload,
                        contentDescription = "Upload to Cloud",
                        tint = ValetonAmberDrive,
                        modifier = Modifier.size(16.dp)
                    )
                }

                IconButton(
                    onClick = { onDelete() },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        tint = ValetonFireRed.copy(alpha = 0.7f),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Active Blocks Mini Indicators
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            preset.blocks.forEach { block ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(3.dp))
                        .background(if (block.enabled) Color(block.type.colorHex).copy(alpha = 0.2f) else ValetonNavyDark)
                        .border(
                            1.dp,
                            if (block.enabled) Color(block.type.colorHex) else Color(0xFF1E293B),
                            RoundedCornerShape(3.dp)
                        )
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = block.type.code,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (block.enabled) Color(block.type.colorHex) else TextMuted
                    )
                }
            }
        }
    }
}
