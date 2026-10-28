package com.example.model

enum class EffectBlockType(val code: String, val fullName: String, val colorHex: Long) {
    NR("NR", "Noise Reducer", 0xFFE2E8F0),
    PRE("PRE", "Pre-Effects / Compressor", 0xFFD946EF),
    WAH("WAH", "Wah-Wah / Expression", 0xFF475569),
    DST("DST", "Distortion / Overdrive", 0xFF64748B),
    NS("N➔S", "Signal Routing", 0xFF334155),
    AMP("AMP", "Amplifier Simulator", 0xFFF59E0B),
    CAB("CAB", "Cabinet IR Simulator", 0xFFF97316),
    EQ("EQ", "Equalizer", 0xFFFDE68A),
    MOD("MOD", "Modulation", 0xFF06B6D4),
    DLY("DLY", "Delay Echo", 0xFF475569),
    RVB("RVB", "Reverb Space", 0xFF2563EB),
    VOL("VOL", "Volume Pedal", 0xFF10B981);

    val isTopRow: Boolean
        get() = this in listOf(NR, PRE, WAH, DST, NS, AMP)
}

data class EffectParam(
    val name: String,
    val min: Float,
    val max: Float,
    val value: Float,
    val unit: String = "",
    val step: Float = 1.0f
)

data class EffectBlock(
    val type: EffectBlockType,
    val enabled: Boolean,
    val selectedModel: String,
    val availableModels: List<String>,
    val parameters: Map<String, Float>
)

data class ValetonPreset(
    val id: String,
    val patchNumber: String,
    val name: String,
    val author: String = "Valeton Pro Lab",
    val description: String = "",
    val guitarType: String = "Single Coil Neck",
    val genre: String = "Rock",
    val targetArtistSong: String = "",
    val blocks: List<EffectBlock>,
    val masterVolume: Float = 75f,
    val bpm: Int = 120,
    val isCloud: Boolean = false,
    val likesCount: Int = 0,
    val downloadsCount: Int = 0,
    val aiOptimized: Boolean = false,
    val aiOptimizationNotes: String = ""
)

enum class InstrumentType(val displayName: String, val iconLabel: String) {
    GUITAR("Gitar Elektrik / Akustik", "GUITAR"),
    BASS("Bass Elektrik", "BASS")
}

enum class GuitarPickupType(val displayName: String, val desc: String) {
    SINGLE_COIL_STRAT("Single Coil (Strat/Tele)", "Bright, glassy, scooped mids, dynamic"),
    HUMBUCKER_LP("Humbucker (Les Paul/PRS)", "Warm, thick, high output, rich mids"),
    P90("P90 Soapbar", "Punchy grit, mid bite between single & bucker"),
    ACTIVE_FLUENCE("Active / Fluence (Modern Metal)", "Ultra high output, tight low end, sterile clarity"),
    ACOUSTIC_PIEZO("Piezo / Acoustic", "Full acoustic frequency, needs body resonance")
}

enum class BassPickupType(val displayName: String, val desc: String) {
    P_BASS("Precision Bass (Split-Coil)", "Thump tebal, punchy low-mid, Motown & Classic Rock"),
    J_BASS("Jazz Bass (Dual Single-Coil)", "Karakter growl, scoop mid untuk slap funk & fusion"),
    STINGRAY_ACTIVE("StingRay (Active Humbucker)", "Punchy agresif, treble renyah untuk modern slap"),
    MODERN_5STRING("Modern 5-String Active (Dingwall)", "Ultra-tight low B, kejernihan metal & djent"),
    FRETLESS_PIEZO("Fretless / Acoustic Piezo", "Resonansi kayu hangat, sustain mwah untuk jazz")
}

enum class ToneDestination(val displayName: String) {
    DIRECT_PA("Direct to PA / Audio Interface (IR ON)"),
    AMP_FRONT("Front of Clean Guitar Amp (IR OFF)"),
    FX_LOOP_RETURN("Guitar Poweramp FX Return (IR OFF)")
}
