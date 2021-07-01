package com.mjbaig.ijlsabot.bot

import com.mjbaig.ijlsabot.handlers.PraiseTheSuns
import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.message.MessageCreateEvent
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
open class LeTrollBot(discordClient: GatewayDiscordClient, praiseTheSuns: PraiseTheSuns) {

    private val logger = getLogger(LeTrollBot::class.java)

    private val commands = mutableMapOf<String, (event: MessageCreateEvent) -> Mono<Void>>()

    init {

        commands.putAll(praiseTheSuns.getCommandMap())

        discordClient
                .on(MessageCreateEvent::class.java)
                .flatMap { event ->
                    Mono.justOrEmpty(event.message.content)
                            .flatMap { content: String ->
                                Flux.fromIterable(commands.entries)
                                        .filter { entry ->
                                            content.toLowerCase().startsWith("!${entry.key.toLowerCase()}")
                                        }.flatMap { entry -> entry.value(event) }
                                        .next()
                            }
                }.subscribe()
    }

}