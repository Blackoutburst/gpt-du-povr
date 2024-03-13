package dev.blackoutburst.gptest.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val role: String,
    val content: String
)