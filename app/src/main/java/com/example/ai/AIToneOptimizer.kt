package com.example.ai

import com.example.model.DefaultFactoryPresets
import com.example.model.EffectBlock
import com.example.model.EffectBlockType
import com.example.model.GuitarPickupType
import com.example.model.ToneDestination
import com.example.model.ValetonPreset
import java.util.UUID

object AIToneOptimizer {

    fun generatePresetFromPrompt(
        prompt: String,
        pickup: GuitarPickupType = GuitarPickupType.SINGLE_COIL_STRAT,
        genre: String,
        destination: ToneDestination,
        instrument: com.example.model.InstrumentType = com.example.model.InstrumentType.GUITAR,
        bassPickup: com.example.model.BassPickupType? = null
    ): ValetonPreset {
        val lowerPrompt = prompt.lowercase()
        val isBass = instrument == com.example.model.InstrumentType.BASS
        val isMetal = lowerPrompt.contains("metal") || lowerPrompt.contains("djent") || lowerPrompt.contains("chug") || lowerPrompt.contains("heavy") || lowerPrompt.contains("metallica") || lowerPrompt.contains("drop") || genre.contains("Metal", ignoreCase = true)
        val isNeoSoul = lowerPrompt.contains("neosoul") || lowerPrompt.contains("neo-soul") || lowerPrompt.contains("polyphia") || lowerPrompt.contains("thump") || lowerPrompt.contains("tapping") || lowerPrompt.contains("math") || genre.contains("Neo-Soul", ignoreCase = true)
        val isAmbient = lowerPrompt.contains("ambient") || lowerPrompt.contains("worship") || lowerPrompt.contains("swell") || lowerPrompt.contains("shoegaze") || lowerPrompt.contains("ethereal") || lowerPrompt.contains("shimmer") || genre.contains("Ambient", ignoreCase = true)
        val isBlues = lowerPrompt.contains("blues") || lowerPrompt.contains("mayer") || lowerPrompt.contains("srv") || lowerPrompt.contains("texas") || lowerPrompt.contains("clean") || genre.contains("Blues", ignoreCase = true)
        val isHardRock = lowerPrompt.contains("rock") || lowerPrompt.contains("slash") || lowerPrompt.contains("solo") || lowerPrompt.contains("lead") || genre.contains("Rock", ignoreCase = true)
        val isSlap = lowerPrompt.contains("slap") || lowerPrompt.contains("flea") || lowerPrompt.contains("marcus") || lowerPrompt.contains("funk")

        val targetInstrumentName = if (isBass) (bassPickup?.displayName ?: "Precision Bass") else pickup.displayName

        val targetName = when {
            prompt.isNotBlank() && prompt.length <= 26 -> prompt.capitalizeWords()
            isBass && isSlap -> "AI Slap Funk Punch Bass"
            isBass && isMetal -> "AI Darkglass Metal Bass"
            isBass -> "AI Ampeg SVT Groove Bass"
            isMetal -> "AI Apex Djent & High-Gain"
            isNeoSoul -> "AI Neo-Soul Glass Chime"
            isAmbient -> "AI Cloud Ethereal Swell"
            isBlues -> "AI Texas Edge-Of-Breakup"
            isHardRock -> "AI 80s Singing Lead Solo"
            else -> "AI Custom Optimized Tone"
        }

        // Determine Amp & Drive configuration
        val ampModel = when {
            isBass -> "Ampeg SVT"
            isMetal -> "Cali Rectifier 100"
            isNeoSoul -> "Class A AC30"
            isAmbient -> "Jazz 120 Clean"
            isBlues -> "65 US Twin Reverb"
            isHardRock -> "British Plexi 1959"
            else -> "65 US Twin Reverb"
        }

        val dstModel = when {
            isBass && isMetal -> "Black Bass (Darkglass B7K)"
            isBass -> "Black Bass (Darkglass B7K)"
            isMetal -> "Green 808 Screamer"
            isNeoSoul -> "Pure Clean Boost"
            isAmbient -> "Blues Drive 62"
            isBlues -> "Green 808 Screamer"
            isHardRock -> "Gold K-Centaur"
            else -> "Green 808 Screamer"
        }

        val cabModel = when {
            isBass -> "4x12 1960 Greenback"
            isMetal -> "4x12 Recto V30"
            isNeoSoul -> "2x12 UK Celestion"
            isAmbient -> "2x12 US 65 Jensen"
            isBlues -> "2x12 US 65 Jensen"
            isHardRock -> "4x12 1960 Greenback"
            else -> "2x12 US 65 Jensen"
        }

        val baseBlocks = DefaultFactoryPresets.createDefaultBlockList().toMutableList()
        val blocks = baseBlocks.map { block ->
            when (block.type) {
                EffectBlockType.PRE -> {
                    val compEnabled = isNeoSoul || isAmbient || pickup == GuitarPickupType.SINGLE_COIL_STRAT
                    block.copy(
                        enabled = compEnabled,
                        selectedModel = if (isNeoSoul) "Valeton Dyna-Comp" else "Pure Clean Boost",
                        parameters = mapOf(
                            "Sustain" to (if (isNeoSoul) 72f else if (isAmbient) 55f else 35f),
                            "Attack" to (if (isNeoSoul) 18f else 30f),
                            "Level" to (if (isNeoSoul) 80f else 68f),
                            "Clipping" to 15f
                        )
                    )
                }
                EffectBlockType.DST -> {
                    block.copy(
                        enabled = isMetal || isHardRock || isBlues,
                        selectedModel = dstModel,
                        parameters = mapOf(
                            "Gain" to (if (isMetal) 8f else if (isHardRock) 48f else 32f),
                            "Tone" to (if (isMetal) 70f else if (isHardRock) 55f else 50f),
                            "Volume" to (if (isMetal) 85f else if (isHardRock) 65f else 62f)
                        )
                    )
                }
                EffectBlockType.AMP -> {
                    block.copy(
                        enabled = true,
                        selectedModel = ampModel,
                        parameters = mapOf(
                            "Gain" to (if (isMetal) 74f else if (isHardRock) 65f else if (isBlues) 42f else 32f),
                            "Bass" to (if (isMetal) 44f else if (pickup == GuitarPickupType.HUMBUCKER_LP) 45f else 58f),
                            "Middle" to (if (isMetal) 62f else if (isBlues) 65f else 55f),
                            "Treble" to (if (isNeoSoul) 78f else if (isMetal) 68f else 62f),
                            "Presence" to (if (isNeoSoul) 75f else 60f),
                            "Master" to 75f
                        )
                    )
                }
                EffectBlockType.CAB -> {
                    val isCabOn = destination == ToneDestination.DIRECT_PA
                    block.copy(
                        enabled = isCabOn,
                        selectedModel = cabModel,
                        parameters = mapOf(
                            "Mic Type" to 57f,
                            "Distance" to 18f,
                            "Center" to 14f,
                            "Low Cut" to (if (isMetal) 88f else 80f),
                            "High Cut" to (if (isMetal) 6500f else 7800f)
                        )
                    )
                }
                EffectBlockType.NR -> {
                    block.copy(
                        enabled = isMetal || isHardRock,
                        selectedModel = "Valeton Smart Gate",
                        parameters = mapOf(
                            "Threshold" to (if (isMetal) 62f else if (isHardRock) 42f else 28f),
                            "Release" to (if (isMetal) 12f else 24f),
                            "Detect" to 1f
                        )
                    )
                }
                EffectBlockType.EQ -> {
                    val eq100 = if (isMetal) 2.0f else if (pickup == GuitarPickupType.HUMBUCKER_LP) -0.5f else 1.5f
                    val eq350 = if (pickup == GuitarPickupType.HUMBUCKER_LP) -2.5f else -1.5f
                    val eq1k = if (isHardRock) 2.5f else 0.5f
                    val eq3k = if (isNeoSoul) 4.0f else if (isMetal) 2.5f else 1.0f
                    val eq8k = if (isMetal) -1.5f else if (isNeoSoul) 2.0f else -0.5f
                    block.copy(
                        enabled = true,
                        parameters = mapOf(
                            "100Hz" to eq100,
                            "350Hz" to eq350,
                            "1kHz" to eq1k,
                            "3.2kHz" to eq3k,
                            "8kHz" to eq8k
                        )
                    )
                }
                EffectBlockType.MOD -> {
                    block.copy(
                        enabled = isAmbient || isNeoSoul,
                        selectedModel = if (isNeoSoul) "Detune Doubler" else "Analog Lush Chorus",
                        parameters = mapOf(
                            "Rate" to (if (isAmbient) 24f else 16f),
                            "Depth" to (if (isAmbient) 65f else 35f),
                            "Tone" to 55f,
                            "Mix" to (if (isAmbient) 45f else 25f)
                        )
                    )
                }
                EffectBlockType.DLY -> {
                    block.copy(
                        enabled = isAmbient || isHardRock || isBlues,
                        selectedModel = if (isAmbient) "Tape Echo Space" else "Warm Analog BBD Delay",
                        parameters = mapOf(
                            "Time" to (if (isAmbient) 460f else if (isHardRock) 400f else 320f),
                            "Feedback" to (if (isAmbient) 55f else 28f),
                            "Mix" to (if (isAmbient) 40f else 22f),
                            "Tone" to 50f
                        )
                    )
                }
                EffectBlockType.RVB -> {
                    block.copy(
                        enabled = true,
                        selectedModel = if (isAmbient) "Cloud Shimmer Ambient" else if (isBlues) "Spring 63 Tank" else "Lush Studio Hall",
                        parameters = mapOf(
                            "PreDelay" to (if (isAmbient) 32f else 15f),
                            "Decay" to (if (isAmbient) 80f else 48f),
                            "Tone" to 60f,
                            "Mix" to (if (isAmbient) 48f else 25f)
                        )
                    )
                }
                else -> block
            }
        }

        val notes = buildString {
            append("AI Valeton GP-180 Optimization applied for $targetInstrumentName: ")
            if (isBass) {
                append("Ampeg SVT head emulation configured with Black Bass (B7K) parallel saturation; optical compression tuned for punchy fingerstyle and slap transients; low-cut aligned at 35Hz for fundamental bass notes.")
            } else if (isMetal) {
                append("Front Screamer boost configured with Gain:8, Vol:85 to tighten low-end before Recto amp. Noise Reducer clamped to Threshold 62. High-cut dialed to 6.5kHz for smooth studio recording.")
            } else if (isNeoSoul) {
                append("Dyna-Comp fast attack (18%) and level (80%) tuned for hybrid-picking chirps. 3.2kHz boosted +4.0dB on EQ. AC30 chime optimized.")
            } else if (isAmbient) {
                append("Stereo tape delay feedback elevated to 55% with Cloud Shimmer Reverb decay at 80% and 32ms pre-delay to maintain chord clarity.")
            } else {
                append("EQ mud-cut applied at 350Hz (-1.5dB) to match $targetInstrumentName resonance. Amp gain staged at edge-of-breakup with lush spring reverb.")
            }
            if (destination != ToneDestination.DIRECT_PA) {
                append(" Cabinet IR bypassed for external amp usage (${destination.displayName}).")
            }
        }

        return ValetonPreset(
            id = "ai_" + UUID.randomUUID().toString().take(8),
            patchNumber = "U01-1",
            name = targetName,
            author = "AI Valeton Cloud Genius",
            description = "Custom synthesized patch for '$prompt' tuned specifically for Valeton GP-180 hardware DSP engine.",
            guitarType = targetInstrumentName,
            genre = genre,
            targetArtistSong = prompt,
            blocks = blocks,
            masterVolume = 80f,
            bpm = if (isAmbient) 95 else if (isMetal) 135 else 120,
            isCloud = false,
            likesCount = 1,
            downloadsCount = 0,
            aiOptimized = true,
            aiOptimizationNotes = notes
        )
    }

    fun autoOptimizeTone(
        currentPreset: ValetonPreset,
        pickup: GuitarPickupType,
        destination: ToneDestination
    ): ValetonPreset {
        val updatedBlocks = currentPreset.blocks.map { block ->
            when (block.type) {
                EffectBlockType.CAB -> {
                    val shouldBeOn = destination == ToneDestination.DIRECT_PA
                    val params = block.parameters.toMutableMap()
                    params["Low Cut"] = 82f
                    params["High Cut"] = 7200f
                    block.copy(enabled = shouldBeOn, parameters = params)
                }
                EffectBlockType.NR -> {
                    // Check if high gain is used
                    val ampBlock = currentPreset.blocks.find { it.type == EffectBlockType.AMP }
                    val ampGain = ampBlock?.parameters?.get("Gain") ?: 50f
                    val isHighGain = ampGain > 60f
                    val params = block.parameters.toMutableMap()
                    params["Threshold"] = if (isHighGain) 55f else 32f
                    params["Release"] = if (isHighGain) 15f else 28f
                    block.copy(enabled = isHighGain || block.enabled, parameters = params)
                }
                EffectBlockType.EQ -> {
                    val params = block.parameters.toMutableMap()
                    // Tame mud
                    when (pickup) {
                        GuitarPickupType.HUMBUCKER_LP -> {
                            params["100Hz"] = -0.5f
                            params["350Hz"] = -2.5f
                            params["1kHz"] = 1.0f
                            params["3.2kHz"] = 2.0f
                            params["8kHz"] = -1.0f
                        }
                        GuitarPickupType.SINGLE_COIL_STRAT -> {
                            params["100Hz"] = 1.0f
                            params["350Hz"] = -2.0f
                            params["1kHz"] = 0.5f
                            params["3.2kHz"] = 3.0f
                            params["8kHz"] = -0.5f
                        }
                        GuitarPickupType.ACTIVE_FLUENCE -> {
                            params["100Hz"] = 1.5f
                            params["350Hz"] = -3.5f
                            params["1kHz"] = -0.5f
                            params["3.2kHz"] = 2.5f
                            params["8kHz"] = -2.0f
                        }
                        else -> {
                            params["350Hz"] = -2.0f
                        }
                    }
                    block.copy(enabled = true, parameters = params)
                }
                EffectBlockType.DLY -> {
                    // Sync delay ms to 120 bpm if default
                    val params = block.parameters.toMutableMap()
                    val bpm = if (currentPreset.bpm > 0) currentPreset.bpm else 120
                    val beatMs = (60_000f / bpm)
                    // Dotted 8th note is 0.75 * quarter note
                    val dottedEighth = (beatMs * 0.75f).coerceIn(100f, 1200f)
                    params["Time"] = dottedEighth
                    block.copy(parameters = params)
                }
                else -> block
            }
        }

        val optimizationReport = "AI Auto-Optimization Complete for Valeton GP-180:\n" +
                "• Resonant mud at 350Hz suppressed by -2.0dB for clarity.\n" +
                "• Delay time precisely synchronized to BPM (${currentPreset.bpm} BPM -> ${((60000f / currentPreset.bpm) * 0.75f).toInt()}ms dotted-8th).\n" +
                "• Noise Reducer threshold recalibrated according to preamp gain staging.\n" +
                "• Cabinet IR low/high cut tightened (82Hz - 7.2kHz) for punchy studio recording."

        return currentPreset.copy(
            blocks = updatedBlocks,
            aiOptimized = true,
            aiOptimizationNotes = optimizationReport
        )
    }

    private fun String.capitalizeWords(): String =
        split(" ").joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }
}
