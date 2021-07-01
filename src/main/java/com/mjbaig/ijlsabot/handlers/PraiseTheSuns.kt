package com.mjbaig.ijlsabot.handlers

import discord4j.common.util.Snowflake
import discord4j.core.`object`.reaction.ReactionEmoji
import discord4j.core.event.domain.message.MessageCreateEvent
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PraiseTheSuns : CommandHandler {

    private val logger = getLogger(PraiseTheSuns::class.java)

    private fun praiseTheSun(event: MessageCreateEvent): Mono<Void> {

        logger.info(event.message.content)

        return event.message.addReaction(ReactionEmoji.custom(Snowflake.of(646521277640605696), "praisethesun", false)).then()

    }

    private fun sunsWin(event: MessageCreateEvent): Mono<Void> {
        return event.message.channel.flatMap {
            it.createMessage("https://www.youtube.com/watch?v=bzJDimvPW1Y")
        }.then()
    }

    override fun getCommandMap(): Map<String, (MessageCreateEvent) -> Mono<Void>> {

        return mapOf("DevinBooker" to this::praiseTheSun,
                "CP3" to this::praiseTheSun,
                "Suns" to this::praiseTheSun,
                "SunsWin" to this::sunsWin)
    }
}