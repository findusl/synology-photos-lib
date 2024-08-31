package de.lehrbaum.lib.synology.photos.dto

import de.lehrbaum.lib.synology.photos.JsonDeserializer
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull

class JsonDeserializerTest {
	@Test
	fun `test deserialization of otp missing response`() {
		val json = """
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
            }""".trimIndent()

		val result = JsonDeserializer.decodeFromString<LoginResponse>(json)

		assertIs<LoginResponse.Failure>(result)
		assertEquals(403, result.error.code)
		assertEquals("otp", result.error.errors!!.types!![0].type)
	}

	@Test
	fun `test deserialization of session expired response`() {
		val json = """
            {
              "error": {
                "code": 119
              },
              "success": false
            }""".trimIndent()

		val result = JsonDeserializer.decodeFromString<LoginResponse>(json)

		assertIs<LoginResponse.Failure>(result)
		assertEquals(119, result.error.code)
		assertNull(result.error.errors)
	}
}