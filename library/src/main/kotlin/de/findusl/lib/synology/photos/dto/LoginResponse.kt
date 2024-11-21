package de.findusl.lib.synology.photos.dto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
data class LoginData(
	@SerialName("device_id")
	val deviceId: String,
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("success")
sealed interface LoginResponse {
	@Serializable
	@SerialName("true")
	data class Success(
		val data: LoginData
	) : LoginResponse

	@Serializable
	@SerialName("false")
	data class Failure(
		val error: ErrorData
	) : LoginResponse
}
