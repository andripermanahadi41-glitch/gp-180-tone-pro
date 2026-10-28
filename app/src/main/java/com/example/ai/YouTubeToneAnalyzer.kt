package com.example.ai

import com.example.model.BassPickupType
import com.example.model.DefaultFactoryPresets
import com.example.model.EffectBlock
import com.example.model.EffectBlockType
import com.example.model.GuitarPickupType
import com.example.model.InstrumentType
import com.example.model.PlayingTechnique
import com.example.model.SpectralAnalysisData
import com.example.model.ToneDestination
import com.example.model.ValetonPreset
import com.example.model.YouTubeToneReference
import java.util.UUID

object YouTubeToneAnalyzer {

    // Curated iconic YouTube Tone References for Guitar
    val curatedReferences: List<YouTubeToneReference> by lazy {
        listOf(
            YouTubeToneReference(
                id = "yt_01",
                title = "Paul Davids - That Secret John Mayer Strat Tone Breakdown",
                channel = "Paul Davids (Guitar)",
                videoUrl = "https://youtube.com/watch?v=paul_davids_mayer_tone",
                genre = "Blues / Pop",
                keyRigBreakdown = "65 Blackface Twin Reverb + Klon Centaur/TS10 + 2x12 Jensen + Analog Spring",
                targetSpectrum = SpectralAnalysisData(
                    subBass = 0.35f,
                    lowMids = 0.55f,
                    bodyMud = 0.30f,   // Scooped mud
                    vocalPunch = 0.65f,
                    presenceSnap = 0.85f, // Glassy bell chime
                    airFizz = 0.40f
                ),
                recommendedPreset = DefaultFactoryPresets.factoryPresets[0],
                aiInsight = "Mayer's tone relies on a deep -3dB scoop around 350Hz with a steep glass peak at 3.5kHz and tube rectifier sag."
            ),
            YouTubeToneReference(
                id = "yt_02",
                title = "Tim Henson - How I Get My Acoustic & Electric Hybrid Sound",
                channel = "Tim Henson (Polyphia)",
                videoUrl = "https://youtube.com/watch?v=tim_henson_hybrid_tone",
                genre = "Neo-Soul / Math Rock",
                keyRigBreakdown = "Fast Optical Compressor + Class A AC30 Top Boost + 2x12 Celestion Alnico + Detune Doubler",
                targetSpectrum = SpectralAnalysisData(
                    subBass = 0.20f,
                    lowMids = 0.40f,
                    bodyMud = 0.25f,
                    vocalPunch = 0.70f,
                    presenceSnap = 0.95f, // Ultra snap for thumping
                    airFizz = 0.60f
                ),
                recommendedPreset = DefaultFactoryPresets.factoryPresets[1],
                aiInsight = "High crest factor requires fast compressor attack (15ms) and high-pass cutoff at 95Hz to keep percussive hybrid picking articulate."
            ),
            YouTubeToneReference(
                id = "yt_03",
                title = "Ola Englund - WILL IT CHUG? Ultimate High-Gain Shootout",
                channel = "Ola Englund",
                videoUrl = "https://youtube.com/watch?v=ola_englund_will_it_chug",
                genre = "Modern Metal / Djent",
                keyRigBreakdown = "TS808 Boost (Gain:0, Vol:10) + Cali Rectifier Lead + 4x12 V30 IR + Hard Noise Gate",
                targetSpectrum = SpectralAnalysisData(
                    subBass = 0.45f,
                    lowMids = 0.80f,
                    bodyMud = 0.25f,   // Scooped mud for tight chug
                    vocalPunch = 0.85f,
                    presenceSnap = 0.75f,
                    airFizz = 0.30f    // High cut applied to prevent digital sizzle
                ),
                recommendedPreset = DefaultFactoryPresets.factoryPresets[3],
                aiInsight = "The 'Will It Chug' formula: Zero overdrive gain while maxing volume pushes preamp into tight saturation without flubby bass."
            ),
            YouTubeToneReference(
                id = "yt_04",
                title = "Rhett Shull - The Sound of a Cranked Vox AC30 at Edge of Breakup",
                channel = "Rhett Shull",
                videoUrl = "https://youtube.com/watch?v=rhett_shull_ac30_breakup",
                genre = "Indie / Classic Rock",
                keyRigBreakdown = "Class A AC30 Top Boost + Blues Driver + Tape Echo + Plate Reverb",
                targetSpectrum = SpectralAnalysisData(
                    subBass = 0.30f,
                    lowMids = 0.60f,
                    bodyMud = 0.45f,
                    vocalPunch = 0.80f,
                    presenceSnap = 0.70f,
                    airFizz = 0.50f
                ),
                recommendedPreset = DefaultFactoryPresets.factoryPresets[2],
                aiInsight = "British Class-A EL84 power amp compression delivers singing harmonic overtones when picking hard and sparkling cleans when rolling volume back."
            ),
            YouTubeToneReference(
                id = "yt_05",
                title = "Bernth - Liquid Lead Solo & Infinite Sustain Settings",
                channel = "BERNTH (Guitar Lessons)",
                videoUrl = "https://youtube.com/watch?v=bernth_liquid_lead_preset",
                genre = "Hard Rock / Shred",
                keyRigBreakdown = "Plexi 1959 Lead + Gold Klon Overdrive + Stereo Ping Pong Delay + Smooth Gate",
                targetSpectrum = SpectralAnalysisData(
                    subBass = 0.25f,
                    lowMids = 0.50f,
                    bodyMud = 0.35f,
                    vocalPunch = 0.90f,
                    presenceSnap = 0.65f,
                    airFizz = 0.35f
                ),
                recommendedPreset = DefaultFactoryPresets.factoryPresets[2],
                aiInsight = "Singing shred tone demands vocal mid-range saturation (1.2kHz push) and medium release noise gate to prevent cutting off note decays."
            )
        )
    }

    // Curated iconic YouTube Tone References for Bass
    val curatedBassReferences: List<YouTubeToneReference> by lazy {
        listOf(
            YouTubeToneReference(
                id = "yt_bass_01",
                title = "Flea - RHCP Slap Bass Tone Breakdown & Technique",
                channel = "Flea Bass Masterclass",
                videoUrl = "https://youtube.com/watch?v=flea_rhcp_slap_bass",
                genre = "Funk Rock / Slap",
                keyRigBreakdown = "Fast Optical Compressor + Gallien Krueger / Ampeg SVT + Envelope Filter + 4x10 Cab",
                targetSpectrum = SpectralAnalysisData(
                    subBass = 0.85f,
                    lowMids = 0.70f,
                    bodyMud = 0.25f,
                    vocalPunch = 0.65f,
                    presenceSnap = 0.90f,
                    airFizz = 0.35f
                ),
                recommendedPreset = AIToneOptimizer.generatePresetFromPrompt(
                    prompt = "Flea RHCP Slap Funk Punch Bass",
                    genre = "Funk Rock",
                    destination = ToneDestination.DIRECT_PA,
                    instrument = InstrumentType.BASS,
                    bassPickup = BassPickupType.STINGRAY_ACTIVE
                ),
                aiInsight = "Slap bass needs punchy 50-80Hz sub-bass thump and 4kHz high snap, with hollowed 400Hz mud."
            ),
            YouTubeToneReference(
                id = "yt_bass_02",
                title = "Marcus Miller - The Ultimate Sire Jazz Bass Slap & Tone Secrets",
                channel = "Marcus Miller Official",
                videoUrl = "https://youtube.com/watch?v=marcus_miller_jazz_bass",
                genre = "Jazz / Fusion",
                keyRigBreakdown = "Active 2-Band Preamp Boost + Ampeg SVT + Studio Chorus + 4x10 Horn",
                targetSpectrum = SpectralAnalysisData(
                    subBass = 0.80f,
                    lowMids = 0.60f,
                    bodyMud = 0.20f,
                    vocalPunch = 0.75f,
                    presenceSnap = 0.92f,
                    airFizz = 0.45f
                ),
                recommendedPreset = AIToneOptimizer.generatePresetFromPrompt(
                    prompt = "Marcus Miller Jazz Bass Slap Groove",
                    genre = "Jazz / Fusion",
                    destination = ToneDestination.DIRECT_PA,
                    instrument = InstrumentType.BASS,
                    bassPickup = BassPickupType.J_BASS
                ),
                aiInsight = "Marcus signature smile EQ curve: massive low-end punch and glassy high snap with transparent optical comp."
            ),
            YouTubeToneReference(
                id = "yt_bass_03",
                title = "Darkglass Microtubes B7K Ultra - Aggressive Modern Metal Bass",
                channel = "Darkglass Electronics",
                videoUrl = "https://youtube.com/watch?v=darkglass_metal_bass",
                genre = "Modern Metal / Djent",
                keyRigBreakdown = "Darkglass B7K Drive (Attack Boost, Grunt On) + Ampeg SVT + 8x10 Ampeg IR + Noise Gate",
                targetSpectrum = SpectralAnalysisData(
                    subBass = 0.90f,
                    lowMids = 0.75f,
                    bodyMud = 0.30f,
                    vocalPunch = 0.85f,
                    presenceSnap = 0.80f,
                    airFizz = 0.30f
                ),
                recommendedPreset = AIToneOptimizer.generatePresetFromPrompt(
                    prompt = "Darkglass B7K Ultra Modern Metal Bass",
                    genre = "Modern Metal",
                    destination = ToneDestination.DIRECT_PA,
                    instrument = InstrumentType.BASS,
                    bassPickup = BassPickupType.MODERN_5STRING
                ),
                aiInsight = "Parallel saturation keeps clean low-end sub frequencies while driving 2.8kHz high-mids for cut-through clank."
            )
        )
    }

    fun getReferencesForInstrument(instrument: InstrumentType): List<YouTubeToneReference> {
        return if (instrument == InstrumentType.BASS) curatedBassReferences else curatedReferences
    }

    /**
     * Algorithmic AI Tone Extraction:
     * Analyzes user's guitar profile (pickup, technique) and targets YouTube reference,
     * calculating acoustic delta: Delta(f) = Target(f) - UserGuitar(f)
     * and maps it directly onto Valeton GP-180 parameter blocks.
     */
    fun matchYouTubeTone(
        reference: YouTubeToneReference,
        userPickup: GuitarPickupType,
        technique: PlayingTechnique,
        destination: ToneDestination = ToneDestination.DIRECT_PA
    ): ValetonPreset {
        val base = reference.recommendedPreset
        val targetSpec = reference.targetSpectrum

        // 1. Calculate pickup natural resonance profile
        val userSpec = calculatePickupSpectralProfile(userPickup, technique)

        // 2. Compute spectral equalization delta
        val deltaSubBass = targetSpec.subBass - userSpec.subBass
        val deltaLowMids = targetSpec.lowMids - userSpec.lowMids
        val deltaBodyMud = targetSpec.bodyMud - userSpec.bodyMud
        val deltaVocalPunch = targetSpec.vocalPunch - userSpec.vocalPunch
        val deltaPresenceSnap = targetSpec.presenceSnap - userSpec.presenceSnap
        val deltaAirFizz = targetSpec.airFizz - userSpec.airFizz

        // 3. Map delta to Valeton GP-180 5-Band Graphic EQ & DSP Parameters
        val eq100 = (deltaLowMids * 6f).coerceIn(-6f, 6f)
        val eq350 = (deltaBodyMud * 8f).coerceIn(-8f, 4f)
        val eq1k = (deltaVocalPunch * 6f).coerceIn(-6f, 6f)
        val eq3k = (deltaPresenceSnap * 7f).coerceIn(-5f, 7f)
        val eq8k = (deltaAirFizz * 5f).coerceIn(-6f, 5f)

        // 4. Adjust dynamic compressor based on playing technique
        val compSustain = when (technique) {
            PlayingTechnique.HYBRID_PICKING -> 75f
            PlayingTechnique.DYNAMIC_FINGERSTYLE -> 60f
            PlayingTechnique.LEAD_SHRED_LEGATO -> 70f
            PlayingTechnique.PALM_MUTING_CHUG -> 25f
            PlayingTechnique.VINTAGE_BLUES_BEND -> 45f
        }
        val compAttack = when (technique) {
            PlayingTechnique.HYBRID_PICKING -> 15f
            PlayingTechnique.PALM_MUTING_CHUG -> 40f
            else -> 30f
        }

        // 5. Adjust Noise Reducer according to gain and palm-mute needs
        val isChug = technique == PlayingTechnique.PALM_MUTING_CHUG || reference.genre.contains("Metal", ignoreCase = true)
        val gateThreshold = if (isChug) 62f else 36f
        val gateRelease = if (isChug) 12f else 28f

        // 6. Build updated Valeton blocks
        val optimizedBlocks = base.blocks.map { block ->
            when (block.type) {
                EffectBlockType.PRE -> {
                    val params = block.parameters.toMutableMap()
                    params["Sustain"] = compSustain
                    params["Attack"] = compAttack
                    params["Level"] = 75f
                    block.copy(parameters = params)
                }
                EffectBlockType.NR -> {
                    val params = block.parameters.toMutableMap()
                    params["Threshold"] = gateThreshold
                    params["Release"] = gateRelease
                    block.copy(enabled = isChug || block.enabled, parameters = params)
                }
                EffectBlockType.EQ -> {
                    val params = block.parameters.toMutableMap()
                    params["100Hz"] = eq100
                    params["350Hz"] = eq350
                    params["1kHz"] = eq1k
                    params["3.2kHz"] = eq3k
                    params["8kHz"] = eq8k
                    block.copy(enabled = true, parameters = params)
                }
                EffectBlockType.CAB -> {
                    val shouldEnable = destination == ToneDestination.DIRECT_PA
                    val params = block.parameters.toMutableMap()
                    params["Low Cut"] = if (isChug) 90f else 80f
                    params["High Cut"] = if (isChug) 6500f else 7800f
                    block.copy(enabled = shouldEnable, parameters = params)
                }
                else -> block
            }
        }

        val analysisNotes = buildString {
            append("VALETON GP-180 AI ACOUSTIC OPTIMIZATION ALGORITHM REPORT:\n")
            append("Target Reference: '${reference.title}' (${reference.channel})\n")
            append("• Guitar Profile: ${userPickup.displayName} + ${technique.displayName}\n")
            append("• Spectral Compensation: 350Hz Notch: ${String.format("%.1f", eq350)}dB (Mud elimination), 3.2kHz Snap: ${String.format("+%.1f", eq3k)}dB\n")
            append("• Dynamic Envelope: Compressor Attack ${compAttack.toInt()}ms, Gate Threshold: ${gateThreshold.toInt()}%\n")
            append("• Hardware Translation: Matched to Valeton GP-180 DSP hardware engine with ${if (destination == ToneDestination.DIRECT_PA) "Studio IR Cab Sim" else "Poweramp Bypass"}.")
        }

        return base.copy(
            id = "yt_match_" + UUID.randomUUID().toString().take(6),
            patchNumber = "U01-1",
            name = "YT: " + reference.title.split("-").first().trim().take(18),
            author = "AI Tone Matcher",
            description = "AI Match of YouTube reference '${reference.title}' optimized for ${userPickup.displayName}.",
            guitarType = userPickup.displayName,
            genre = reference.genre,
            targetArtistSong = reference.title,
            blocks = optimizedBlocks,
            isCloud = false,
            aiOptimized = true,
            aiOptimizationNotes = analysisNotes
        )
    }

    fun calculatePickupSpectralProfile(
        pickup: GuitarPickupType,
        technique: PlayingTechnique
    ): SpectralAnalysisData {
        return when (pickup) {
            GuitarPickupType.SINGLE_COIL_STRAT -> SpectralAnalysisData(
                subBass = 0.25f,
                lowMids = 0.45f,
                bodyMud = 0.55f,   // Strats often have neck boom around 300Hz
                vocalPunch = 0.50f,
                presenceSnap = 0.85f,
                airFizz = 0.70f
            )
            GuitarPickupType.HUMBUCKER_LP -> SpectralAnalysisData(
                subBass = 0.45f,
                lowMids = 0.75f,
                bodyMud = 0.65f,
                vocalPunch = 0.80f,
                presenceSnap = 0.50f,
                airFizz = 0.35f
            )
            GuitarPickupType.ACTIVE_FLUENCE -> SpectralAnalysisData(
                subBass = 0.50f,
                lowMids = 0.80f,
                bodyMud = 0.40f,
                vocalPunch = 0.75f,
                presenceSnap = 0.80f,
                airFizz = 0.50f
            )
            GuitarPickupType.P90 -> SpectralAnalysisData(
                subBass = 0.35f,
                lowMids = 0.60f,
                bodyMud = 0.50f,
                vocalPunch = 0.75f,
                presenceSnap = 0.65f,
                airFizz = 0.55f
            )
            GuitarPickupType.ACOUSTIC_PIEZO -> SpectralAnalysisData(
                subBass = 0.20f,
                lowMids = 0.40f,
                bodyMud = 0.70f,
                vocalPunch = 0.45f,
                presenceSnap = 0.80f,
                airFizz = 0.85f
            )
        }
    }
}
