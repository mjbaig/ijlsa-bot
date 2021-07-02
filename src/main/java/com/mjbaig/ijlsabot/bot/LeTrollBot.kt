package com.mjbaig.ijlsabot.bot

import com.mjbaig.ijlsabot.handlers.CommandHandler
import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.message.MessageCreateEvent
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
open class LeTrollBot(discordClient: GatewayDiscordClient, commandHandlers: List<CommandHandler>) {

    private val logger = getLogger(LeTrollBot::class.java)

    init {

        val commands: Map<String, (event: MessageCreateEvent) -> Mono<Void>> = commandHandlers.foldRight(mapOf()) { commandHandler, acc ->
            acc + commandHandler.getCommandMap()
        }

        discordClient
                .on(MessageCreateEvent::class.java)
                .flatMap { event ->
                    Mono.justOrEmpty(event.message.content)
                            .flatMap { content: String ->
                                logger.info(content)
                                Flux.fromIterable(commands.entries)
                                        .filter { entry ->
                                            content.toLowerCase().startsWith("!${entry.key.toLowerCase()}")
                                        }.flatMap { entry -> entry.value(event) }
                                        .next()
                            }
                }.subscribe(null, {e -> logger.error(e.message, e)})

    }

}