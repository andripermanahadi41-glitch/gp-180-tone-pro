package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.filled.FolderSpecial
import androidx.compose.material.icons.filled.LinearScale
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppTab
import com.example.ui.MainViewModel
import com.example.ui.components.TopBarHeader
import com.example.ui.screens.AiStudioScreen
import com.example.ui.screens.ChainEditorScreen
import com.example.ui.screens.CloudLibraryScreen
import com.example.ui.screens.HardwareGuideScreen
import com.example.ui.screens.MyPresetsScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.ValetonCyan
import com.example.ui.theme.ValetonNavyCard
import com.example.ui.theme.ValetonNavyDark
import com.example.ui.theme.ValetonNavyElevated
import com.example.ui.theme.ValetonNavySuit
import com.example.ui.theme.ValetonNavySurface
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainAppScreen(viewModel: MainViewModel) {
    val selectedTab by viewModel.selectedTab.collectAsState()
    val currentPreset by viewModel.currentPreset.collectAsState()
    val isAudioPlaying by viewModel.isAudioPlaying.collectAsState()
    val currentLanguage by viewModel.currentLanguage.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbarEvent.collectLatest { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    // BackHandler: Return to AI Studio if on another tab
    BackHandler(enabled = selectedTab != AppTab.AI_STUDIO) {
        viewModel.selectTab(AppTab.AI_STUDIO)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(ValetonNavySuit)
            .windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            TopBarHeader(
                currentPreset = currentPreset,
                isPlaying = isAudioPlaying,
                onTogglePlay = { viewModel.testAudioPreview("Auto") },
                currentLanguage = currentLanguage,
                onToggleLanguage = { viewModel.toggleLanguage() }
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .border(1.dp, ValetonNavyElevated, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                containerColor = ValetonNavyDark,
                tonalElevation = 8.dp
            ) {
                AppTab.values().forEach { tab ->
                    val isSelected = selectedTab == tab
                    val tabTitle = tab.getLocalizedTitle(currentLanguage)
                    val iconVector = when (tab) {
                        AppTab.AI_STUDIO -> Icons.Filled.AutoAwesome
                        AppTab.CHAIN_EDITOR -> Icons.Filled.LinearScale
                        AppTab.HARDWARE_GUIDE -> Icons.Filled.Tune
                        AppTab.CLOUD_LIBRARY -> Icons.Filled.CloudQueue
                        AppTab.MY_PRESETS -> Icons.Filled.FolderSpecial
                    }

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { viewModel.selectTab(tab) },
                        icon = {
                            Icon(
                                imageVector = iconVector,
                                contentDescription = tabTitle,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        label = {
                            Text(
                                text = tabTitle,
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = ValetonNavyDark,
                            selectedTextColor = ValetonCyan,
                            indicatorColor = ValetonCyan,
                            unselectedIconColor = TextMuted,
                            unselectedTextColor = TextMuted
                        ),
                        modifier = Modifier.testTag("tab_${tab.name.lowercase()}")
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = ValetonNavySuit
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(ValetonNavySuit)
        ) {
            when (selectedTab) {
                AppTab.AI_STUDIO -> AiStudioScreen(viewModel = viewModel)
                AppTab.CHAIN_EDITOR -> ChainEditorScreen(viewModel = viewModel)
                AppTab.HARDWARE_GUIDE -> HardwareGuideScreen(viewModel = viewModel)
                AppTab.CLOUD_LIBRARY -> CloudLibraryScreen(viewModel = viewModel)
                AppTab.MY_PRESETS -> MyPresetsScreen(viewModel = viewModel)
            }
        }
    }
}
