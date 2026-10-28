package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PresetDao {
    @Query("SELECT * FROM valeton_presets ORDER BY patchNumber ASC")
    fun getAllPresetsFlow(): Flow<List<PresetEntity>>

    @Query("SELECT * FROM valeton_presets WHERE id = :id LIMIT 1")
    suspend fun getPresetById(id: String): PresetEntity?

    @Query("SELECT * FROM valeton_presets WHERE isFavorite = 1")
    fun getFavoritePresetsFlow(): Flow<List<PresetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPreset(preset: PresetEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPresets(presets: List<PresetEntity>)

    @Update
    suspend fun updatePreset(preset: PresetEntity)

    @Delete
    suspend fun deletePreset(preset: PresetEntity)

    @Query("DELETE FROM valeton_presets WHERE id = :id")
    suspend fun deletePresetById(id: String)

    @Query("SELECT COUNT(*) FROM valeton_presets")
    suspend fun count(): Int
}
