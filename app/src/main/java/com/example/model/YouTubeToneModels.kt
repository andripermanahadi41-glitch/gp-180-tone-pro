package com.example.model

enum class PlayingTechnique(val displayName: String, val description: String) {
    HYBRID_PICKING("Hybrid Picking & Tapping", "Fast percussive attack, scooped mids, dynamic volume snap"),
    PALM_MUTING_CHUG("Heavy Palm Muting & Chug", "Dense low-mid resonance, requires high-pass tightening & fast noise gate"),
    DYNAMIC_FINGERSTYLE("Fingerstyle & Ambient Chords", "Dynamic envelope, transparent optical compression, body warmth"),
    LEAD_SHRED_LEGATO("Lead Shredding & Fast Legato", "Singing sustain, mid-boost overdrive, smoothed top end & stereo delay"),
    VINTAGE_BLUES_BEND("Dynamic Blues Bending & Riffs", "Edge-of-breakup touch sensitivity, responsive amp sag & spring tank")
}

data class SpectralAnalysisData(
    val subBass: Float = 0.5f,      // 20 - 80 Hz
    val lowMids: Float = 0.6f,      // 80 - 300 Hz
    val bodyMud: Float = 0.7f,      // 300 - 600 Hz
    val vocalPunch: Float = 0.5f,   // 600 - 2500 Hz
    val presenceSnap: Float = 0.6f, // 2500 - 6000 Hz
    val airFizz: Float = 0.4f       // 6000 - 15000 Hz
)

data class YouTubeToneReference(
    val id: String,
    val title: String,
    val channel: String,
    val videoUrl: String,
    val genre: String,
    val keyRigBreakdown: String,
    val targetSpectrum: SpectralAnalysisData,
    val recommendedPreset: ValetonPreset,
    val aiInsight: String
)
