package dev.blackoutburst.gptest.openai.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class ChatRequest(
    val messages: List<Message>,
    val model: String
)
