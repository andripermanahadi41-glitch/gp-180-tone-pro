package com.example.model

data class ModuleCatalogItem(
    val name: String,
    val emulatedGear: String,
    val category: String,
    val description: String,
    val defaultParams: Map<String, Float>
)

object ValetonCatalog {

    val nrModels = listOf(
        ModuleCatalogItem(
            name = "Smart Noise Gate",
            emulatedGear = "Valeton Smart Digital Gate",
            category = "Noise Reduction",
            description = "Gerbang derau digital pintar untuk memotong desis/hum elektrik tanpa memotong sustain alami senar.",
            defaultParams = mapOf("Thre" to 20f, "Attack" to 15f, "Release" to 25f)
        )
    )

    val preModels = listOf(
        ModuleCatalogItem(
            name = "Dyna Comp",
            emulatedGear = "MXR Dyna Comp",
            category = "Compressor",
            description = "Kompresor klasik bernuansa perkusif tebal, sangat disukai untuk petikan bersih dan funk.",
            defaultParams = mapOf("Sustn" to 50f, "Attack" to 25f, "Volume" to 70f, "Tone" to 50f)
        ),
        ModuleCatalogItem(
            name = "S-Comp",
            emulatedGear = "Ross Compressor",
            category = "Compressor",
            description = "Kompresor transparan bernada halus dengan karakter legendaris Nashville.",
            defaultParams = mapOf("Sustn" to 45f, "Attack" to 30f, "Volume" to 68f, "Tone" to 50f)
        ),
        ModuleCatalogItem(
            name = "Keeley C4",
            emulatedGear = "Keeley 4-Knob Compressor",
            category = "Compressor",
            description = "Kompresor butik 4-knob dengan kejernihan nada tinggi dan kontrol clipping.",
            defaultParams = mapOf("Sustn" to 55f, "Attack" to 20f, "Volume" to 75f, "Tone" to 60f)
        ),
        ModuleCatalogItem(
            name = "Micro Boost",
            emulatedGear = "MXR Micro Amp",
            category = "Booster",
            description = "Clean boost murni untuk mengangkat level sinyal gitar tanpa mengubah karakter EQ.",
            defaultParams = mapOf("Sustn" to 10f, "Attack" to 30f, "Volume" to 80f, "Tone" to 50f)
        ),
        ModuleCatalogItem(
            name = "Xotic BB Pre",
            emulatedGear = "Xotic BB Preamp",
            category = "Booster / Preamp",
            description = "Preamp dorongan musikal tebal dengan sedikit kompresi tabung hangat.",
            defaultParams = mapOf("Sustn" to 30f, "Attack" to 30f, "Volume" to 72f, "Tone" to 58f)
        ),
        ModuleCatalogItem(
            name = "Fortin 14 Boost",
            emulatedGear = "Fortin Grind Boost",
            category = "Metal Booster",
            description = "Booster modern agresif untuk mengencangkan low-end sebelum masuk amplifier high gain.",
            defaultParams = mapOf("Sustn" to 15f, "Attack" to 15f, "Volume" to 85f, "Tone" to 70f)
        ),
        ModuleCatalogItem(
            name = "Acoustic Sim",
            emulatedGear = "Acoustic Guitar Simulator",
            category = "Guitar Sim",
            description = "Mengubah karakter pickup elektrik menjadi resonansi gitar akustik berbadan kayu.",
            defaultParams = mapOf("Sustn" to 35f, "Attack" to 25f, "Volume" to 75f, "Tone" to 65f)
        )
    )

    val wahModels = listOf(
        ModuleCatalogItem(
            name = "CryBaby",
            emulatedGear = "Dunlop CryBaby GCB95",
            category = "Wah-Wah",
            description = "Pedal wah paling legendaris dengan sapuan midrange klasik vokal.",
            defaultParams = mapOf("Position" to 50f, "Range" to 75f, "Pitch" to 0f, "Mix" to 100f)
        ),
        ModuleCatalogItem(
            name = "Vox V847",
            emulatedGear = "Vox V847 Wah",
            category = "Wah-Wah",
            description = "Karakter vintage wah Inggris era 60-an yang hangat dan organik.",
            defaultParams = mapOf("Position" to 50f, "Range" to 70f, "Pitch" to 0f, "Mix" to 100f)
        ),
        ModuleCatalogItem(
            name = "Petrucci Wah",
            emulatedGear = "Dunlop John Petrucci Custom",
            category = "Custom Wah",
            description = "Sapuan wah modern dengan jangkauan frekuensi ekstra lebar untuk solo nada tinggi.",
            defaultParams = mapOf("Position" to 50f, "Range" to 85f, "Pitch" to 0f, "Mix" to 100f)
        ),
        ModuleCatalogItem(
            name = "Pitch Bend (Whammy)",
            emulatedGear = "DigiTech Whammy",
            category = "Pitch FX",
            description = "Simulasi pedal Whammy untuk dive-bomb dan oktaf sapuan dinamis hingga +2 oktaf.",
            defaultParams = mapOf("Position" to 0f, "Range" to 100f, "Pitch" to 12f, "Mix" to 100f)
        ),
        ModuleCatalogItem(
            name = "Octaver",
            emulatedGear = "Boss OC-2 Octave",
            category = "Pitch FX",
            description = "Menambahkan nada oktaf bawah/atas sintetik yang tebal di bawah nada asli.",
            defaultParams = mapOf("Position" to 50f, "Range" to 50f, "Pitch" to -12f, "Mix" to 50f)
        ),
        ModuleCatalogItem(
            name = "Detune",
            emulatedGear = "Micro Pitch Detuner",
            category = "Pitch FX",
            description = "Menduplikasi nada dengan pergeseran beberapa cent nada untuk efek chorus mikro tebal.",
            defaultParams = mapOf("Position" to 50f, "Range" to 40f, "Pitch" to 1f, "Mix" to 40f)
        )
    )

    val dstModels = listOf(
        ModuleCatalogItem(
            name = "Green OD (TS808)",
            emulatedGear = "Ibanez TS808 Tube Screamer",
            category = "Overdrive",
            description = "Raja overdrive klasik dunia dengan mid-hump legendaris pemotong frekuensi bass berlebih.",
            defaultParams = mapOf("Gain" to 38f, "Tone" to 55f, "Volume" to 65f)
        ),
        ModuleCatalogItem(
            name = "OD 9 (TS9)",
            emulatedGear = "Ibanez TS9",
            category = "Overdrive",
            description = "Varian Tube Screamer dengan respon nada atas sedikit lebih tajam dan dinamis.",
            defaultParams = mapOf("Gain" to 42f, "Tone" to 60f, "Volume" to 65f)
        ),
        ModuleCatalogItem(
            name = "Yellow OD (SD-1)",
            emulatedGear = "Boss SD-1 Super Overdrive",
            category = "Overdrive",
            description = "Distorsi asimetris yang renyah dan sangat cocok untuk mendongkrak ampli Marshall.",
            defaultParams = mapOf("Gain" to 45f, "Tone" to 52f, "Volume" to 68f)
        ),
        ModuleCatalogItem(
            name = "Penesas (Klon Centaur)",
            emulatedGear = "Klon Centaur Gold",
            category = "Overdrive",
            description = "Overdrive transparan mitikal dengan dorongan midrange vokal harmonik hangat.",
            defaultParams = mapOf("Gain" to 35f, "Tone" to 50f, "Volume" to 75f)
        ),
        ModuleCatalogItem(
            name = "Force (OCD)",
            emulatedGear = "Fulltone OCD",
            category = "Overdrive",
            description = "Drive responsif bergaya amplifier tabung dengan dinamika sentuhan jari superior.",
            defaultParams = mapOf("Gain" to 52f, "Tone" to 50f, "Volume" to 65f)
        ),
        ModuleCatalogItem(
            name = "TaiChi (Zendrive)",
            emulatedGear = "Hermida Zendrive",
            category = "Boutique Overdrive",
            description = "Nada overdrive Robben Ford bergaya Dumble yang sangat halus dan bernyanyi.",
            defaultParams = mapOf("Gain" to 48f, "Tone" to 55f, "Volume" to 62f)
        ),
        ModuleCatalogItem(
            name = "Darktale (ProCo RAT)",
            emulatedGear = "ProCo RAT Distortion",
            category = "Distortion",
            description = "Pedal distorsi agresi tinggi dengan filter khas antara distorsi keras dan fuzz.",
            defaultParams = mapOf("Gain" to 65f, "Tone" to 45f, "Volume" to 60f)
        ),
        ModuleCatalogItem(
            name = "Chief (Guv'nor)",
            emulatedGear = "Marshall The Guv'nor",
            category = "Distortion",
            description = "Mengubah ampli clean apapun menjadi karakter stack Marshall JCM800 klasik.",
            defaultParams = mapOf("Gain" to 60f, "Tone" to 55f, "Volume" to 65f)
        ),
        ModuleCatalogItem(
            name = "Plustortion (MXR Dist+)",
            emulatedGear = "MXR Distortion+",
            category = "Distortion",
            description = "Distorsi hard-clipping Jerman bergaya 70-an ala Randy Rhoads.",
            defaultParams = mapOf("Gain" to 70f, "Tone" to 50f, "Volume" to 65f)
        ),
        ModuleCatalogItem(
            name = "Lazaro (Big Muff)",
            emulatedGear = "Electro-Harmonix Big Muff Pi",
            category = "Fuzz",
            description = "Dinding fuzz legendaris dengan sustain abadi dan bass raksasa ala Gilmour.",
            defaultParams = mapOf("Gain" to 75f, "Tone" to 48f, "Volume" to 60f)
        ),
        ModuleCatalogItem(
            name = "Red Haze (Fuzz Face)",
            emulatedGear = "Dallas Arbiter Fuzz Face",
            category = "Fuzz",
            description = "Fuzz germanium vintage Jimi Hendrix yang membersih saat volume gitar dikecilkan.",
            defaultParams = mapOf("Gain" to 80f, "Tone" to 50f, "Volume" to 65f)
        ),
        ModuleCatalogItem(
            name = "Black Bass (Darkglass B7K)",
            emulatedGear = "Darkglass Microtubes B7K",
            category = "Bass Drive",
            description = "Preamp drive modern ultra-tajam untuk bassis metal dan rock modern.",
            defaultParams = mapOf("Gain" to 55f, "Tone" to 65f, "Volume" to 70f)
        )
    )

    val snapToneModels = listOf(
        ModuleCatalogItem(
            name = "EVH 5150 III (NAM)",
            emulatedGear = "EVH 5150 III 100W Red Channel",
            category = "Neural Amp Modeler",
            description = "Tangkapan Neural NAM ampli modern high-gain paling dicari untuk riff chug dan shredding.",
            defaultParams = mapOf("Input Gain" to 65f, "Output Vol" to 75f, "Bass" to 50f, "Middle" to 60f, "Treble" to 65f)
        ),
        ModuleCatalogItem(
            name = "Revv Generator 120 (NAM)",
            emulatedGear = "Revv Generator 120 MKIII Purple",
            category = "Neural Amp Modeler",
            description = "Kloning NAM presisi butik Kanada dengan mid-punch terfokus dan saturasi jernih.",
            defaultParams = mapOf("Input Gain" to 68f, "Output Vol" to 72f, "Bass" to 48f, "Middle" to 65f, "Treble" to 68f)
        ),
        ModuleCatalogItem(
            name = "Victory Kraken (NAM)",
            emulatedGear = "Victory VX The Kraken Channel 2",
            category = "Neural Amp Modeler",
            description = "Distorsi Inggris modern garang untuk drop-tuning dan extended range guitar.",
            defaultParams = mapOf("Input Gain" to 70f, "Output Vol" to 70f, "Bass" to 52f, "Middle" to 58f, "Treble" to 62f)
        ),
        ModuleCatalogItem(
            name = "King of Tone (NAM)",
            emulatedGear = "Analog.Man King of Tone High Gain",
            category = "Neural Pedal Capture",
            description = "Profil neural pedal butik legendaris penambah sustain alami dan harmonik tabung.",
            defaultParams = mapOf("Input Gain" to 55f, "Output Vol" to 78f, "Bass" to 50f, "Middle" to 55f, "Treble" to 58f)
        ),
        ModuleCatalogItem(
            name = "User NAM Slot 01",
            emulatedGear = "Custom User NAM Profile .nam",
            category = "Custom NAM Slot",
            description = "Slot kosong untuk menampung file profil tangkapan NAM dari PC/software via USB.",
            defaultParams = mapOf("Input Gain" to 50f, "Output Vol" to 75f, "Bass" to 50f, "Middle" to 50f, "Treble" to 50f)
        )
    )

    val ampModels = listOf(
        ModuleCatalogItem(
            name = "'65 Twin Reverb",
            emulatedGear = "Fender '65 Twin Reverb Blackface",
            category = "Clean / Boutique",
            description = "Pilihan utama suara clean kaca berkilau dengan headroom bersih masif.",
            defaultParams = mapOf("Gain" to 38f, "Master" to 75f, "Bass" to 55f, "Middle" to 60f, "Treble" to 68f, "Presence" to 55f)
        ),
        ModuleCatalogItem(
            name = "Tweed Deluxe",
            emulatedGear = "Fender 5E3 Tweed Deluxe",
            category = "Clean / Crunch",
            description = "Ampli tabung vintage era 50-an dengan karakter kompresi hangat saat dipetik keras.",
            defaultParams = mapOf("Gain" to 48f, "Master" to 70f, "Bass" to 58f, "Middle" to 55f, "Treble" to 60f, "Presence" to 50f)
        ),
        ModuleCatalogItem(
            name = "Vox AC30",
            emulatedGear = "Vox AC30 Top Boost",
            category = "Clean / Crunch",
            description = "Karakter 'British Chime' legendaris Brian May dan The Beatles yang renyah berkarakter.",
            defaultParams = mapOf("Gain" to 45f, "Master" to 78f, "Bass" to 42f, "Middle" to 65f, "Treble" to 75f, "Presence" to 70f)
        ),
        ModuleCatalogItem(
            name = "Matchless Chieftain",
            emulatedGear = "Matchless Chieftain 30W",
            category = "Clean / Boutique",
            description = "Ampli butik kelas atas dengan respon transien ultra-cepat dan dinamika senar organik.",
            defaultParams = mapOf("Gain" to 42f, "Master" to 80f, "Bass" to 50f, "Middle" to 62f, "Treble" to 70f, "Presence" to 65f)
        ),
        ModuleCatalogItem(
            name = "Roland JC-120",
            emulatedGear = "Roland Jazz Chorus JC-120",
            category = "Clean",
            description = "Ampli solid-state paling bersih di dunia, standar emas untuk era 80-an dan shoegaze.",
            defaultParams = mapOf("Gain" to 30f, "Master" to 75f, "Bass" to 60f, "Middle" to 50f, "Treble" to 70f, "Presence" to 60f)
        ),
        ModuleCatalogItem(
            name = "Marshall JCM800",
            emulatedGear = "Marshall JCM800 2203 Lead",
            category = "High Gain & Rock",
            description = "Pilar utama musik Hard Rock 80-an (Slash, Iron Maiden) dengan mid-growl bertenaga.",
            defaultParams = mapOf("Gain" to 65f, "Master" to 75f, "Bass" to 55f, "Middle" to 72f, "Treble" to 62f, "Presence" to 65f)
        ),
        ModuleCatalogItem(
            name = "Mesa Dual Recto",
            emulatedGear = "Mesa/Boogie Dual Rectifier",
            category = "High Gain & Rock",
            description = "High-gain dinding suara modern dengan low-end bergemuruh untuk Nu-Metal dan Punk Rock.",
            defaultParams = mapOf("Gain" to 72f, "Master" to 70f, "Bass" to 45f, "Middle" to 60f, "Treble" to 68f, "Presence" to 70f)
        ),
        ModuleCatalogItem(
            name = "Peavey 5150",
            emulatedGear = "Peavey 5150 Block Letter Lead",
            category = "High Gain & Rock",
            description = "Standar industri musik Metalcore dan modern metal dengan distorsi saturasi padat.",
            defaultParams = mapOf("Gain" to 70f, "Master" to 72f, "Bass" to 48f, "Middle" to 64f, "Treble" to 65f, "Presence" to 68f)
        ),
        ModuleCatalogItem(
            name = "Friedman BE-50",
            emulatedGear = "Friedman BE-50 Brown Eye",
            category = "High Gain & Rock",
            description = "Modifikasi ampli Marshall 'Brown Sound' butik terbaik dengan respon mewah dan nada vokal.",
            defaultParams = mapOf("Gain" to 68f, "Master" to 74f, "Bass" to 52f, "Middle" to 68f, "Treble" to 64f, "Presence" to 62f)
        ),
        ModuleCatalogItem(
            name = "Diezel VH4",
            emulatedGear = "Diezel VH4 Channel 4",
            category = "High Gain & Rock",
            description = "Distorsi presisi Jerman yang digunakan Tool dan Metallica untuk palm mute brutal.",
            defaultParams = mapOf("Gain" to 75f, "Master" to 70f, "Bass" to 50f, "Middle" to 62f, "Treble" to 66f, "Presence" to 72f)
        ),
        ModuleCatalogItem(
            name = "Ampeg SVT",
            emulatedGear = "Ampeg SVT Classic Bass Head",
            category = "Bass Amp",
            description = "Monumen suara bass rock tabung 300W dengan pukulan dada yang solid dan hangat.",
            defaultParams = mapOf("Gain" to 45f, "Master" to 80f, "Bass" to 65f, "Middle" to 55f, "Treble" to 50f, "Presence" to 45f)
        ),
        ModuleCatalogItem(
            name = "Acoustic Preamp",
            emulatedGear = "Valeton AC Pre Clean",
            category = "Acoustic",
            description = "Preamp jernih linear untuk memperkuat pickup piezo gitar akustik elektrik.",
            defaultParams = mapOf("Gain" to 30f, "Master" to 85f, "Bass" to 50f, "Middle" to 50f, "Treble" to 60f, "Presence" to 55f)
        )
    )

    val cabModels = listOf(
        ModuleCatalogItem(
            name = "2x12 US 65 Jensen",
            emulatedGear = "Fender 2x12 Jensen C12N",
            category = "Open Back",
            description = "Kabinet terbuka dengan treble kaca bening dan bass santai.",
            defaultParams = mapOf("Mic Type" to 57f, "Distance" to 25f, "Center" to 18f, "Low Cut" to 80f, "High Cut" to 7500f)
        ),
        ModuleCatalogItem(
            name = "4x12 1960 Greenback",
            emulatedGear = "Marshall 1960AX Celestion G12M",
            category = "Closed Back",
            description = "Kabinet rock klasik penemu midrange kayu organik dan treble empuk.",
            defaultParams = mapOf("Mic Type" to 57f, "Distance" to 20f, "Center" to 15f, "Low Cut" to 80f, "High Cut" to 6800f)
        ),
        ModuleCatalogItem(
            name = "4x12 Recto V30",
            emulatedGear = "Mesa Boogie 4x12 Celestion Vintage 30",
            category = "Closed Back",
            description = "Kabinet high-gain paling populer di studio rekaman dunia dengan mid bite tajam.",
            defaultParams = mapOf("Mic Type" to 57f, "Distance" to 15f, "Center" to 12f, "Low Cut" to 85f, "High Cut" to 6500f)
        ),
        ModuleCatalogItem(
            name = "User IR Slot 01",
            emulatedGear = "Third-Party IR 1024/2048 pts",
            category = "User IR Slot",
            description = "Memuat file Impulse Response .wav kustom (OwnHammer, York Audio, Celestion).",
            defaultParams = mapOf("Mic Type" to 0f, "Distance" to 0f, "Center" to 0f, "Low Cut" to 80f, "High Cut" to 8000f)
        )
    )

    val eqModels = listOf(
        ModuleCatalogItem(
            name = "5-Band Guitar EQ",
            emulatedGear = "Mesa 5-Band Graphic Equalizer",
            category = "Graphic EQ",
            description = "5 pita geser frekuensi standar (100Hz, 350Hz, 1kHz, 3.2kHz, 8kHz) untuk membentuk tone.",
            defaultParams = mapOf("100Hz" to 0f, "350Hz" to -2f, "1kHz" to 1f, "3.2kHz" to 2f, "8kHz" to -1f)
        ),
        ModuleCatalogItem(
            name = "7-Band Graphic",
            emulatedGear = "Boss GE-7 Graphic Equalizer",
            category = "Graphic EQ",
            description = "Kontrol komprehensif 7 titik frekuensi gitar dari low hingga air band.",
            defaultParams = mapOf("100Hz" to 0f, "200Hz" to 0f, "400Hz" to -2f, "800Hz" to 1f, "1.6kHz" to 2f, "3.2kHz" to 1f, "6.4kHz" to 0f)
        ),
        ModuleCatalogItem(
            name = "Parametric Studio",
            emulatedGear = "Parametric Studio EQ with Q Factor",
            category = "Parametric EQ",
            description = "Bebas menentukan frekuensi tengah, lebar bandwidth (Q), dan penguatan dB.",
            defaultParams = mapOf("Freq" to 1000f, "Q" to 1.4f, "Gain" to 0f, "Low Cut" to 80f, "High Cut" to 10000f)
        )
    )

    val modModels = listOf(
        ModuleCatalogItem(
            name = "Analog Chorus",
            emulatedGear = "Boss CE-2 Analog Chorus",
            category = "Chorus",
            description = "Chorus analog BBD klasik yang memberikan ketebalan dimensi mewah.",
            defaultParams = mapOf("Rate" to 32f, "Depth" to 55f, "Tone" to 50f, "Mix" to 40f)
        ),
        ModuleCatalogItem(
            name = "Stereo Chorus",
            emulatedGear = "TC Electronic SCF Chorus",
            category = "Stereo Chorus",
            description = "Pemisahan stereo lebar dengan modulasi bersih studio tanpa kehilangan artikulasi.",
            defaultParams = mapOf("Rate" to 28f, "Depth" to 60f, "Tone" to 55f, "Mix" to 45f)
        ),
        ModuleCatalogItem(
            name = "Vintage Flanger",
            emulatedGear = "Electro-Harmonix Electric Mistress",
            category = "Flanger",
            description = "Efek jet-plane mengayun dengan resonansi regenerasi khas David Gilmour.",
            defaultParams = mapOf("Rate" to 20f, "Depth" to 65f, "Tone" to 50f, "Mix" to 40f)
        ),
        ModuleCatalogItem(
            name = "Phase 90",
            emulatedGear = "MXR Phase 90 Script Logo",
            category = "Phaser",
            description = "Phaser 4-stage legendaris Eddie Van Halen dengan ayunan frekuensi organik.",
            defaultParams = mapOf("Rate" to 35f, "Depth" to 60f, "Tone" to 50f, "Mix" to 50f)
        ),
        ModuleCatalogItem(
            name = "Optical Tremolo",
            emulatedGear = "Fender Optical Bias Tremolo",
            category = "Tremolo",
            description = "Denyut volume naik-turun alami bergaya ampli vintage era 60-an.",
            defaultParams = mapOf("Rate" to 40f, "Depth" to 60f, "Tone" to 50f, "Mix" to 60f)
        ),
        ModuleCatalogItem(
            name = "Uni-Vibe",
            emulatedGear = "Shin-ei Uni-Vibe",
            category = "Vibe / Rotary",
            description = "Modulasi fotolistrik khas Hendrix bernuansa chorus-phaser berputar.",
            defaultParams = mapOf("Rate" to 36f, "Depth" to 70f, "Tone" to 50f, "Mix" to 55f)
        )
    )

    val dlyModels = listOf(
        ModuleCatalogItem(
            name = "Digital Delay",
            emulatedGear = "Boss DD-3 Digital Delay",
            category = "Digital Delay",
            description = "Pantulan jernih kristal 1:1 tanpa penurunan kualitas nada pada setiap repetisi.",
            defaultParams = mapOf("Time" to 350f, "Feedback" to 30f, "Mix" to 25f, "Tone" to 60f)
        ),
        ModuleCatalogItem(
            name = "Analog BBD",
            emulatedGear = "Electro-Harmonix Memory Man",
            category = "Analog Delay",
            description = "Chip BBD dengan pantulan yang semakin hangat dan sedikit terdegradasi secara musikal.",
            defaultParams = mapOf("Time" to 380f, "Feedback" to 35f, "Mix" to 28f, "Tone" to 45f)
        ),
        ModuleCatalogItem(
            name = "Tape Echo",
            emulatedGear = "Roland RE-201 Space Echo",
            category = "Tape Delay",
            description = "Gema pita magnetik vintage dengan getaran wow/flutter organik dan saturasi alami.",
            defaultParams = mapOf("Time" to 420f, "Feedback" to 40f, "Mix" to 32f, "Tone" to 48f)
        ),
        ModuleCatalogItem(
            name = "Ping Pong Delay",
            emulatedGear = "Stereo Ping Pong Delay",
            category = "Stereo Delay",
            description = "Pantulan gema yang memantul dinamis antara speaker kiri dan kanan.",
            defaultParams = mapOf("Time" to 400f, "Feedback" to 35f, "Mix" to 30f, "Tone" to 50f)
        ),
        ModuleCatalogItem(
            name = "Reverse Delay",
            emulatedGear = "Psychedelic Reverse Delay",
            category = "Special Delay",
            description = "Membalikkan arah audio setiap repetisi untuk efek psikedelik atmosferik.",
            defaultParams = mapOf("Time" to 500f, "Feedback" to 25f, "Mix" to 35f, "Tone" to 50f)
        )
    )

    val rvbModels = listOf(
        ModuleCatalogItem(
            name = "Spring 63 Tank",
            emulatedGear = "Fender 1963 Spring Reverb Unit",
            category = "Spring Reverb",
            description = "Reverb per pegas vintage dengan 'drip' khas musik Surf dan Blues.",
            defaultParams = mapOf("PreDelay" to 10f, "Decay" to 45f, "Tone" to 55f, "Mix" to 25f)
        ),
        ModuleCatalogItem(
            name = "Lush Studio Hall",
            emulatedGear = "Lexicon 480L Concert Hall",
            category = "Hall Reverb",
            description = "Simulasi aula konser megah dengan ekor dengung panjang yang anggun.",
            defaultParams = mapOf("PreDelay" to 25f, "Decay" to 60f, "Tone" to 50f, "Mix" to 30f)
        ),
        ModuleCatalogItem(
            name = "Plate 140",
            emulatedGear = "EMT 140 Plate Reverb",
            category = "Plate Reverb",
            description = "Pelat logam studio legendaris yang memberikan kilau cerah dan halus pada gitar lead.",
            defaultParams = mapOf("PreDelay" to 15f, "Decay" to 50f, "Tone" to 60f, "Mix" to 25f)
        ),
        ModuleCatalogItem(
            name = "Shimmer Reverb",
            emulatedGear = "Modern Pitch Shimmer Reverb",
            category = "Ambient Reverb",
            description = "Menghasilkan lapisan pad string oktaf atas surgawi di belakang petikan gitar.",
            defaultParams = mapOf("PreDelay" to 30f, "Decay" to 75f, "Tone" to 65f, "Mix" to 45f)
        ),
        ModuleCatalogItem(
            name = "Cloud Space",
            emulatedGear = "Infinite Ambient Cloud",
            category = "Ambient Reverb",
            description = "Ruang angkasa tanpa batas untuk musik worship dan shoegaze instrumental.",
            defaultParams = mapOf("PreDelay" to 40f, "Decay" to 85f, "Tone" to 60f, "Mix" to 50f)
        )
    )

    val volModels = listOf(
        ModuleCatalogItem(
            name = "Linear Curve",
            emulatedGear = "Linear Volume Sweep",
            category = "Expression Volume",
            description = "Kenaikan volume merata berbanding lurus dari posisi tumit hingga jari.",
            defaultParams = mapOf("Min Vol" to 0f, "Max Vol" to 100f, "Curve" to 50f)
        ),
        ModuleCatalogItem(
            name = "Logarithmic Curve",
            emulatedGear = "Audio Taper Logarithmic",
            category = "Expression Volume",
            description = "Kurva logaritmik yang disesuaikan dengan sensitivitas pendengaran telinga manusia.",
            defaultParams = mapOf("Min Vol" to 0f, "Max Vol" to 100f, "Curve" to 30f)
        )
    )

    fun getModelsForType(type: EffectBlockType): List<ModuleCatalogItem> {
        return when (type) {
            EffectBlockType.NR -> nrModels
            EffectBlockType.PRE -> preModels
            EffectBlockType.WAH -> wahModels
            EffectBlockType.DST -> dstModels
            EffectBlockType.NS -> snapToneModels
            EffectBlockType.AMP -> ampModels
            EffectBlockType.CAB -> cabModels
            EffectBlockType.EQ -> eqModels
            EffectBlockType.MOD -> modModels
            EffectBlockType.DLY -> dlyModels
            EffectBlockType.RVB -> rvbModels
            EffectBlockType.VOL -> volModels
        }
    }
}
