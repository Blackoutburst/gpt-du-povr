package dev.blackoutburst.gptest

import dev.blackoutburst.gptest.mistral.Mistral
import dev.blackoutburst.gptest.openai.OpenAI
import dev.blackoutburst.gptest.stablediffusion.StableDiffusion
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.utils.FileUpload

object AiInterface {

    private fun sendText(event: MessageReceivedEvent, text: String) {
        val chunks = text.chunked(1900)
        for (str in chunks)
            event.channel.sendMessage(str).queue()
    }

    fun gpt(event: MessageReceivedEvent, text: String) = sendText(event, OpenAI.chat(text.trim()))

    fun mistral(event: MessageReceivedEvent, text: String) = sendText(event, Mistral.chat(text.trim()))

    fun stableDiffusion(event: MessageReceivedEvent) {
        StableDiffusion.generate(event.message.contentRaw.removePrefix("$").trim())?.let {
            event.channel.sendFiles(FileUpload.fromData(it)).queue()
        }
    }
}