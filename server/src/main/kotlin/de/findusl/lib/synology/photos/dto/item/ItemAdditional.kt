package de.findusl.lib.synology.photos.dto.item

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemAdditional(
    @SerialName("address")
    val address: Address? = null,
    @SerialName("orientation")
    val orientation: Int? = null,
    @SerialName("orientation_original")
    val orientationOriginal: Int? = null,
    @SerialName("resolution")
    val resolution: Resolution? = null,
    @SerialName("thumbnail")
    val thumbnail: Thumbnail? = null,
)