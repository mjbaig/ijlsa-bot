package com.mjbaig.ijlsabot.bot

import discord4j.core.event.domain.message.MessageCreateEvent
import reactor.core.publisher.Mono

interface Bot {
    fun getCommandMap(): Map<String, (MessageCreateEvent) -> Mono<Void>>
}