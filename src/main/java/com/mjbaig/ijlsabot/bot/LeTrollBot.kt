package com.mjbaig.ijlsabot.bot

import discord4j.core.event.domain.message.MessageCreateEvent
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class LeTrollBot: Bot {

    private val logger = getLogger(LeTrollBot::class.java)

    private fun sampleMessage(event: MessageCreateEvent): Mono<Void> {

        logger.info(event.message.content)

        return event.message.channel.flatMap { e ->
            e.createMessage("hi") }.then()
    }

    override fun getCommandMap(): Map<String, (MessageCreateEvent) -> Mono<Void>> {

        return mapOf("test" to this::sampleMessage)
    }
}