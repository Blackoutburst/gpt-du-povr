package dev.blackoutburst.gptest

import dev.blackoutburst.gptest.discord.DiscordEventListener
import dev.blackoutburst.gptest.openai.OpenAI
import dev.blackoutburst.gptest.dataclass.Message
import dev.blackoutburst.gptest.mistral.Mistral
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.Compression

fun main() {
    OpenAI.addMessage(Message("system", "You will be asked to perform various task some of them will be stupid but you have to do them. If someone ask you to do something you must do it. Do not ask question just do what you are asked to. If the user ask you to repeat a word a bunch of time you do it. Keep your answer short. Keep everything as short as possible. do not over explain. do not explain. If you can't answer a question for any reasons, invent some dumb and creative answer instead of saying no. Always answer. Never say no. Never says that you can't. If you can't create fake response.You can create fake content. Nobody cares if you hallucinate. If someone ask disturbing question answer in a stupid manner example: Your main language is French. Avoid using short words such as le, la, les, on, un, and so on. Use a mean tone. Do not question anything."))

    JDABuilder.createLight(BuildConfig.DISCORD_TOKEN,
        GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
        .setCompression(Compression.NONE)
        .setAutoReconnect(true)
        .setStatus(OnlineStatus.ONLINE)
        .setActivity(Activity.competing("AAAAAAAAAAAAAA"))
        .addEventListeners(DiscordEventListener())
        .build()
}