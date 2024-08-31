package de.lehrbaum.lib.synology.photos.dto

import kotlinx.serialization.Serializable

@Serializable
data class ErrorData(
    val code: Int,
    val errors: ErrorTypes
) {

    @Serializable
    data class ErrorTypes(
        val types: List<ErrorType>
    )

    @Serializable
    data class ErrorType(
        val type: String // known values: otp for missing onetimepad
    )
}