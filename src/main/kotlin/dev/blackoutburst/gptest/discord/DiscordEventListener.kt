package dev.blackoutburst.gptest.discord

import dev.blackoutburst.gptest.openai.OpenAI
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class DiscordEventListener : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.member == null || event.member!!.user.isBot) return

        event.channel.sendTyping().queue()
        val botMsg = OpenAI.chat("[${event.member!!.effectiveName}] ${event.message.contentRaw}")
        event.channel.sendMessage(botMsg).queue()
    }
}