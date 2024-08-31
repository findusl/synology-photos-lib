package de.lehrbaum.lib.synology.photos.dto

import de.lehrbaum.lib.synology.photos.JsonDeserializer
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class JsonDeserializerTest {
    @Test
    fun `test deserialization of LoginResponse`() {
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
        assertEquals("otp", result.error.errors.types[0].type)
    }
}