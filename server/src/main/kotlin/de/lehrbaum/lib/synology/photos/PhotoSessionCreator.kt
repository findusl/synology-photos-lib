package de.lehrbaum.lib.synology.photos

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import de.lehrbaum.lib.synology.photos.dto.LoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import org.slf4j.LoggerFactory

object PhotoSessionCreator {

	/**
	 * @param baseUrl Protocol, Host and port of your nas. The requests will be sent
	 *      against `<baseUrl>/photo/webapi/entry.cgi` Example: http://192.168.148.10
	 */
	suspend fun loginNoOtp(
		baseUrl: String,
		username: String,
		password: String,
		deviceId: String,
	): Result<PhotoSession, Any> {
		val client = createHttpClient(baseUrl)
		val response = client.getDefaultWithParams {
			append("api", "SYNO.API.Auth")
			append("version", "7")
			append("method", "login")
			append("account", username)
			append("passwd", password)
			append("device_id", deviceId)
		}
		val loginResponse = response.body<LoginResponse>()
		when (loginResponse) {
			is LoginResponse.Failure -> {
				println("Login failed with response ${response.bodyAsText()}")
				throw RuntimeException("Got $loginResponse")
			}

			is LoginResponse.Success -> {
				return Ok(PhotoSession(client))
			}
		}
	}

	/**
	 * @param baseUrl Protocol, Host and port of your nas. The requests will be sent against `<baseUrl>/photo/webapi/entry.cgi`
	 *          Example: http://192.168.148.10
	 * @param deviceName A name for remembering this device. Null will in future not remember
	 */
	suspend fun loginWithOtp(
		baseUrl: String,
		username: String,
		password: String,
		otp: String,
		deviceName: String?,
	): Result<Pair<PhotoSession, String>, Any> {
		val client = createHttpClient(baseUrl)
		val response = client.getDefaultWithParams {
			append("api", "SYNO.API.Auth")
			append("version", "7")
			append("method", "login")
			// TODO support not remembering device. The response then does not have a deviceId which currently fails
			if (deviceName != null) {
				append("enable_device_token", "yes")
				append("device_name", deviceName)
			}
			append("account", username)
			append("passwd", password)
			append("otp_code", otp)
		}
		val loginResponse = response.body<LoginResponse>()
		when (loginResponse) {
			is LoginResponse.Failure -> {
				println("Login failed with response ${response.bodyAsText()}")
				throw RuntimeException("Got $loginResponse")
			}

			is LoginResponse.Success -> {
				return Ok(PhotoSession(client) to loginResponse.data.deviceId)
			}
		}
	}

	private fun createHttpClient(baseUrl: String) =
		HttpClient(CIO) {
			install(HttpCookies)
			install(ContentNegotiation) {
				json(JsonDeserializer)
			}
			install(HttpTimeout) {
				requestTimeoutMillis = 10000
			}
			defaultRequest {
				url("$baseUrl/photo/webapi/entry.cgi")
			}
			install(Logging) {
				level = LogLevel.INFO
				//disableLogging() // ContentNegotiation does not listen to Level.None
			}
		}

	private fun disableLogging() {
		val rootLogger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) as Logger
		rootLogger.setLevel(Level.OFF)
	}
}


