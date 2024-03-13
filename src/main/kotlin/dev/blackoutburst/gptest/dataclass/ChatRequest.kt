package dev.blackoutburst.gptest.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class ChatRequest(
    val messages: List<Message>,
    val model: String
)
