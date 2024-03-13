package dev.blackoutburst.gptest.mistral

import dev.blackoutburst.gptest.BuildConfig
import dev.blackoutburst.gptest.http.Fetcher
import dev.blackoutburst.gptest.dataclass.ChatObject
import dev.blackoutburst.gptest.dataclass.ChatRequest
import dev.blackoutburst.gptest.dataclass.Message
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object Mistral {

    private val json = Json { ignoreUnknownKeys = true }
    private val messages = mutableListOf<Message>()

    fun addMessage(message: Message) {
        if (messages.size > 30) {
            val iterator = messages.iterator()
            while (iterator.hasNext()) {
                val msg = iterator.next()
                if (msg.role != "system") {
                    iterator.remove()
                    break
                }
            }
        }
        messages.add(message)
    }

    fun chat(
        message: String,
        role: String = "user",
        model: String = "open-mixtral-8x7b"
    ) : String {
        addMessage(Message(role, message))

        val body = json.encodeToString(ChatRequest(messages, model))
        val res = Fetcher.post("https://api.mistral.ai/v1", BuildConfig.MISTRAL_KEY, "chat/completions", body)
        return if (res != null) {
            val output = json.decodeFromString<ChatObject>(res)
            addMessage(Message("assistant", output.choices.first().message.content))
            output.choices.first().message.content
        } else "https://c.tenor.com/SXM68LEzlTYAAAAC/tenor.gif"
    }
}