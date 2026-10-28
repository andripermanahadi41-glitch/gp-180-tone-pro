package com.example.export

import com.example.data.local.Converters
import com.example.model.ValetonPreset

object PresetExporter {
    private val adapter = Converters.moshiInstance.adapter(ValetonPreset::class.java).indent("  ")

    fun exportToJson(preset: ValetonPreset): String {
        return adapter.toJson(preset)
    }

    fun generateHardwareDialInSheet(preset: ValetonPreset): String {
        return buildString {
            append("VALETON GP-180 PRO - HARDWARE DIAL-IN SHEET\n")
            append("=========================================\n")
            append("Patch: ${preset.patchNumber} - ${preset.name}\n")
            append("Genre: ${preset.genre} | BPM: ${preset.bpm} | Master Vol: ${preset.masterVolume.toInt()}\n")
            append("Target Instrument: ${preset.guitarType}\n")
            if (preset.aiOptimized) {
                append("AI Status: OPTIMIZED FOR GP-180 CLOUD DSP\n")
            }
            append("-----------------------------------------\n\n")

            preset.blocks.forEach { block ->
                val state = if (block.enabled) "[ON]" else "[BYPASS]"
                append("${block.type.code} ${block.type.fullName}: $state\n")
                append("   Model: ${block.selectedModel}\n")
                if (block.enabled) {
                    append("   Knob Parameters (P1/P2/P3/P4):\n")
                    block.parameters.forEach { (paramName, value) ->
                        val formattedVal = if (value % 1.0f == 0.0f) "${value.toInt()}" else String.format("%.1f", value)
                        append("    • $paramName: $formattedVal\n")
                    }
                }
                append("\n")
            }

            if (preset.aiOptimizationNotes.isNotBlank()) {
                append("AI Tone Tuning Notes:\n")
                append("${preset.aiOptimizationNotes}\n")
            }
            append("=========================================\n")
            append("Exported from GP180 Tone Pro AI Suite")
        }
    }
}
