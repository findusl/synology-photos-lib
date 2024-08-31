package de.lehrbaum.lib.synology.photos

import com.github.michaelbull.result.coroutines.coroutineBinding
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.path
import kotlinx.coroutines.runBlocking

fun main() {
	runBlocking {
		coroutineBinding {
			val session = PhotoSessionCreator.loginNoOtp(
				"http:// ${System.getenv("ip_address")}",
				System.getenv("username"),
				System.getenv("password"),
				System.getenv("device_id"),
			).bind()
			val peopleBody = session.peopleRequest().bind()
			println("Got " + peopleBody.data.list[0])
		}
	}
	println("Done!")
}

private suspend fun HttpClient.infoRequest(): HttpResponse =
	get {
		url {
			configureUrl()

			parameters.apply {
				append("api", "SYNO.API.Info")
				append("version", "1")
				append("method", "query")
				append("query", "SYNO.API.Auth")
			}
		}
	}

private fun URLBuilder.configureUrl() {
	protocol = URLProtocol.HTTP
	host = System.getenv("ip_address")
	path("photo/webapi/entry.cgi")
}
