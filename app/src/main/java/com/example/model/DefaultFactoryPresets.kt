package com.example.model

object DefaultFactoryPresets {

    fun createDefaultBlockList(): List<EffectBlock> {
        return listOf(
            EffectBlock(
                type = EffectBlockType.NR,
                enabled = true,
                selectedModel = "Gate 1",
                availableModels = listOf("Gate 1", "Gate 2", "Studio Expander", "Smart Noise Gate"),
                parameters = mapOf("Threshold" to 20f, "Release" to 25f, "Detect" to 1f)
            ),
            EffectBlock(
                type = EffectBlockType.PRE,
                enabled = true,
                selectedModel = "Valeton Dyna-Comp",
                availableModels = listOf("Valeton Dyna-Comp", "Pure Clean Boost", "Vintage Preamp", "Acoustic Sim"),
                parameters = mapOf("Sustain" to 45f, "Attack" to 30f, "Level" to 70f, "Clipping" to 15f)
            ),
            EffectBlock(
                type = EffectBlockType.WAH,
                enabled = false,
                selectedModel = "Cry Wah 84",
                availableModels = listOf("Cry Wah 84", "Vintage Auto-Wah", "Bass Touch Wah"),
                parameters = mapOf("Position" to 50f, "Range" to 75f, "Mix" to 100f)
            ),
            EffectBlock(
                type = EffectBlockType.DST,
                enabled = false,
                selectedModel = "Green 808 Screamer",
                availableModels = listOf("Green 808 Screamer", "Blues Drive 62", "Rodent Dist", "Gold K-Centaur", "Fuzz Face 69", "Metal Zone Pro"),
                parameters = mapOf("Gain" to 38f, "Tone" to 58f, "Volume" to 65f)
            ),
            EffectBlock(
                type = EffectBlockType.NS,
                enabled = false,
                selectedModel = "Serial Routing",
                availableModels = listOf("Serial Routing", "Parallel Split", "FX Loop Send/Return"),
                parameters = mapOf("Send Vol" to 100f, "Return Vol" to 100f, "Balance" to 50f)
            ),
            EffectBlock(
                type = EffectBlockType.AMP,
                enabled = true,
                selectedModel = "65 US Twin Reverb",
                availableModels = listOf("65 US Twin Reverb", "British Plexi 1959", "UK 800 Lead", "Cali Rectifier 100", "SLO 100 Modern", "Class A AC30", "Jazz 120 Clean"),
                parameters = mapOf("Gain" to 42f, "Bass" to 55f, "Middle" to 62f, "Treble" to 68f, "Presence" to 50f, "Master" to 75f)
            ),
            EffectBlock(
                type = EffectBlockType.CAB,
                enabled = true,
                selectedModel = "2x12 US 65 Jensen",
                availableModels = listOf("1x12 Tweed Deluxe", "2x12 US 65 Jensen", "2x12 UK Celestion", "4x12 1960 Greenback", "4x12 Recto V30", "Custom Studio IR"),
                parameters = mapOf("Mic Type" to 57f, "Distance" to 25f, "Center" to 18f, "Low Cut" to 80f, "High Cut" to 7500f)
            ),
            EffectBlock(
                type = EffectBlockType.EQ,
                enabled = true,
                selectedModel = "5-Band Graphic Studio",
                availableModels = listOf("5-Band Graphic Studio", "3-Band Parametric", "Guitar Mid Boost"),
                parameters = mapOf("100Hz" to 1.5f, "350Hz" to -2.0f, "1kHz" to 0.5f, "3.2kHz" to 2.0f, "8kHz" to -1.0f)
            ),
            EffectBlock(
                type = EffectBlockType.MOD,
                enabled = true,
                selectedModel = "Analog Lush Chorus",
                availableModels = listOf("Analog Lush Chorus", "Vintage Flanger", "Orange Phase 90", "Optical Tremolo", "Detune Doubler", "Uni-Vibe 68"),
                parameters = mapOf("Rate" to 32f, "Depth" to 55f, "Tone" to 50f, "Mix" to 40f)
            ),
            EffectBlock(
                type = EffectBlockType.DLY,
                enabled = false,
                selectedModel = "Warm Analog BBD Delay",
                availableModels = listOf("Warm Analog BBD Delay", "Digital Crystal Delay", "Tape Echo Space", "Ping Pong Stereo", "Dynamic Ducking Delay"),
                parameters = mapOf("Time" to 380f, "Feedback" to 35f, "Mix" to 28f, "Tone" to 45f)
            ),
            EffectBlock(
                type = EffectBlockType.RVB,
                enabled = true,
                selectedModel = "Lush Studio Hall",
                availableModels = listOf("Spring 63 Tank", "Lush Studio Hall", "Clean Room", "Plate 140", "Cloud Shimmer Ambient", "Church Cathedral"),
                parameters = mapOf("PreDelay" to 20f, "Decay" to 55f, "Tone" to 60f, "Mix" to 30f)
            ),
            EffectBlock(
                type = EffectBlockType.VOL,
                enabled = true,
                selectedModel = "Post Volume Pedal",
                availableModels = listOf("Post Volume Pedal", "Pre Volume Pedal"),
                parameters = mapOf("Volume" to 100f, "Min Vol" to 0f, "Curve" to 50f)
            )
        )
    }

    val factoryPresets: List<ValetonPreset> = listOf(
        ValetonPreset(
            id = "preset_terbang",
            patchNumber = "192",
            name = "terbang",
            author = "Valeton Artist",
            description = "Preset 'terbang' khas Valeton GP-180 dengan susunan chain NR, PRE, AMP, CAB, EQ, MOD, RVB, VOL.",
            guitarType = "Single Coil (Strat/Tele)",
            genre = "Indonesian Pop Rock / Clean",
            targetArtistSong = "The Fly / Gigi / Sheila On 7 - Terbang",
            blocks = createDefaultBlockList(),
            masterVolume = 80f,
            bpm = 118,
            isCloud = true,
            likesCount = 3890,
            downloadsCount = 12400,
            aiOptimized = true,
            aiOptimizationNotes = "AI Tuned: Gate Threshold diatur ke 20, Preamp kompresi transparan, chorus stereo lebar, dan hall reverb 55% decay."
        ),
        ValetonPreset(
            id = "preset_01",
            patchNumber = "P01-1",
            name = "Slow Dancing Strat",
            author = "John M. Tone Studio",
            description = "Iconic scoop-mid, glassy breakup edge-of-breakup tone with subtle warm analog delay and lush spring reverb.",
            guitarType = "Single Coil (Strat/Tele)",
            genre = "Blues / Pop",
            targetArtistSong = "John Mayer - Slow Dancing in a Burning Room",
            blocks = createDefaultBlockList().map { b ->
                if (b.type == EffectBlockType.DST) b.copy(enabled = true)
                else if (b.type == EffectBlockType.DLY) b.copy(enabled = true)
                else b
            },
            masterVolume = 78f,
            bpm = 92,
            isCloud = true,
            likesCount = 1248,
            downloadsCount = 4820,
            aiOptimized = true,
            aiOptimizationNotes = "AI Tuned: Scooped 350Hz by -2.5dB to eliminate neck pickup boominess; added 1.8dB at 3.2kHz for vocal-like articulation."
        ),
        ValetonPreset(
            id = "preset_02",
            patchNumber = "P01-2",
            name = "Polyphia Neo-Soul Thump",
            author = "Tim H. Tech Lab",
            description = "Super tight, hyper-articulate percussive clean tone with fast compression, bright top end, and chirpy attack.",
            guitarType = "Single Coil (Strat/Tele)",
            genre = "Neo-Soul / Math Rock",
            targetArtistSong = "Polyphia - Playing God / Ego Death",
            blocks = createDefaultBlockList().map { b ->
                if (b.type == EffectBlockType.AMP) b.copy(selectedModel = "Class A AC30")
                else if (b.type == EffectBlockType.CAB) b.copy(selectedModel = "2x12 UK Celestion")
                else b
            },
            masterVolume = 80f,
            bpm = 138,
            isCloud = true,
            likesCount = 2100,
            downloadsCount = 6750,
            aiOptimized = true,
            aiOptimizationNotes = "AI Tuned: High pass filter tightened at 95Hz; 3.2kHz boosted by +4.5dB for razor-sharp nylon/hybrid-picking snap."
        ),
        ValetonPreset(
            id = "preset_03",
            patchNumber = "P01-3",
            name = "Appetite Slash Crunch",
            author = "GNR Classic Rock",
            description = "Roaring Marshall JCM/Plexi crunch with warm Alnico II humbucker character, tight mid growl, and singing sustain.",
            guitarType = "Humbucker (Les Paul/PRS)",
            genre = "Hard Rock",
            targetArtistSong = "Guns N' Roses - Sweet Child O' Mine",
            blocks = createDefaultBlockList().map { b ->
                if (b.type == EffectBlockType.DST) b.copy(enabled = true, selectedModel = "Gold K-Centaur")
                else if (b.type == EffectBlockType.AMP) b.copy(selectedModel = "British Plexi 1959")
                else if (b.type == EffectBlockType.CAB) b.copy(selectedModel = "4x12 1960 Greenback")
                else if (b.type == EffectBlockType.DLY) b.copy(enabled = true)
                else b
            },
            masterVolume = 82f,
            bpm = 125,
            isCloud = true,
            likesCount = 1850,
            downloadsCount = 5900,
            aiOptimized = true,
            aiOptimizationNotes = "AI Tuned: +3.0dB push at 1kHz for vintage Marshall midrange punch; high cut dialed to 6.8kHz to tame harsh digital fizz."
        ),
        ValetonPreset(
            id = "preset_04",
            patchNumber = "P01-4",
            name = "Modern Djent & Metal Chug",
            author = "Misha Metal Lab",
            description = "Crushing, ultra-tight percussive high gain tone with 808 front boost, aggressive noise gate, and scooped mid punch.",
            guitarType = "Active / Fluence (Modern Metal)",
            genre = "Modern Metal / Djent",
            targetArtistSong = "Periphery / Architects / Gojira",
            blocks = createDefaultBlockList().map { b ->
                if (b.type == EffectBlockType.DST) b.copy(enabled = true, selectedModel = "Green 808 Screamer")
                else if (b.type == EffectBlockType.AMP) b.copy(selectedModel = "Cali Rectifier 100")
                else if (b.type == EffectBlockType.CAB) b.copy(selectedModel = "4x12 Recto V30")
                else if (b.type == EffectBlockType.NR) b.copy(enabled = true, parameters = mapOf("Threshold" to 65f, "Release" to 10f, "Detect" to 1f))
                else b
            },
            masterVolume = 85f,
            bpm = 140,
            isCloud = true,
            likesCount = 3420,
            downloadsCount = 8900,
            aiOptimized = true,
            aiOptimizationNotes = "AI Tuned: 808 gain zeroed with volume maxed to push preamp without mush; Noise Gate threshold clamped to 65 for staccato palm muting."
        )
    )
}
