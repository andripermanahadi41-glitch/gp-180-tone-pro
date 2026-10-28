package com.example.data

import android.content.Context
import com.example.data.local.AppDatabase
import com.example.data.local.PresetEntity
import com.example.model.DefaultFactoryPresets
import com.example.model.ValetonPreset
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PresetRepository(private val context: Context) {
    private val database = AppDatabase.getDatabase(context)
    private val dao = database.presetDao()

    // Cloud community presets (in-memory synchronized cloud store)
    private val _cloudPresets = MutableStateFlow<List<ValetonPreset>>(DefaultFactoryPresets.factoryPresets)
    val cloudPresets: StateFlow<List<ValetonPreset>> = _cloudPresets.asStateFlow()

    fun getAllLocalPresets(): Flow<List<ValetonPreset>> {
        return dao.getAllPresetsFlow().map { list ->
            list.map { it.toDomain() }
        }
    }

    suspend fun getPresetById(id: String): ValetonPreset? = withContext(Dispatchers.IO) {
        dao.getPresetById(id)?.toDomain()
    }

    suspend fun savePreset(preset: ValetonPreset) = withContext(Dispatchers.IO) {
        dao.insertPreset(PresetEntity.fromDomain(preset))
    }

    suspend fun deletePreset(id: String) = withContext(Dispatchers.IO) {
        dao.deletePresetById(id)
    }

    suspend fun uploadToCloud(preset: ValetonPreset): Boolean = withContext(Dispatchers.IO) {
        val cloudPreset = preset.copy(
            id = "cloud_" + System.currentTimeMillis().toString().takeLast(6),
            isCloud = true,
            likesCount = 1,
            downloadsCount = 0
        )
        val current = _cloudPresets.value.toMutableList()
        current.add(0, cloudPreset)
        _cloudPresets.value = current
        true
    }

    suspend fun likeCloudPreset(presetId: String) = withContext(Dispatchers.IO) {
        val updated = _cloudPresets.value.map {
            if (it.id == presetId) it.copy(likesCount = it.likesCount + 1) else it
        }
        _cloudPresets.value = updated
    }

    suspend fun downloadCloudPreset(cloudPreset: ValetonPreset): ValetonPreset = withContext(Dispatchers.IO) {
        // Increment download count
        val updatedList = _cloudPresets.value.map {
            if (it.id == cloudPreset.id) it.copy(downloadsCount = it.downloadsCount + 1) else it
        }
        _cloudPresets.value = updatedList

        // Save into local database as new user patch
        val count = dao.count()
        val userPatchNum = "U" + String.format("%02d", (count + 1)) + "-1"
        val downloaded = cloudPreset.copy(
            id = "user_" + System.currentTimeMillis().toString().takeLast(6),
            patchNumber = userPatchNum,
            isCloud = false
        )
        dao.insertPreset(PresetEntity.fromDomain(downloaded))
        downloaded
    }
}
