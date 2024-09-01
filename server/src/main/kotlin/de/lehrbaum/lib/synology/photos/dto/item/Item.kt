package de.lehrbaum.lib.synology.photos.dto.item

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item(
	@SerialName("id")
	val id: Int,
	@SerialName("additional")
    val itemAdditional: ItemAdditional = ItemAdditional(),
	@SerialName("filename")
    val filename: String,
	@SerialName("filesize")
    val filesize: Int,
	@SerialName("folder_id")
    val folderId: Int,
	@SerialName("owner_user_id")
    val ownerUserId: Int,
	@SerialName("time")
    val timeCreated: Long,
	@SerialName("type")
    val type: String // TODO can be enum of photo and video
)