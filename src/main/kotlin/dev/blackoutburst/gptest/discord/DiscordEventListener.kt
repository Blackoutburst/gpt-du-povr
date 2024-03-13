package dev.blackoutburst.gptest.discord

import dev.blackoutburst.gptest.AiInterface
import dev.blackoutburst.gptest.mistral.Mistral
import dev.blackoutburst.gptest.openai.OpenAI
import dev.blackoutburst.gptest.stablediffusion.StableDiffusion
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class DiscordEventListener : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.member == null || event.member!!.user.isBot) return
        val prefix = event.message.contentRaw[0]

        when (prefix) {
            '!' -> {
                event.channel.sendTyping().queue()
                AiInterface.gpt(event, event.message.contentRaw.removePrefix("!"))
            }
            '+' -> {
                event.channel.sendTyping().queue()
                AiInterface.mistral(event, event.message.contentRaw.removePrefix("+"))
            }
            '$' -> {
                event.channel.sendTyping().queue()
                AiInterface.stableDiffusion(event)
            }
        }

    }
}