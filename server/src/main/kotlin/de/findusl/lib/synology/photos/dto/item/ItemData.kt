package de.findusl.lib.synology.photos.dto.item

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemData(
    @SerialName("list")
    val list: List<Item>
)