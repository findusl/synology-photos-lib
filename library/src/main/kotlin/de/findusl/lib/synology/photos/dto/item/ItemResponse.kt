package de.findusl.lib.synology.photos.dto.item

import de.findusl.lib.synology.photos.dto.ErrorData
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("success")
sealed interface ItemResponse {
	@Serializable
	@SerialName("true")
	data class Success(
		@SerialName("data")
		val data: ItemData
	) : ItemResponse

	@Serializable
	@SerialName("false")
	data class Failure(
		@SerialName("error")
		val error: ErrorData
	) : ItemResponse
}
