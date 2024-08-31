package de.lehrbaum.lib.synology.photos

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.ParametersBuilder

suspend fun HttpClient.getDefaultWithParams(parametersBlock: ParametersBuilder.() -> Unit) =
	get {
		url {
			parameters.parametersBlock()
		}
	}