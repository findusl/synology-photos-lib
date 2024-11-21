package de.findusl.lib.synology.photos

import com.github.michaelbull.result.Result

object Environment {
	suspend fun loginNoOtp(): Result<PhotoSession, Any> =
		PhotoSessionCreator.loginNoOtp(
			"http://${System.getenv("ip_address")}",
			System.getenv("username"),
			System.getenv("password"),
			System.getenv("device_id"),
		)
}
