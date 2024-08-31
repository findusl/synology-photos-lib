package de.lehrbaum.lib.synology.photos

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import de.lehrbaum.lib.synology.photos.dto.PeopleResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class PhotoSession internal constructor(private val client: HttpClient) {
    suspend fun peopleRequest(): Result<PeopleResponse.Success, Any> {
        val httpResponse = client.get {
            url {
                parameters.apply {
                    append("api", "SYNO.FotoTeam.Browse.Person")
                    append("version", "2")
                    append("offset", "0")
                    append("limit", "100")
                    append("method", "list")
                }
            }
        }
        val peopleBody = httpResponse.body<PeopleResponse>()
        if (peopleBody !is PeopleResponse.Success) {
            throw RuntimeException("PeopleRequest failed. status: " + httpResponse.status + " Body: " + httpResponse.bodyAsText())
        }
        return Ok(peopleBody)
    }

}
