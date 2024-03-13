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
import java.io.File
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

object StableDiffusion {

    fun generate(text: String): File? {
        val client = OkHttpClient.Builder()
            .callTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()

        val url = "http://127.0.0.1:7860/sdapi/v1/txt2img"

        val payload = Payload("masterpiece, best quality, highres, $text", 20)
        val jsonPayload = Json.encodeToString(payload)

        val request = Request.Builder()
            .url(url)
            .post(jsonPayload.toRequestBody("application/json".toMediaType()))
            .build()

        client.newCall(request).execute().use { response ->
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