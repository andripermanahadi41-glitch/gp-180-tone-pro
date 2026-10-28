package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.ai.AIToneOptimizer
import com.example.model.DefaultFactoryPresets
import com.example.model.GuitarPickupType
import com.example.model.ToneDestination
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("GP180 Tone Pro", appName)
  }

  @Test
  fun `verify AI tone optimizer synthesizes valid preset`() {
    val preset = AIToneOptimizer.generatePresetFromPrompt(
      prompt = "John Mayer Slow Dancing",
      pickup = GuitarPickupType.SINGLE_COIL_STRAT,
      genre = "Blues / Pop",
      destination = ToneDestination.DIRECT_PA
    )
    assertNotNull(preset)
    assertTrue(preset.blocks.isNotEmpty())
    assertTrue(preset.aiOptimized)
  }

  @Test
  fun `verify auto-optimization applies acoustic tuning`() {
    val defaultPreset = DefaultFactoryPresets.factoryPresets.first()
    val optimized = AIToneOptimizer.autoOptimizeTone(
      currentPreset = defaultPreset,
      pickup = GuitarPickupType.HUMBUCKER_LP,
      destination = ToneDestination.DIRECT_PA
    )
    assertTrue(optimized.aiOptimized)
    assertTrue(optimized.aiOptimizationNotes.contains("AI Auto-Optimization Complete"))
  }

  @Test
  fun `verify Bass AI preset generates Ampeg SVT and low-cut optimization`() {
    val bassPreset = AIToneOptimizer.generatePresetFromPrompt(
      prompt = "Marcus Miller Slap Bass Funk",
      genre = "Funk / Fusion",
      destination = ToneDestination.DIRECT_PA,
      instrument = com.example.model.InstrumentType.BASS,
      bassPickup = com.example.model.BassPickupType.J_BASS
    )
    assertNotNull(bassPreset)
    assertEquals(12, bassPreset.blocks.size)
    val ampBlock = bassPreset.blocks.find { it.type == com.example.model.EffectBlockType.AMP }
    assertNotNull(ampBlock)
    assertEquals("Ampeg SVT", ampBlock?.selectedModel)
    assertTrue(bassPreset.aiOptimizationNotes.contains("Ampeg SVT"))
  }

  @Test
  fun `verify ViewModel instrument toggle adapts prompt and state`() {
    val application = ApplicationProvider.getApplicationContext<android.app.Application>()
    val viewModel = com.example.ui.MainViewModel(application)

    // Initial default is GUITAR
    assertEquals(com.example.model.InstrumentType.GUITAR, viewModel.selectedInstrument.value)

    // Switch to BASS
    viewModel.setInstrument(com.example.model.InstrumentType.BASS)
    assertEquals(com.example.model.InstrumentType.BASS, viewModel.selectedInstrument.value)
    assertTrue(viewModel.aiPrompt.value.contains("Bass", ignoreCase = true) || viewModel.aiPrompt.value.contains("Ampeg", ignoreCase = true))

    // Switch back to GUITAR
    viewModel.setInstrument(com.example.model.InstrumentType.GUITAR)
    assertEquals(com.example.model.InstrumentType.GUITAR, viewModel.selectedInstrument.value)
    assertTrue(viewModel.aiPrompt.value.contains("Stratocaster", ignoreCase = true) || viewModel.aiPrompt.value.contains("Mayer", ignoreCase = true))
  }

  @Test
  fun `verify YouTubeToneAnalyzer provides bass references when Bass instrument requested`() {
    val guitarRefs = com.example.ai.YouTubeToneAnalyzer.getReferencesForInstrument(com.example.model.InstrumentType.GUITAR)
    val bassRefs = com.example.ai.YouTubeToneAnalyzer.getReferencesForInstrument(com.example.model.InstrumentType.BASS)

    assertTrue(guitarRefs.isNotEmpty())
    assertTrue(bassRefs.isNotEmpty())
    assertTrue(guitarRefs.any { it.title.contains("Mayer") || it.title.contains("Ola") })
    assertTrue(bassRefs.any { it.title.contains("Flea") || it.title.contains("Marcus") || it.title.contains("Darkglass") })
  }

  @Test
  fun `verify Indonesian language strings and toggle`() {
    val application = ApplicationProvider.getApplicationContext<android.app.Application>()
    val viewModel = com.example.ui.MainViewModel(application)

    // Initial default should be Indonesian
    assertEquals(com.example.ui.i18n.AppLanguage.INDONESIAN, viewModel.currentLanguage.value)
    val idStrings = com.example.ui.i18n.AppStrings.get(viewModel.currentLanguage.value)
    assertEquals("Studio AI", com.example.ui.AppTab.AI_STUDIO.getLocalizedTitle(viewModel.currentLanguage.value))
    assertEquals("Suite DSP Multi-Efek Valeton", idStrings.suiteSubtitle)
    assertEquals("TES NADA", idStrings.testTone)
    assertEquals("Penyusun Preset AI", idStrings.modeBuilder)

    // Toggle to English
    viewModel.toggleLanguage()
    assertEquals(com.example.ui.i18n.AppLanguage.ENGLISH, viewModel.currentLanguage.value)
    val enStrings = com.example.ui.i18n.AppStrings.get(viewModel.currentLanguage.value)
    assertEquals("AI Studio", com.example.ui.AppTab.AI_STUDIO.getLocalizedTitle(viewModel.currentLanguage.value))
    assertEquals("TEST TONE", enStrings.testTone)

    // Toggle back to Indonesian
    viewModel.toggleLanguage()
    assertEquals(com.example.ui.i18n.AppLanguage.INDONESIAN, viewModel.currentLanguage.value)
  }
}
