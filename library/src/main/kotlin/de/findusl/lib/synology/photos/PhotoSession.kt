package de.findusl.lib.synology.photos

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import de.findusl.lib.synology.photos.dto.PeopleResponse
import de.findusl.lib.synology.photos.dto.PersonData
import de.findusl.lib.synology.photos.dto.item.ItemData
import de.findusl.lib.synology.photos.dto.item.ItemResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import java.time.Instant

class PhotoSession internal constructor(private val client: HttpClient) {
	suspend fun listPeople(): Result<PersonData, Any> {
		val httpResponse = client.getDefaultWithParams {
			append("api", "SYNO.FotoTeam.Browse.Person")
			append("method", "list")
			append("version", "2")
			append("offset", "0")
			append("limit", "100")
		}
		return convertPeopleResponse(httpResponse)
	}

	suspend fun suggestPeople(namePrefix: String): Result<PersonData, Any> {
		val httpResponse = client.getDefaultWithParams {
			append("api", "SYNO.FotoTeam.Browse.Person")
			append("method", "suggest")
			append("version", "2")
			append("name_prefix", namePrefix)
			append("limit", "1")
		}
		return convertPeopleResponse(httpResponse)
	}

	suspend fun itemsForPersonAndDates(
		personId: Int,
		dates: List<Pair<Instant, Instant>>
	): Result<ItemData, Any> {
		val httpResponse = client.getDefaultWithParams {
			append("api", "SYNO.FotoTeam.Browse.Item")
			append("method", "list_with_filter")
			append("version", "2")
			append("offset", "0")
			append("limit", "100")
			append("additional", "[\"thumbnail\"]")
			append("person", "[$personId]")
			append("person_policy", "and")
			val timeSpans = dates
				.map { (start, end) ->
					"{\"start_time\":${start.epochSecond},\"end_time\":${end.epochSecond}}"
				}.joinToString(separator = ",", prefix = "[", postfix = "]")
			append("time", timeSpans)
		}
		return convertItemResponse(httpResponse)
	}

	suspend fun itemThumbnail(
		itemId: Int,
		cacheKey: String,
		size: String, // Possible known values xl, sm
	): Result<HttpResponse, Any> {
		val httpResponse = client.getDefaultWithParams {
			append("api", "SYNO.FotoTeam.Thumbnail")
			append("method", "get")
			append("version", "2")
			append("id", itemId.toString())
			append("cache_key", cacheKey)
			append("type", "unit")
			append("size", size)
		}
		if (httpResponse.status.isSuccess()) {
			return Ok(httpResponse)
		} else {
			throw RuntimeException("Request failed with ${httpResponse.status}")
		}
	}

	private suspend fun convertPeopleResponse(httpResponse: HttpResponse): Result<PersonData, Nothing> {
		val peopleBody = httpResponse.body<PeopleResponse>()
		if (peopleBody !is PeopleResponse.Success) {
			throw RuntimeException("PeopleRequest failed. status: " + httpResponse.status + " Body: " + httpResponse.bodyAsText())
		}
		return Ok(peopleBody.data)
	}

	private suspend fun convertItemResponse(httpResponse: HttpResponse): Result<ItemData, Nothing> {
		val itemBody = httpResponse.body<ItemResponse>()
		if (itemBody !is ItemResponse.Success) {
			throw RuntimeException("PeopleRequest failed. status: " + httpResponse.status + " Body: " + httpResponse.bodyAsText())
		}
		return Ok(itemBody.data)
	}

}
