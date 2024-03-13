package dev.blackoutburst.gptest.http

import dev.blackoutburst.gptest.BuildConfig
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit

object Fetcher {

    private val client = OkHttpClient.Builder()
        .callTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()

    private val HEADER = "application/json; charset=utf-8".toMediaType()

    fun get(url: String, key: String, endpoint: String, onError: () -> Unit = {}): String? {
        return try {
            val request = Request.Builder()
                .url("$url/$endpoint")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer $key")
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    response.body?.string()?.let { println(it) }

                    throw IOException("Unexpected code $response")
                }

                response.body?.string() ?: throw IOException("Unexpected null response body")
            }
        } catch (e: IOException) {
            onError()
            e.printStackTrace()
            null
        }
    }

    fun post(url: String, key: String, endpoint: String, json: String, onError: () -> Unit = {}): String? {
        return try {
            val body = json.toRequestBody(HEADER)

            val request = Request.Builder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer $key")
                .url("$url/$endpoint")
                .post(body)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    response.body?.string()?.let { println(it) }

                    throw IOException("Unexpected code $response")
                }

                response.body?.string() ?: throw IOException("Unexpected null response body")
            }
        } catch (e: IOException) {
            onError()
            e.printStackTrace()
            null
        }
    }
}