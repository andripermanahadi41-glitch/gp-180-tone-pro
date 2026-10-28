package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.model.ValetonPreset
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
fun CloudLibraryScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val currentLanguage by viewModel.currentLanguage.collectAsState()
    val s = AppStrings.get(currentLanguage)
    val cloudPresets by viewModel.cloudPresets.collectAsState()
    val searchQuery by viewModel.cloudSearchQuery.collectAsState()
    val selectedGenre by viewModel.selectedCloudGenre.collectAsState()
    val currentPreset by viewModel.currentPreset.collectAsState()
    val genreScrollState = rememberScrollState()

    val genres = if (currentLanguage == AppLanguage.INDONESIAN) {
        listOf("Semua", "Blues / Pop", "Neo-Soul / Math Rock", "Hard Rock", "Modern Metal / Djent", "Shoegaze / Ambient")
    } else {
        listOf("All", "Blues / Pop", "Neo-Soul / Math Rock", "Hard Rock", "Modern Metal / Djent", "Shoegaze / Ambient")
    }

    val filteredPresets = cloudPresets.filter { preset ->
        val matchesQuery = searchQuery.isBlank() ||
                preset.name.contains(searchQuery, ignoreCase = true) ||
                preset.targetArtistSong.contains(searchQuery, ignoreCase = true) ||
                preset.author.contains(searchQuery, ignoreCase = true)
        val matchesGenre = selectedGenre == "All" || selectedGenre == "Semua" || preset.genre.equals(selectedGenre, ignoreCase = true)
        matchesQuery && matchesGenre
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ValetonNavySuit)
            .padding(16.dp)
    ) {
        // Cloud Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(ValetonNavyCard, ValetonNavySurface)
                    )
                )
                .border(1.dp, ValetonCyan.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(ValetonElectricBlue.copy(alpha = 0.2f))
                            .border(1.dp, ValetonElectricBlue, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CloudQueue,
                            contentDescription = "Cloud",
                            tint = ValetonCyan,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = s.cloudTitle,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(ValetonLimeGreen.copy(alpha = 0.2f))
                                    .padding(horizontal = 5.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = if (currentLanguage == AppLanguage.INDONESIAN) "TERHUBUNG" else "SYNCED",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ValetonLimeGreen
                                )
                            }
                        }
                        Text(
                            text = s.cloudSubtitle,
                            fontSize = 11.sp,
                            color = TextSecondary,
                            maxLines = 1
                        )
                    }
                }

                Button(
                    onClick = { viewModel.uploadCurrentToCloud() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ValetonElectricBlue,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("upload_to_cloud_button")
                ) {
                    Icon(
                        imageVector = Icons.Filled.CloudUpload,
                        contentDescription = null,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (currentLanguage == AppLanguage.INDONESIAN) "BAGIKAN" else "SHARE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.cloudSearchQuery.value = it },
            placeholder = { Text(s.cloudSearchPlaceholder, color = TextMuted) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = ValetonCyan
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("cloud_search_input"),
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

        Spacer(modifier = Modifier.height(10.dp))

        // Genre Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(genreScrollState),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            genres.forEach { g ->
                val isSelected = selectedGenre == g
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isSelected) ValetonCyan else ValetonNavyCard)
                        .border(1.dp, if (isSelected) ValetonCyan else ValetonNavyElevated, RoundedCornerShape(16.dp))
                        .clickable { viewModel.selectedCloudGenre.value = g }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = g,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) ValetonNavyDark else TextSecondary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Preset List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(filteredPresets) { preset ->
                CloudPresetCard(
                    preset = preset,
                    onDownload = { viewModel.downloadFromCloud(preset) },
                    onLike = { viewModel.likePreset(preset.id) },
                    onAudition = {
                        viewModel.loadPreset(preset)
                        viewModel.testAudioPreview("Auto")
                    }
                )
            }
        }
    }
}

@Composable
fun CloudPresetCard(
    preset: ValetonPreset,
    onDownload: () -> Unit,
    onLike: () -> Unit,
    onAudition: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
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
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = preset.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    if (preset.aiOptimized) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(ValetonCyan.copy(alpha = 0.2f))
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "AI TUNED",
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Black,
                                color = ValetonCyan
                            )
                        }
                    }
                }

                Text(
                    text = "By ${preset.author} • ${preset.genre}",
                    fontSize = 11.sp,
                    color = ValetonCyanMuted
                )
            }

            // Likes Counter
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .clickable { onLike() }
                    .padding(horizontal = 6.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Likes",
                    tint = ValetonFireRed,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${preset.likesCount}",
                    fontSize = 11.sp,
                    color = TextSecondary,
                    fontFamily = FontFamily.Monospace
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = preset.description,
            fontSize = 11.sp,
            color = TextSecondary,
            lineHeight = 15.sp,
            maxLines = 2
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Active Blocks Mini Badges
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            preset.blocks.filter { it.enabled }.take(6).forEach { block ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(block.type.colorHex).copy(alpha = 0.15f))
                        .border(1.dp, Color(block.type.colorHex).copy(alpha = 0.4f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 5.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = block.type.code,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(block.type.colorHex)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Audition & Download Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { onAudition() },
                modifier = Modifier
                    .weight(1f)
                    .height(36.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = ValetonCyan),
                border = androidx.compose.foundation.BorderStroke(1.dp, ValetonCyan),
                shape = RoundedCornerShape(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "DENGAR",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = { onDownload() },
                modifier = Modifier
                    .weight(1.3f)
                    .height(36.dp)
                    .testTag("download_preset_${preset.id}"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ValetonCyan,
                    contentColor = ValetonNavyDark
                ),
                shape = RoundedCornerShape(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.CloudDownload,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "UNDUH KE GP-180",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }
    }
}
