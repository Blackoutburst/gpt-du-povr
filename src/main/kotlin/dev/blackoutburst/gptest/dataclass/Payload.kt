package dev.blackoutburst.gptest.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class Payload(
    val prompt: String,
    val steps: Int
)