package de.lehrbaum.lib.synology.photos.server

import com.github.michaelbull.result.coroutines.coroutineBinding
import de.lehrbaum.lib.synology.photos.Environment
import de.lehrbaum.lib.synology.photos.JsonDeserializer
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respondBytesWriter
import io.ktor.server.response.respondText
import io.ktor.server.routing.RoutingContext
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.utils.io.copyTo
import kotlinx.serialization.encodeToString
import java.time.Instant
import java.time.ZoneId

fun startServer() {
	embeddedServer(CIO, port = 8080) {
		// Install essential features
		install(CallLogging)
		install(ContentNegotiation)

		// Define the routing for the server
		routing {
			get("day-image", RoutingContext::getDayImage)
		}
	}.start(wait = true)
}

private suspend fun RoutingContext.getDayImage() {
	val result = coroutineBinding {
		val session = Environment.loginNoOtp().bind()
		val personData = session.suggestPeople("Mine").bind()
		println("Got Person " + personData.list[0])
		val dates = getTodayPastYears()
		val itemData = session.itemsForPersonAndDates(personData.list[0].id, dates).bind()
		println("Got ${itemData.list.size} items")
		if (itemData.list.isEmpty()) {
			call.respondText(text = "No pictures found for today :(", status = HttpStatusCode.NotFound)
			return@coroutineBinding
		}
		val chosenItem = itemData.list.random()
		println("Chose $chosenItem")
		val cacheKey = chosenItem.itemAdditional!!.thumbnail!!.cacheKey
		val response = session.itemThumbnail(chosenItem.id, cacheKey, "xl").bind()
		call.response.headers.append("X-Item", JsonDeserializer.encodeToString(chosenItem))
		call.respondBytesWriter(contentType = response.contentType(), status = response.status) {
			response.bodyAsChannel().copyTo(this) // Stream the content directly to the response
		}
	}
	if (result.isErr) {
		call.respondText("Got ${result.error}", status = HttpStatusCode.InternalServerError)
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
