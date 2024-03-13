package dev.blackoutburst.gptest

import dev.blackoutburst.gptest.discord.DiscordEventListener
import dev.blackoutburst.gptest.openai.OpenAI
import dev.blackoutburst.gptest.openai.dataclass.Message
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.Compression

fun main() {
    OpenAI.addMessage(Message("system", "Your main langage is French you must speak in French unless the user start speaking another langage.Your name is \"TellementPT\". Multiples users may talk in the conversation so in the beginning of every message the name of the user currently speaking will be inside Brackets like so \"[UserName]\". Do not add your name in your messages only users have them so you can know who is talking. Do not add the name of the user you are answering in your messages. Your will mainly be asked to help users about coding issues. Keep your answer concise and short. Do not over explain everything."))

    JDABuilder.createLight(BuildConfig.DISCORD_TOKEN,
        GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
        .setCompression(Compression.NONE)
        .setAutoReconnect(true)
        .setStatus(OnlineStatus.ONLINE)
        .setActivity(Activity.competing("AAAAAAAAAAAAAA"))
        .addEventListeners(DiscordEventListener())
        .build()
}