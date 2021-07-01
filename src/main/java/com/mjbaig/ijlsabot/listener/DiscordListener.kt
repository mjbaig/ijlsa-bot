package com.mjbaig.ijlsabot.listener

import com.mjbaig.ijlsabot.bot.LeTrollBot
import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.message.MessageCreateEvent
import org.slf4j.LoggerFactory.getLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
open class DiscordListener(discordClient: GatewayDiscordClient, leTrollBot: LeTrollBot) {

    private val logger = getLogger(DiscordListener::class.java)

    private val commands = mutableMapOf<String, (event: MessageCreateEvent) -> Mono<Void>>()

    init {

        commands.putAll(leTrollBot.getCommandMap())

        discordClient
                .on(MessageCreateEvent::class.java)
                .flatMap { event ->
                    Mono.justOrEmpty(event.message.content)
                            .flatMap { content: String ->
                                Flux.fromIterable(commands.entries)
                                        .filter { entry -> content.startsWith("!${entry.key}") }
                                        .flatMap { entry -> entry.value(event) }
                                        .next()
                            }
                }.subscribe()
    }

}