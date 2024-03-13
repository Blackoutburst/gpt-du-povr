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
    OpenAI.addMessage(Message("system", "You are a friendly AI inside a group chat, your main goal is to chill and talk with users. Your name is \"TellementPT\". In the beginning of every message the name of the user currently speaking will be inside Brackets like so \"[User Name] Hey guys\""))

    JDABuilder.createLight(BuildConfig.DISCORD_TOKEN,
        GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
        .setCompression(Compression.NONE)
        .setAutoReconnect(true)
        .setStatus(OnlineStatus.ONLINE)
        .setActivity(Activity.competing("AAAAAAAAAAAAAA"))
        .addEventListeners(DiscordEventListener())
        .build()
}