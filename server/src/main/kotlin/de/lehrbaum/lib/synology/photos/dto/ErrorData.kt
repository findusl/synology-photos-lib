package de.lehrbaum.lib.synology.photos.dto

import kotlinx.serialization.Serializable

@Serializable
data class ErrorData(
	val code: Int, // Known value 119 for session expired, 403 for missing otp, 120 for missing field
	val errors: ErrorTypes?,
) {

	// Awful to parse type safe...
	@Serializable
	data class ErrorTypes(
		val types: List<ErrorType>?,
		val name: String?,
		val reason: String?,
	)

	@Serializable
	data class ErrorType(
		val type: String // known values: otp for missing onetimepad
	)
}