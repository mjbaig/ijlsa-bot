package com.mjbaig.ijlsabot.handlers

import com.mjbaig.ijlsabot.server.NBAStatsService
import com.mjbaig.ijlsabot.server.Player
import discord4j.core.event.domain.message.MessageCreateEvent
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PlayerStatsHandler(private val nbaStatsService: NBAStatsService) : CommandHandler {

    private val logger = getLogger(PlayerStatsHandler::class.java)

    private fun getPlayerProfile(event: MessageCreateEvent): Mono<Void> {


        val player: Mono<Player> = Mono.justOrEmpty(event.message.content).flatMap { content ->

            Mono.justOrEmpty(content.split(" ")).flatMap {
                nbaStatsService.getPlayerByName(it[1], it[2])
            }

        }.onErrorResume { e -> Mono.error(e) }

        return player.flatMap { p ->
            event.message.channel.flatMap { messageChannel ->
                messageChannel.createMessage(p.toString())
            }.then()
        }.then()

    }

    override fun getCommandMap(): Map<String, (MessageCreateEvent) -> Mono<Void>> {
        return mapOf("PlayerProfile" to this::getPlayerProfile)
    }
}