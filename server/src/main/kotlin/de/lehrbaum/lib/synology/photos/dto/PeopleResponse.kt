package de.lehrbaum.lib.synology.photos.dto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
data class PersonData(
	val list: List<Person>
)

@Serializable
data class Person(
	val cover: Int,
	val id: Int,
	@SerialName("item_count")
	val itemCount: Int,
	val name: String,
	val show: Boolean
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("success")
sealed interface PeopleResponse {
	@Serializable
	@SerialName("true")
	data class Success(
		val data: PersonData
	) : PeopleResponse

	@Serializable
	@SerialName("false")
	data class Failure(
		val error: ErrorData
	) : PeopleResponse
}
