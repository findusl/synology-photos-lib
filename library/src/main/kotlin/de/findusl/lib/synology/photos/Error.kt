package de.findusl.lib.synology.photos

sealed class Error(val humanReadable: String) {
	object OtpRequired : Error("OTP required for login.")
	object SessionExpired : Error("Session expired, login again.")
	object MissingField : Error("Missing required field.")

	class Unknown(humanReadable: String) : Error(humanReadable)

	class UnknownException(description: String, val exception: Exception) :
		Error(description + " " + exception.message) {
		fun toStringWithException() = toString() + '\n' + exception.stackTraceToString()
	}

	override fun toString() = humanReadable
}
