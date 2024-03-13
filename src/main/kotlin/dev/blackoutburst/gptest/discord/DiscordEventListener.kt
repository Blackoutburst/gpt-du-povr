package dev.blackoutburst.gptest.discord

import dev.blackoutburst.gptest.mistral.Mistral
import dev.blackoutburst.gptest.openai.OpenAI
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class DiscordEventListener : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.member == null || event.member!!.user.isBot) return
        if (event.message.contentRaw.startsWith("!")) {
            event.channel.sendTyping().queue()

            OpenAI.chat(event.message.contentRaw.removePrefix("!").trim()).let {
                val chunks = it.chunked(1900)
                for (str in chunks)
                    event.channel.sendMessage(str).queue()
            }
        }

        if (event.message.contentRaw.startsWith("+")) {
            event.channel.sendTyping().queue()

            Mistral.chat(event.message.contentRaw.removePrefix("+").trim()).let {
                val chunks = it.chunked(1900)
                for (str in chunks)
                    event.channel.sendMessage(str).queue()
            }
        }

    }
}