package com.example.data.local

import androidx.room.TypeConverter
import com.example.model.EffectBlock
import com.example.model.EffectBlockType
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class EffectBlockTypeAdapter {
    @FromJson
    fun fromJson(value: String): EffectBlockType {
        return when (value.trim().uppercase()) {
            "REV", "RVB", "REVERB" -> EffectBlockType.RVB
            "NR" -> EffectBlockType.NR
            "PRE" -> EffectBlockType.PRE
            "WAH" -> EffectBlockType.WAH
            "DST" -> EffectBlockType.DST
            "NS", "N➔S", "N->S" -> EffectBlockType.NS
            "AMP" -> EffectBlockType.AMP
            "CAB" -> EffectBlockType.CAB
            "EQ" -> EffectBlockType.EQ
            "MOD" -> EffectBlockType.MOD
            "DLY", "DELAY" -> EffectBlockType.DLY
            "VOL", "VOLUME" -> EffectBlockType.VOL
            else -> try {
                EffectBlockType.valueOf(value.trim().uppercase())
            } catch (_: Exception) {
                EffectBlockType.RVB
            }
        }
    }

    @ToJson
    fun toJson(type: EffectBlockType): String {
        return type.name
    }
}

class Converters {
    companion object {
        val moshiInstance: Moshi = Moshi.Builder()
            .add(EffectBlockTypeAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    private val listType = Types.newParameterizedType(List::class.java, EffectBlock::class.java)
    private val adapter = moshiInstance.adapter<List<EffectBlock>>(listType)

    @TypeConverter
    fun fromBlockList(blocks: List<EffectBlock>?): String {
        return if (blocks == null) "[]" else adapter.toJson(blocks)
    }

    @TypeConverter
    fun toBlockList(json: String?): List<EffectBlock> {
        if (json.isNullOrBlank()) return emptyList()
        val sanitized = json.replace("\"REV\"", "\"RVB\"")
        return try {
            adapter.fromJson(sanitized) ?: emptyList()
        } catch (_: Exception) {
            emptyList()
        }
    }
}
