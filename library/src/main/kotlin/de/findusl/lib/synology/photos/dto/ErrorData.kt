package de.findusl.lib.synology.photos.dto

import kotlinx.serialization.Serializable

@Serializable
data class ErrorData(
	val code: Int, // Known value 119 for session expired, 403 for missing otp, 120 for missing field
	val errors: ErrorDetail?,
) {

	// Awful to parse type safe...
	@Serializable
	data class ErrorDetail(
		val types: List<ErrorType>?,
		val name: String?, // if there is a name there is usually a reason
		val reason: String?,
	)

	@Serializable
	data class ErrorType(
		val type: String // known values: otp for missing onetimepad
	)
}
