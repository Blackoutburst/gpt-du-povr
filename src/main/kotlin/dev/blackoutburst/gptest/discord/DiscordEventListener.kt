package dev.blackoutburst.gptest.discord

import dev.blackoutburst.gptest.openai.OpenAI
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class DiscordEventListener : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.member == null || event.member!!.user.isBot) return
        if (!event.message.contentRaw.startsWith("!")) return
        event.channel.sendTyping().queue()

        OpenAI.chat("[${event.member!!.effectiveName}] ${event.message.contentRaw}").let {
            event.channel.sendMessage(it).queue()
        }

    }
}