package dev.blackoutburst.gptest.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class ChatObject(
    val choices: List<Choice>
)

@Serializable
data class Choice(
    val message: Message,
    val finish_reason: String
)
