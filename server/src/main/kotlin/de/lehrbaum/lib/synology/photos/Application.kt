package de.lehrbaum.lib.synology.photos

import com.github.michaelbull.result.coroutines.coroutineBinding
import de.lehrbaum.lib.synology.photos.server.startServer
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.path
import kotlinx.coroutines.runBlocking
import java.time.Instant
import java.time.ZoneId

fun main() {
	startServer()
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
}

private fun runSampleRequests() {
	runBlocking {
		coroutineBinding {
			val session = Environment.loginNoOtp().bind()
			val personData = session.suggestPeople("Mine").bind()
			println("Got Person " + personData.list[0])
			val dates = getTodayPastYears()
			val itemData = session.itemsForPersonAndDates(personData.list[0].id, dates).bind()
			println("Got ${itemData.list.size} items")
		}
	}
}

private fun getTodayPastYears(): List<Pair<Instant, Instant>> {
	val today = Instant.now().atZone(ZoneId.systemDefault()).toLocalDateTime().toLocalDate()
	return (1..20).map { i ->
		val datePast = today.minusYears(i.toLong())
		val startOfDay = datePast.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()
		val endOfDay = datePast.plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()
		startOfDay to endOfDay
	}
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
