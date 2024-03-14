package dev.blackoutburst.gptest.stablediffusion

import dev.blackoutburst.gptest.dataclass.Payload
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.File
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

object StableDiffusion {

    private const val URL = "http://127.0.0.1:7860/sdapi/v1/txt2img"

    private val client = OkHttpClient.Builder()
        .callTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()

    private fun request(text: String): Response {
        val payload = Payload("masterpiece, best quality, highres, $text", 20)
        val jsonPayload = Json.encodeToString(payload)

        val request = Request.Builder()
            .url(URL)
            .post(jsonPayload.toRequestBody("application/json".toMediaType()))
            .build()

        return client.newCall(request).execute()
    }

    fun generate(text: String): File? {
        request(text).use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val responseBody = response.body?.string()
            val jsonObject = Json.parseToJsonElement(responseBody.orEmpty()).jsonObject
            val images = jsonObject["images"]?.jsonArray

            return images?.firstOrNull()?.jsonPrimitive?.content?.let { encodedImage ->
                val decodedBytes = Base64.getDecoder().decode(encodedImage)
                val file = File("output.png")
                file.writeBytes(decodedBytes)
                file
            }
        }
    }
}