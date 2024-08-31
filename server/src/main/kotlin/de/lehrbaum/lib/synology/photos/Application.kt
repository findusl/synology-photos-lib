package de.lehrbaum.lib.synology.photos

import com.github.michaelbull.result.coroutines.coroutineBinding
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.path
import kotlinx.coroutines.runBlocking
import java.time.Instant

fun main() {
	runSampleRequests()
}

private fun runOtpLogin() {
	runBlocking {
		val result = PhotoSessionCreator.loginWithOtp(
			"http://${System.getenv("ip_address")}",
			System.getenv("username"),
			System.getenv("password"),
			"otp",
			System.getenv("device_name"),
		)
		if (result.isOk) {
			val deviceId = result.value.second
			println("Got deviceId $deviceId")
		}
	}
	println("Done!")
}

private fun runSampleRequests() {
	runBlocking {
		coroutineBinding {
			val session = PhotoSessionCreator.loginNoOtp(
				"http://${System.getenv("ip_address")}",
				System.getenv("username"),
				System.getenv("password"),
				System.getenv("device_id"),
			).bind()
			val personData = session.suggestPeople("Mine").bind()
			println("Got Person " + personData.list[0])
			val dates = listOf(Instant.ofEpochSecond(1690848000) to Instant.ofEpochSecond(1693526399))
			val itemData = session.itemsForPersonAndDates(personData.list[0].id, dates).bind()
			println("Got ${itemData.list.size} items")
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
