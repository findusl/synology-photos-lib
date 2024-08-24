package de.lehrbaum.lib.synology.photos

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.URLProtocol
import io.ktor.http.path
import kotlinx.coroutines.runBlocking

fun main() {
	val client = HttpClient(CIO) {
		install(HttpCookies)
		install(Logging) {
			level = LogLevel.ALL
			logger = Logger.DEFAULT
		}
	}

	runBlocking {
		val response = client.get {
			url {
                protocol = URLProtocol.HTTP
				host = "" // TODO load from some config file
				port = 5000
				path("webapi/entry.cgi")

				this.parameters.apply {
					append("api", "SYNO.API.Info")
					append("version", "1")
					append("method", "query")
					append("query", "SYNO.API.Auth")
				}
			}
		}

		println(response.status)
		println(response.bodyAsText())
	}
	println("Done!")
}

/*
When otp is present:
{
  "data": {
    "account": "MyName",
    "device_id": "asdfasdf",
    "ik_message": "",
    "is_portal_port": false,
    "sid": "asdfasdf",
    "synotoken": "--------"
  },
  "success": true
}
 */

/*
Known deviceId no otp:
{
  "data": {
    "account": "MyName",
    "device_id": "asdfasdf",
    "ik_message": "",
    "is_portal_port": false,
    "sid": "asdfasdf",
    "synotoken": "--------"
  },
  "success": true
}
 */

/*
When otp is missing or unknown deviceId:
{
  "error": {
    "code": 403,
    "errors": {
      "token": "---",
      "types": [
        {
          "type": "otp"
        }
      ]
    }
  },
  "success": false
}
 */