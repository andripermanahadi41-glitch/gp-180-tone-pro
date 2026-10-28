package com.example.ui.i18n

import com.example.model.EffectBlockType
import com.example.model.GuitarPickupType
import com.example.model.BassPickupType
import com.example.model.ToneDestination
import com.example.model.InstrumentType

object AppStrings {

    fun get(lang: AppLanguage): Strings = when (lang) {
        AppLanguage.INDONESIAN -> IndonesianStrings
        AppLanguage.ENGLISH -> EnglishStrings
    }

    interface Strings {
        // App Identity
        val appName: String
        val suiteSubtitle: String
        val aiCloudTag: String
        val aiOptimized: String
        val testTone: String
        val stopTone: String
        val languageToggleTitle: String

        // Navigation Tabs
        val tabChainEditor: String
        val tabAiStudio: String
        val tabHardwareGuide: String
        val tabCloudLibrary: String
        val tabMyPresets: String

        // AI Studio Screen
        val aiStudioTitle: String
        val aiStudioSubtitle: String
        val modeBuilder: String
        val modeMatcher: String
        val instrumentLabel: String
        val instrumentSubtitle: String
        val guitarLabel: String
        val guitarDesc: String
        val bassLabel: String
        val bassDesc: String
        val pickupLabel: String
        val bassPickupLabel: String
        val genreLabel: String
        val destinationLabel: String
        val promptLabel: String
        val promptHint: String
        val generateButton: String
        val optimizingText: String
        val optimizationSuccess: String
        val quickPromptsTitle: String
        val aiAnalysisNotesTitle: String
        val footswitchSuggestionsTitle: String
        val applyToChainButton: String
        val appliedToChainNotice: String

        // YouTube Tone Matcher
        val ytMatcherTitle: String
        val ytMatcherSubtitle: String
        val ytSelectPrompt: String
        val ytUrlPlaceholder: String
        val ytAnalyzeCustomUrl: String
        val ytApplyMatch: String
        val ytToneDetected: String

        // Chain Editor Screen
        val chainEditorHeader: String
        val chainEditorSubtabEdit: String
        val chainEditorSubtabPatch: String
        val authentic12ModulesTitle: String
        val blockEnabled: String
        val blockBypassed: String
        val selectModelButton: String
        val resetBlockButton: String
        val savePresetButton: String
        val patchSettingsTitle: String
        val masterVolumeLabel: String
        val tempoBpmLabel: String
        val presetNameLabel: String

        // 12 Effect Block Names & Descriptions
        fun blockName(type: EffectBlockType): String
        fun blockDesc(type: EffectBlockType): String
        fun pickupName(pickup: GuitarPickupType): String
        fun bassPickupName(pickup: BassPickupType): String
        fun destinationName(destination: ToneDestination): String

        // Cloud Library Screen
        val cloudTitle: String
        val cloudSubtitle: String
        val cloudSearchPlaceholder: String
        val cloudGenreAll: String
        val downloadButton: String
        val downloadedStatus: String
        val likesCount: String
        val downloadsCount: String
        val uploadMyPresetButton: String
        val syncSuccessNotice: String

        // Hardware Guide & Dial Screen
        val hardwareTitle: String
        val hardwareSubtitle: String
        val footswitchSectionTitle: String
        val ioPortsTitle: String
        val directAccessButtonsTitle: String
        val exportJsonTitle: String
        val copyJsonButton: String
        val jsonCopiedNotice: String

        // My Presets Screen
        val myPresetsTitle: String
        val myPresetsSubtitle: String
        val loadPresetAction: String
        val duplicatePresetAction: String
        val deletePresetAction: String
        val renamePresetAction: String
        val exportPresetAction: String
        val deleteConfirmTitle: String
        val deleteConfirmMessage: String
        val confirmDelete: String
        val cancel: String
        val save: String
    }

    private object IndonesianStrings : Strings {
        override val appName = "GP180 Tone Pro"
        override val suiteSubtitle = "Suite DSP Multi-Efek Valeton"
        override val aiCloudTag = "AI CLOUD"
        override val aiOptimized = "OPTIMASI AI"
        override val testTone = "TES NADA"
        override val stopTone = "BERHENTI"
        override val languageToggleTitle = "Ganti Bahasa (ID / EN)"

        override val tabChainEditor = "Edit Rantai"
        override val tabAiStudio = "Studio AI"
        override val tabHardwareGuide = "Dial GP-180"
        override val tabCloudLibrary = "Cloud"
        override val tabMyPresets = "Kelola"

        override val aiStudioTitle = "Optimasi Nada Cerdas GP-180"
        override val aiStudioSubtitle = "Algoritma AI menganalisis pickup, instrumen, dan gaya bermain untuk meracik preset 12 blok Valeton GP-180 secara otomatis."
        override val modeBuilder = "Penyusun Preset AI"
        override val modeMatcher = "Pencocok Nada YouTube"
        override val instrumentLabel = "Mode Instrumen"
        override val instrumentSubtitle = "Pilih instrumen untuk algoritma tuning respon frekuensi rendah atau tinggi Valeton GP-180"
        override val guitarLabel = "GITAR"
        override val guitarDesc = "Frekuensi mid/high, overdrive harmonik, & ampli tabung"
        override val bassLabel = "BASS"
        override val bassDesc = "Optimasi low-end, low-cut 35Hz, kompresor optik, & saturasi bass"
        override val pickupLabel = "Tipe Pickup Gitar"
        override val bassPickupLabel = "Tipe Pickup Bass"
        override val genreLabel = "Genre & Karakter Suara"
        override val destinationLabel = "Target Output (Destinasi Suara)"
        override val promptLabel = "Deskripsi Karakter Nada Impian"
        override val promptHint = "Contoh: Solo blues creamy John Mayer, distorsi modern metal djent, atau slap bass punchy Flea"
        override val generateButton = "HASILKAN PRESET OPTIMAL DENGAN AI"
        override val optimizingText = "Menganalisis & meracik DSP GP-180..."
        override val optimizationSuccess = "Preset Berhasil Dioptimasi!"
        override val quickPromptsTitle = "Inspirasi Karakter Cepat"
        override val aiAnalysisNotesTitle = "Catatan Analisis Algoritma AI"
        override val footswitchSuggestionsTitle = "Pemetaan Footswitch GP-180 yang Disarankan"
        override val applyToChainButton = "TERAPKAN KE RANTAI AKTIF"
        override val appliedToChainNotice = "Preset AI berhasil diterapkan ke slot aktif!"

        override val ytMatcherTitle = "Pencocok Nada Referensi YouTube"
        override val ytMatcherSubtitle = "Deteksi profil EQ, tingkat saturasi, dan modul rantai sinyal dari artis dan lagu populer di YouTube."
        override val ytSelectPrompt = "Pilih Contoh Nada Artis atau Masukkan URL YouTube:"
        override val ytUrlPlaceholder = "https://youtube.com/watch?v=..."
        override val ytAnalyzeCustomUrl = "Analisis Video YouTube"
        override val ytApplyMatch = "Terapkan Nada Artis Ini"
        override val ytToneDetected = "Profil nada YouTube berhasil dianalisis!"

        override val chainEditorHeader = "Rantai Sinyal Autentik Valeton GP-180"
        override val chainEditorSubtabEdit = "Edit Efek"
        override val chainEditorSubtabPatch = "Pengaturan Patch"
        override val authentic12ModulesTitle = "12 Modul Rantai Sinyal GP-180"
        override val blockEnabled = "AKTIF"
        override val blockBypassed = "BYPASS"
        override val selectModelButton = "Ganti Model Efek"
        override val resetBlockButton = "Reset Parameter"
        override val savePresetButton = "Simpan Preset"
        override val patchSettingsTitle = "Pengaturan Global Patch"
        override val masterVolumeLabel = "Volume Master"
        override val tempoBpmLabel = "Tempo (BPM)"
        override val presetNameLabel = "Nama Patch"

        override fun blockName(type: EffectBlockType): String = when (type) {
            EffectBlockType.NR -> "NR (Noise Reduction)"
            EffectBlockType.PRE -> "PRE (Pre-Efek / Kompresor)"
            EffectBlockType.WAH -> "WAH (Wah-Wah & Pitch FX)"
            EffectBlockType.DST -> "DST (Distorsi / Overdrive)"
            EffectBlockType.NS -> "N→S (SnapTone / NAM Player)"
            EffectBlockType.AMP -> "AMP (Pemodelan Ampli)"
            EffectBlockType.CAB -> "CAB (Kabinet & IR Pihak Ketiga)"
            EffectBlockType.EQ -> "EQ (Equalizer Nada)"
            EffectBlockType.MOD -> "MOD (Modulasi - Chorus/Flanger)"
            EffectBlockType.DLY -> "DLY (Delay & Echo)"
            EffectBlockType.RVB -> "RVB (Reverb & Ruang Akustik)"
            EffectBlockType.VOL -> "VOL (Pedal Volume Master)"
        }

        override fun blockDesc(type: EffectBlockType): String = when (type) {
            EffectBlockType.NR -> "Menghilangkan desis dan dengungan pickup tanpa memotong sustain nada."
            EffectBlockType.PRE -> "Kompresor dinamika dan booster sinyal sebelum masuk ke drive."
            EffectBlockType.WAH -> "Filter wah dinamis, auto-wah, dan efek pergeseran nada (pitch shift)."
            EffectBlockType.DST -> "Beragam simulasi pedal overdrive, distorsi modern, hingga vintage fuzz."
            EffectBlockType.NS -> "Modul khusus pembaca model tangkapan Neural Amp Modeler (NAM)."
            EffectBlockType.AMP -> "Pemodelan amplifier tabung klasik, modern high-gain, dan bass rig."
            EffectBlockType.CAB -> "Simulasi kotak speaker multi-mic dan slot impulse response (IR)."
            EffectBlockType.EQ -> "Penyetelan frekuensi presisi untuk menonjolkan atau memotong frekuensi tertentu."
            EffectBlockType.MOD -> "Efek modulasi dimensional: Chorus, Phaser, Flanger, Tremolo, Vibrato."
            EffectBlockType.DLY -> "Pantulan suara delay analog tape, digital jernih, hingga ambient ping-pong."
            EffectBlockType.RVB -> "Simulasi ruangan Room, Hall, Plate, Spring vintage, hingga Shimmer luas."
            EffectBlockType.VOL -> "Kontrol level output akhir dan kurva pedal ekspresi volume."
        }

        override fun pickupName(pickup: GuitarPickupType): String = when (pickup) {
            GuitarPickupType.SINGLE_COIL_STRAT -> "Stratocaster Single Coil (Cerah & Renyah)"
            GuitarPickupType.HUMBUCKER_LP -> "Les Paul Humbucker (Tebal & Output Tinggi)"
            GuitarPickupType.P90 -> "P-90 Soapbar (Kasar & Vintage Mid-Push)"
            GuitarPickupType.ACTIVE_FLUENCE -> "Pickup Aktif / Fluence (High-Output Modern)"
            GuitarPickupType.ACOUSTIC_PIEZO -> "Piezo Akustik (Respons Dinamik Alami)"
        }

        override fun bassPickupName(pickup: BassPickupType): String = when (pickup) {
            BassPickupType.P_BASS -> "P-Bass Split Coil (Punch Klasik & Mid Bulat)"
            BassPickupType.J_BASS -> "J-Bass Dual Single (Mid-Scoop Slap & Jernih)"
            BassPickupType.STINGRAY_ACTIVE -> "StingRay Humbucker Aktif (Treble Renyah & Punch)"
            BassPickupType.MODERN_5STRING -> "Modern 5-String Active (Low B Rapat & Jernih)"
            BassPickupType.FRETLESS_PIEZO -> "Fretless / Akustik Piezo (Mwah Hangat & Sustain)"
        }

        override fun destinationName(destination: ToneDestination): String = when (destination) {
            ToneDestination.DIRECT_PA -> "PA Langsung / Audio Interface (CAB & IR Aktif)"
            ToneDestination.AMP_FRONT -> "Input Depan Ampli Clean Gitar (Bypass CAB)"
            ToneDestination.FX_LOOP_RETURN -> "Return FX Loop Poweramp Gitar (Bypass CAB)"
        }

        override val cloudTitle = "Preset Komunitas GP-180 Cloud"
        override val cloudSubtitle = "Jelajahi, dengarkan cuplikan, dan sinkronkan ratusan preset buatan gitaris dan basis dunia langsung ke unit Anda."
        override val cloudSearchPlaceholder = "Cari preset artis, genre, atau gaya..."
        override val cloudGenreAll = "Semua"
        override val downloadButton = "UNDUH KE GP-180"
        override val downloadedStatus = "TERSIMPAN"
        override val likesCount = "Suka"
        override val downloadsCount = "Unduhan"
        override val uploadMyPresetButton = "Bagikan Preset Saya ke Cloud"
        override val syncSuccessNotice = "Preset berhasil disinkronkan ke memori GP-180!"

        override val hardwareTitle = "Panduan Dial & I/O Valeton GP-180"
        override val hardwareSubtitle = "Diagram kontrol panel depan, fungsi tombol pintas fisik, dan pemetaan jack I/O belakang."
        override val footswitchSectionTitle = "Fungsi 3 Footswitch Multi-Guna (FS1, FS2, FS3)"
        override val ioPortsTitle = "Koneksi Port I/O Belakang"
        override val directAccessButtonsTitle = "Tombol Akses Langsung Panel Depan"
        override val exportJsonTitle = "Ekspor Format JSON untuk Software Editor PC"
        override val copyJsonButton = "Salin Data JSON ke Clipboard"
        override val jsonCopiedNotice = "Konfigurasi preset disalin! Tempel di Valeton GP-180 Editor PC."

        override val myPresetsTitle = "Kelola Preset Lokal GP-180"
        override val myPresetsSubtitle = "Atur slot bank A/B/C, buat salinan cadangan, dan sesuaikan bank nada pertunjukan Anda."
        override val loadPresetAction = "Muat ke Slot Aktif"
        override val duplicatePresetAction = "Duplikasi"
        override val deletePresetAction = "Hapus"
        override val renamePresetAction = "Ubah Nama"
        override val exportPresetAction = "Ekspor File .valepreset"
        override val deleteConfirmTitle = "Konfirmasi Hapus Preset"
        override val deleteConfirmMessage = "Apakah Anda yakin ingin menghapus preset ini dari penyimpanan lokal?"
        override val confirmDelete = "Hapus"
        override val cancel = "Batal"
        override val save = "Simpan"
    }

    private object EnglishStrings : Strings {
        override val appName = "GP180 Tone Pro"
        override val suiteSubtitle = "Valeton Multi-Effects DSP Suite"
        override val aiCloudTag = "AI CLOUD"
        override val aiOptimized = "AI OPTIMIZED"
        override val testTone = "TEST TONE"
        override val stopTone = "STOP"
        override val languageToggleTitle = "Switch Language (ID / EN)"

        override val tabChainEditor = "Edit"
        override val tabAiStudio = "AI Studio"
        override val tabHardwareGuide = "GP180 Dial"
        override val tabCloudLibrary = "Cloud"
        override val tabMyPresets = "Manage"

        override val aiStudioTitle = "GP-180 Smart Tone Optimization"
        override val aiStudioSubtitle = "Cloud AI algorithm analyzes your pickup, instrument, and playing style to automatically synthesize optimal 12-block Valeton GP-180 presets."
        override val modeBuilder = "Preset Builder"
        override val modeMatcher = "YouTube Tone Matcher"
        override val instrumentLabel = "Instrument Mode"
        override val instrumentSubtitle = "Select instrument to tailor Valeton GP-180 low-end or high-end DSP frequency response"
        override val guitarLabel = "GUITAR"
        override val guitarDesc = "Mid/high voicing, harmonic overdrive, & tube amp modeling"
        override val bassLabel = "BASS"
        override val bassDesc = "Low-end tuning, 35Hz low-cut, optical compressor, & bass saturation"
        override val pickupLabel = "Guitar Pickup Type"
        override val bassPickupLabel = "Bass Pickup Type"
        override val genreLabel = "Genre & Tone Vibe"
        override val destinationLabel = "Output Destination"
        override val promptLabel = "Dream Tone Description"
        override val promptHint = "e.g., John Mayer Slow Dancing Strat clean breakup, modern djent metal, or Flea slap funk punch"
        override val generateButton = "GENERATE OPTIMAL PRESET WITH AI"
        override val optimizingText = "Synthesizing GP-180 DSP parameters..."
        override val optimizationSuccess = "Preset Successfully Optimized!"
        override val quickPromptsTitle = "Quick Inspiration Presets"
        override val aiAnalysisNotesTitle = "AI Algorithm Analysis Notes"
        override val footswitchSuggestionsTitle = "Recommended GP-180 Footswitch Mapping"
        override val applyToChainButton = "APPLY TO ACTIVE CHAIN"
        override val appliedToChainNotice = "AI preset applied to active slot!"

        override val ytMatcherTitle = "YouTube Tone Matcher"
        override val ytMatcherSubtitle = "Detect EQ curves, drive levels, and signal chains from popular YouTube artist tone tests."
        override val ytSelectPrompt = "Select an Artist Reference or Enter a YouTube URL:"
        override val ytUrlPlaceholder = "https://youtube.com/watch?v=..."
        override val ytAnalyzeCustomUrl = "Analyze YouTube Video"
        override val ytApplyMatch = "Apply This Tone Match"
        override val ytToneDetected = "YouTube tone profile successfully analyzed!"

        override val chainEditorHeader = "Valeton GP-180 Authentic Signal Chain"
        override val chainEditorSubtabEdit = "Edit"
        override val chainEditorSubtabPatch = "Patch Settings"
        override val authentic12ModulesTitle = "12 Valeton GP-180 Effect Blocks"
        override val blockEnabled = "ON"
        override val blockBypassed = "BYPASS"
        override val selectModelButton = "Change Effect Model"
        override val resetBlockButton = "Reset Parameters"
        override val savePresetButton = "Save Preset"
        override val patchSettingsTitle = "Global Patch Settings"
        override val masterVolumeLabel = "Master Volume"
        override val tempoBpmLabel = "Tempo (BPM)"
        override val presetNameLabel = "Patch Name"

        override fun blockName(type: EffectBlockType): String = when (type) {
            EffectBlockType.NR -> "NR (Noise Reduction)"
            EffectBlockType.PRE -> "PRE (Pre-effects / Comp)"
            EffectBlockType.WAH -> "WAH (Wah & Pitch FX)"
            EffectBlockType.DST -> "DST (Distortion / Overdrive)"
            EffectBlockType.NS -> "N→S (SnapTone / NAM Player)"
            EffectBlockType.AMP -> "AMP (Amplifier Modeling)"
            EffectBlockType.CAB -> "CAB (Cabinet & 3rd-Party IR)"
            EffectBlockType.EQ -> "EQ (Tone Equalizer)"
            EffectBlockType.MOD -> "MOD (Modulation - Chorus/Flange)"
            EffectBlockType.DLY -> "DLY (Delay & Echo)"
            EffectBlockType.RVB -> "RVB (Reverb & Acoustic Space)"
            EffectBlockType.VOL -> "VOL (Master Volume Pedal)"
        }

        override fun blockDesc(type: EffectBlockType): String = when (type) {
            EffectBlockType.NR -> "Eliminates pickup hum and hiss without cutting off note decay."
            EffectBlockType.PRE -> "Dynamic compressors and clean signal boosters ahead of drive."
            EffectBlockType.WAH -> "Expressive wah sweeps, envelope auto-wah, and pitch shifting."
            EffectBlockType.DST -> "Legendary overdrives, high-gain distortion pedals, and vintage fuzzes."
            EffectBlockType.NS -> "Dedicated neural engine running Neural Amp Modeler (NAM) captures."
            EffectBlockType.AMP -> "Authentic component modeling of iconic tube, solid-state, and bass amps."
            EffectBlockType.CAB -> "Multi-microphone speaker cabinet simulation with custom IR loader."
            EffectBlockType.EQ -> "Precision tone sculpting across graphic and parametric frequency bands."
            EffectBlockType.MOD -> "Spacious dimension with Chorus, Flanger, Phaser, Tremolo, and Vibrato."
            EffectBlockType.DLY -> "Warm analog tape echoes, crisp digital repeats, and ping-pong delays."
            EffectBlockType.RVB -> "Vintage springs, lush studio plates, cavernous halls, and ethereal shimmers."
            EffectBlockType.VOL -> "Output stage gain riding and continuous expression volume swells."
        }

        override fun pickupName(pickup: GuitarPickupType): String = when (pickup) {
            GuitarPickupType.SINGLE_COIL_STRAT -> "Strat Single Coil (Bright & Chimey)"
            GuitarPickupType.HUMBUCKER_LP -> "Les Paul Humbucker (Warm High-Output)"
            GuitarPickupType.P90 -> "P-90 Soapbar (Gritty Vintage Push)"
            GuitarPickupType.ACTIVE_FLUENCE -> "Active / Fluence (Ultra High-Output Modern)"
            GuitarPickupType.ACOUSTIC_PIEZO -> "Piezo / Acoustic (Dynamic Natural Clarity)"
        }

        override fun bassPickupName(pickup: BassPickupType): String = when (pickup) {
            BassPickupType.P_BASS -> "P-Bass Split Coil (Classic Punch & Round Mids)"
            BassPickupType.J_BASS -> "J-Bass Dual Single (Mid-Scoop Slap & Growl)"
            BassPickupType.STINGRAY_ACTIVE -> "StingRay Active Humbucker (Aggressive Punch & Sizzle)"
            BassPickupType.MODERN_5STRING -> "Modern 5-String Active (Ultra-Tight Low B Clarity)"
            BassPickupType.FRETLESS_PIEZO -> "Fretless / Acoustic Piezo (Warm Wood Mwah & Sustain)"
        }

        override fun destinationName(destination: ToneDestination): String = when (destination) {
            ToneDestination.DIRECT_PA -> "Direct PA / Audio Interface (CAB & IR Active)"
            ToneDestination.AMP_FRONT -> "Clean Guitar Amp Front Input (Bypass CAB)"
            ToneDestination.FX_LOOP_RETURN -> "Guitar Poweramp FX Loop Return (Bypass CAB)"
        }

        override val cloudTitle = "GP-180 Community Cloud Presets"
        override val cloudSubtitle = "Browse, audition, and synchronize hundreds of tones handcrafted by guitarists & bassists worldwide."
        override val cloudSearchPlaceholder = "Search artist, genre, or vibe..."
        override val cloudGenreAll = "All"
        override val downloadButton = "DOWNLOAD TO GP-180"
        override val downloadedStatus = "SAVED"
        override val likesCount = "Likes"
        override val downloadsCount = "Downloads"
        override val uploadMyPresetButton = "Share My Preset to Cloud"
        override val syncSuccessNotice = "Preset successfully synchronized to GP-180 hardware memory!"

        override val hardwareTitle = "Valeton GP-180 Hardware & Dial Guide"
        override val hardwareSubtitle = "Front panel controls, quick-access physical buttons, and rear I/O connectivity."
        override val footswitchSectionTitle = "3 Multi-Function Footswitches (FS1, FS2, FS3)"
        override val ioPortsTitle = "Rear Panel I/O Connections"
        override val directAccessButtonsTitle = "Front Panel Direct Access Buttons"
        override val exportJsonTitle = "Export JSON Format for PC Editor Software"
        override val copyJsonButton = "Copy JSON Data to Clipboard"
        override val jsonCopiedNotice = "Preset JSON copied! Paste into Valeton GP-180 PC software."

        override val myPresetsTitle = "Manage GP-180 Local Presets"
        override val myPresetsSubtitle = "Organize patch banks A/B/C, back up custom tones, and prepare your gig setlist."
        override val loadPresetAction = "Load to Active Slot"
        override val duplicatePresetAction = "Duplicate"
        override val deletePresetAction = "Delete"
        override val renamePresetAction = "Rename"
        override val exportPresetAction = "Export .valepreset"
        override val deleteConfirmTitle = "Delete Preset Confirmation"
        override val deleteConfirmMessage = "Are you sure you want to permanently delete this preset from local storage?"
        override val confirmDelete = "Delete"
        override val cancel = "Cancel"
        override val save = "Save"
    }
}
