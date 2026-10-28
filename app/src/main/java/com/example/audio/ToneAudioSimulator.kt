package com.example.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import com.example.model.EffectBlockType
import com.example.model.ValetonPreset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sin

object ToneAudioSimulator {
    private var audioTrack: AudioTrack? = null
    private var playJob: Job? = null

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _waveformSamples = MutableStateFlow<FloatArray>(FloatArray(32) { 0.1f })
    val waveformSamples: StateFlow<FloatArray> = _waveformSamples.asStateFlow()

    fun previewPreset(scope: CoroutineScope, preset: ValetonPreset, riffType: String = "Auto") {
        stop()

        playJob = scope.launch(Dispatchers.Default) {
            try {
                _isPlaying.value = true
                val sampleRate = 22050
                val durationSec = 3.5f
                val totalSamples = (sampleRate * durationSec).toInt()

                // Check preset parameters
                val dstBlock = preset.blocks.find { it.type == EffectBlockType.DST }
                val ampBlock = preset.blocks.find { it.type == EffectBlockType.AMP }
                val modBlock = preset.blocks.find { it.type == EffectBlockType.MOD }
                val dlyBlock = preset.blocks.find { it.type == EffectBlockType.DLY }

                val dstGain = if (dstBlock?.enabled == true) (dstBlock.parameters["Gain"] ?: 30f) / 100f else 0f
                val ampGain = if (ampBlock?.enabled == true) (ampBlock.parameters["Gain"] ?: 40f) / 100f else 0.3f
                val totalDrive = (dstGain * 1.5f + ampGain * 1.8f).coerceIn(0.2f, 4.0f)

                val modMix = if (modBlock?.enabled == true) (modBlock.parameters["Mix"] ?: 0f) / 100f else 0f
                val dlyMix = if (dlyBlock?.enabled == true) (dlyBlock.parameters["Mix"] ?: 0f) / 100f else 0f

                // Musical frequencies: E minor pentatonic or modern progression (E2, G2, A2, B2, D3, E3)
                // 82.4Hz (E2), 123.5Hz (B2), 164.8Hz (E3), 196.0Hz (G3), 246.9Hz (B3), 329.6Hz (E4)
                val notes = when (riffType) {
                    "Chug" -> listOf(82.41, 82.41, 82.41, 98.0, 82.41, 82.41, 110.0, 103.8)
                    "Solo" -> listOf(329.63, 392.00, 440.00, 493.88, 587.33, 659.25)
                    "Ambient" -> listOf(164.81, 196.00, 246.94, 329.63, 392.00, 493.88)
                    else -> listOf(82.41, 123.47, 164.81, 196.00, 246.94, 329.63)
                }

                val buffer = ShortArray(totalSamples)
                val noteDurationSamples = totalSamples / notes.size
                val delaySamples = (sampleRate * 0.28).toInt()

                var currentNoteIdx = 0
                val liveVisual = FloatArray(32)

                for (i in 0 until totalSamples) {
                    val noteInRiff = (i / noteDurationSamples).coerceIn(0, notes.size - 1)
                    val noteFreq = notes[noteInRiff]
                    val localIndex = i % noteDurationSamples
                    val decay = (1.0f - (localIndex.toFloat() / noteDurationSamples)).coerceIn(0f, 1f)

                    // Fundamental + harmonics (guitar string resonance)
                    val t = i.toDouble() / sampleRate
                    var sample = sin(2.0 * PI * noteFreq * t) * 0.6 +
                            sin(2.0 * PI * (noteFreq * 2.0) * t) * 0.25 +
                            sin(2.0 * PI * (noteFreq * 3.0) * t) * 0.15

                    // Modulation (chorus pitch vibrato)
                    if (modMix > 0.05f) {
                        val wobble = sin(2.0 * PI * 3.5 * t) * (0.08 * modMix)
                        sample += sin(2.0 * PI * (noteFreq * (1.0 + wobble)) * t) * (0.4 * modMix)
                    }

                    // Amp & Overdrive Soft-Clipping (tanh distortion curve)
                    var driven = (sample * totalDrive)
                    if (driven > 0.85) driven = 0.85 + (driven - 0.85) * 0.2
                    else if (driven < -0.85) driven = -0.85 + (driven + 0.85) * 0.2
                    driven = driven.coerceIn(-1.0, 1.0)

                    // Delay echo repeats
                    var delayedSample = 0.0
                    if (dlyMix > 0.05f && i > delaySamples) {
                        delayedSample = (buffer[i - delaySamples] / 32767.0) * dlyMix * 0.6
                    }

                    val finalSample = ((driven * decay * 0.8 + delayedSample) * 32767.0).coerceIn(-32767.0, 32767.0)
                    buffer[i] = finalSample.toInt().toShort()

                    // Update live visualization periodically
                    if (i % 700 == 0) {
                        val bin = (i / 700) % 32
                        liveVisual[bin] = (abs(finalSample) / 32767.0f).toFloat().coerceIn(0.1f, 1.0f)
                        _waveformSamples.value = liveVisual.clone()
                    }
                }

                // Play PCM buffer
                val minSize = AudioTrack.getMinBufferSize(
                    sampleRate,
                    AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT
                )

                audioTrack = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(totalSamples * 2)
                    .setTransferMode(AudioTrack.MODE_STATIC)
                    .build()

                audioTrack?.write(buffer, 0, totalSamples)
                audioTrack?.play()

                // Wait until playback completes
                kotlinx.coroutines.delay((durationSec * 1000).toLong())
                _isPlaying.value = false
            } catch (e: Exception) {
                _isPlaying.value = false
            } finally {
                _isPlaying.value = false
            }
        }
    }

    fun stop() {
        playJob?.cancel()
        playJob = null
        try {
            audioTrack?.stop()
            audioTrack?.release()
        } catch (_: Exception) {}
        audioTrack = null
        _isPlaying.value = false
    }
}
