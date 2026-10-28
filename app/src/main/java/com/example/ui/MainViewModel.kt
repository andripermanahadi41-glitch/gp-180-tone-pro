package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ai.AIToneOptimizer
import com.example.audio.ToneAudioSimulator
import com.example.data.PresetRepository
import com.example.model.DefaultFactoryPresets
import com.example.model.EffectBlock
import com.example.model.EffectBlockType
import com.example.model.GuitarPickupType
import com.example.model.ToneDestination
import com.example.model.ValetonPreset
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

import com.example.ui.i18n.AppLanguage
import com.example.ui.i18n.AppStrings

enum class AppTab(val title: String, val iconName: String) {
    CHAIN_EDITOR("Edit", "LinearScale"),
    AI_STUDIO("AI Studio", "AutoAwesome"),
    HARDWARE_GUIDE("GP180 Dial", "Tune"),
    CLOUD_LIBRARY("Cloud", "CloudQueue"),
    MY_PRESETS("Manage", "FolderSpecial");

    fun getLocalizedTitle(lang: AppLanguage): String = when (this) {
        CHAIN_EDITOR -> if (lang == AppLanguage.INDONESIAN) "Edit Rantai" else "Edit"
        AI_STUDIO -> if (lang == AppLanguage.INDONESIAN) "Studio AI" else "AI Studio"
        HARDWARE_GUIDE -> if (lang == AppLanguage.INDONESIAN) "Dial GP-180" else "GP180 Dial"
        CLOUD_LIBRARY -> if (lang == AppLanguage.INDONESIAN) "Cloud" else "Cloud"
        MY_PRESETS -> if (lang == AppLanguage.INDONESIAN) "Kelola" else "Manage"
    }
}

sealed class AiStatus {
    object Idle : AiStatus()
    object Processing : AiStatus()
    data class Success(val message: String) : AiStatus()
    data class Error(val message: String) : AiStatus()
}

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PresetRepository(application)

    val localPresets: StateFlow<List<ValetonPreset>> = repository.getAllLocalPresets()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DefaultFactoryPresets.factoryPresets)

    val cloudPresets: StateFlow<List<ValetonPreset>> = repository.cloudPresets

    private val _currentPreset = MutableStateFlow<ValetonPreset>(DefaultFactoryPresets.factoryPresets.first())
    val currentPreset: StateFlow<ValetonPreset> = _currentPreset.asStateFlow()

    private val _selectedTab = MutableStateFlow(AppTab.CHAIN_EDITOR)
    val selectedTab: StateFlow<AppTab> = _selectedTab.asStateFlow()

    private val _selectedBlockType = MutableStateFlow<EffectBlockType>(EffectBlockType.NR)
    val selectedBlockType: StateFlow<EffectBlockType> = _selectedBlockType.asStateFlow()

    // AI Generation Form States
    val selectedInstrument = MutableStateFlow(com.example.model.InstrumentType.GUITAR)
    val selectedBassPickup = MutableStateFlow(com.example.model.BassPickupType.P_BASS)
    val aiPrompt = MutableStateFlow("John Mayer Slow Dancing Stratocaster clean breakup with smooth delay")
    val selectedPickup = MutableStateFlow(GuitarPickupType.SINGLE_COIL_STRAT)
    val selectedGenre = MutableStateFlow("Blues / Pop")
    val selectedDestination = MutableStateFlow(ToneDestination.DIRECT_PA)
    val aiStatus = MutableStateFlow<AiStatus>(AiStatus.Idle)

    // Cloud Search & Filters
    val cloudSearchQuery = MutableStateFlow("")
    val selectedCloudGenre = MutableStateFlow("All")

    // Notifications / Snackbars
    private val _snackbarEvent = MutableSharedFlow<String>()
    val snackbarEvent: SharedFlow<String> = _snackbarEvent.asSharedFlow()

    val isAudioPlaying = ToneAudioSimulator.isPlaying
    val waveformData = ToneAudioSimulator.waveformSamples

    // Language State (Defaults to Indonesian per user requirement)
    private val _currentLanguage = MutableStateFlow(AppLanguage.INDONESIAN)
    val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()

    fun setLanguage(language: AppLanguage) {
        _currentLanguage.value = language
    }

    fun toggleLanguage() {
        _currentLanguage.value = if (_currentLanguage.value == AppLanguage.INDONESIAN) {
            AppLanguage.ENGLISH
        } else {
            AppLanguage.INDONESIAN
        }
    }

    fun selectTab(tab: AppTab) {
        _selectedTab.value = tab
    }

    fun selectBlock(type: EffectBlockType) {
        _selectedBlockType.value = type
    }

    fun loadPreset(preset: ValetonPreset) {
        _currentPreset.value = preset
        ToneAudioSimulator.stop()
    }

    fun toggleBlock(type: EffectBlockType) {
        val preset = _currentPreset.value
        val updatedBlocks = preset.blocks.map { block ->
            if (block.type == type) block.copy(enabled = !block.enabled) else block
        }
        _currentPreset.value = preset.copy(blocks = updatedBlocks)
    }

    fun updateBlockModel(type: EffectBlockType, newModel: String, newParams: Map<String, Float>? = null) {
        val preset = _currentPreset.value
        val updatedBlocks = preset.blocks.map { block ->
            if (block.type == type) {
                val params = newParams ?: block.parameters
                block.copy(selectedModel = newModel, parameters = params)
            } else block
        }
        _currentPreset.value = preset.copy(blocks = updatedBlocks)
    }

    fun updateBlockParameter(type: EffectBlockType, paramName: String, newValue: Float) {
        val preset = _currentPreset.value
        val updatedBlocks = preset.blocks.map { block ->
            if (block.type == type) {
                val newParams = block.parameters.toMutableMap()
                newParams[paramName] = newValue
                block.copy(parameters = newParams)
            } else block
        }
        _currentPreset.value = preset.copy(blocks = updatedBlocks)
    }

    fun updateMasterVolume(vol: Float) {
        _currentPreset.value = _currentPreset.value.copy(masterVolume = vol)
    }

    fun updateBpm(bpm: Int) {
        _currentPreset.value = _currentPreset.value.copy(bpm = bpm)
    }

    fun setInstrument(instrument: com.example.model.InstrumentType) {
        selectedInstrument.value = instrument
        if (instrument == com.example.model.InstrumentType.BASS) {
            if (aiPrompt.value.contains("Stratocaster", ignoreCase = true) || aiPrompt.value.contains("Mayer", ignoreCase = true)) {
                aiPrompt.value = "Flea RHCP slap funk punch dengan Ampeg SVT"
            }
        } else {
            if (aiPrompt.value.contains("Bass", ignoreCase = true) || aiPrompt.value.contains("Flea", ignoreCase = true)) {
                aiPrompt.value = "John Mayer Slow Dancing Stratocaster clean breakup with smooth delay"
            }
        }
    }

    fun generateAiPreset() {
        viewModelScope.launch {
            aiStatus.value = AiStatus.Processing
            delay(1200) // Simulated AI neural tone matching time
            try {
                // Ensure instrument selection is passed directly into prompt context
                val effectivePrompt = if (selectedInstrument.value == com.example.model.InstrumentType.BASS) {
                    if (aiPrompt.value.contains("Bass", ignoreCase = true)) {
                        aiPrompt.value
                    } else {
                        "Bass (${selectedBassPickup.value.displayName}): ${aiPrompt.value}"
                    }
                } else {
                    aiPrompt.value
                }

                val synthesized = AIToneOptimizer.generatePresetFromPrompt(
                    prompt = effectivePrompt,
                    pickup = selectedPickup.value,
                    genre = selectedGenre.value,
                    destination = selectedDestination.value,
                    instrument = selectedInstrument.value,
                    bassPickup = selectedBassPickup.value
                )
                _currentPreset.value = synthesized
                repository.savePreset(synthesized)
                aiStatus.value = AiStatus.Success("AI Tone successfully synthesized and optimized for Valeton GP-180!")
                _snackbarEvent.emit("Synthesized: ${synthesized.name}")
            } catch (e: Exception) {
                aiStatus.value = AiStatus.Error("Generation error: ${e.message}")
            }
        }
    }

    fun autoOptimizeCurrentTone() {
        viewModelScope.launch {
            aiStatus.value = AiStatus.Processing
            delay(800)
            val optimized = AIToneOptimizer.autoOptimizeTone(
                currentPreset = _currentPreset.value,
                pickup = selectedPickup.value,
                destination = selectedDestination.value
            )
            _currentPreset.value = optimized
            repository.savePreset(optimized)
            aiStatus.value = AiStatus.Success("Current patch frequency, EQ, and gain staging auto-optimized!")
            _snackbarEvent.emit("Tone Auto-Optimized with GP-180 AI Engine!")
        }
    }

    fun saveCurrentPreset(name: String? = null) {
        viewModelScope.launch {
            val toSave = if (name != null) _currentPreset.value.copy(name = name) else _currentPreset.value
            repository.savePreset(toSave)
            _snackbarEvent.emit("Preset '${toSave.name}' saved to My Bank!")
        }
    }

    fun duplicateCurrentPreset() {
        viewModelScope.launch {
            val count = localPresets.value.size
            val duplicated = _currentPreset.value.copy(
                id = "user_" + System.currentTimeMillis().toString().takeLast(6),
                patchNumber = "U" + String.format("%02d", count + 1) + "-1",
                name = "${_currentPreset.value.name} (Copy)"
            )
            repository.savePreset(duplicated)
            _currentPreset.value = duplicated
            _snackbarEvent.emit("Duplicated as ${duplicated.patchNumber}")
        }
    }

    fun uploadCurrentToCloud() {
        viewModelScope.launch {
            val preset = _currentPreset.value
            val success = repository.uploadToCloud(preset)
            if (success) {
                _snackbarEvent.emit("Shared '${preset.name}' to GP180 Cloud Community!")
            }
        }
    }

    fun downloadFromCloud(preset: ValetonPreset) {
        viewModelScope.launch {
            val downloaded = repository.downloadCloudPreset(preset)
            _currentPreset.value = downloaded
            _snackbarEvent.emit("Downloaded ${downloaded.name} to My Bank (${downloaded.patchNumber})")
        }
    }

    fun likePreset(presetId: String) {
        viewModelScope.launch {
            repository.likeCloudPreset(presetId)
        }
    }

    fun deletePreset(id: String) {
        viewModelScope.launch {
            repository.deletePreset(id)
            _snackbarEvent.emit("Preset deleted")
        }
    }

    fun testAudioPreview(riffType: String = "Auto") {
        if (isAudioPlaying.value) {
            ToneAudioSimulator.stop()
        } else {
            ToneAudioSimulator.previewPreset(viewModelScope, _currentPreset.value, riffType)
        }
    }

    override fun onCleared() {
        super.onCleared()
        ToneAudioSimulator.stop()
    }
}
