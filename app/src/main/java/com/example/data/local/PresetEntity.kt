package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.model.EffectBlock
import com.example.model.ValetonPreset

@Entity(tableName = "valeton_presets")
data class PresetEntity(
    @PrimaryKey
    val id: String,
    val patchNumber: String,
    val name: String,
    val author: String,
    val description: String,
    val guitarType: String,
    val genre: String,
    val targetArtistSong: String,
    val blocks: List<EffectBlock>,
    val masterVolume: Float,
    val bpm: Int,
    val isCloud: Boolean,
    val likesCount: Int,
    val downloadsCount: Int,
    val aiOptimized: Boolean,
    val aiOptimizationNotes: String,
    val isFavorite: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis()
) {
    fun toDomain(): ValetonPreset = ValetonPreset(
        id = id,
        patchNumber = patchNumber,
        name = name,
        author = author,
        description = description,
        guitarType = guitarType,
        genre = genre,
        targetArtistSong = targetArtistSong,
        blocks = blocks,
        masterVolume = masterVolume,
        bpm = bpm,
        isCloud = isCloud,
        likesCount = likesCount,
        downloadsCount = downloadsCount,
        aiOptimized = aiOptimized,
        aiOptimizationNotes = aiOptimizationNotes
    )

    companion object {
        fun fromDomain(preset: ValetonPreset, isFavorite: Boolean = false): PresetEntity =
            PresetEntity(
                id = preset.id,
                patchNumber = preset.patchNumber,
                name = preset.name,
                author = preset.author,
                description = preset.description,
                guitarType = preset.guitarType,
                genre = preset.genre,
                targetArtistSong = preset.targetArtistSong,
                blocks = preset.blocks,
                masterVolume = preset.masterVolume,
                bpm = preset.bpm,
                isCloud = preset.isCloud,
                likesCount = preset.likesCount,
                downloadsCount = preset.downloadsCount,
                aiOptimized = preset.aiOptimized,
                aiOptimizationNotes = preset.aiOptimizationNotes,
                isFavorite = isFavorite
            )
    }
}
