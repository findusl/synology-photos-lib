package de.findusl.lib.synology.photos.dto.item

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Metadata(
    @SerialName("audio_bitrate")
    val audioBitrate: Int,
    @SerialName("audio_channel")
    val audioChannel: Int,
    @SerialName("audio_codec")
    val audioCodec: String,
    @SerialName("audio_frequency")
    val audioFrequency: Int,
    @SerialName("container_type")
    val containerType: String,
    @SerialName("duration")
    val duration: Int,
    @SerialName("frame_bitrate")
    val frameBitrate: Int,
    @SerialName("framerate")
    val framerate: Double,
    @SerialName("orientation")
    val orientation: Int,
    @SerialName("resolution_x")
    val resolutionX: Int,
    @SerialName("resolution_y")
    val resolutionY: Int,
    @SerialName("video_bitrate")
    val videoBitrate: Int,
    @SerialName("video_codec")
    val videoCodec: String,
    @SerialName("video_level")
    val videoLevel: Int,
    @SerialName("video_profile")
    val videoProfile: Int
)