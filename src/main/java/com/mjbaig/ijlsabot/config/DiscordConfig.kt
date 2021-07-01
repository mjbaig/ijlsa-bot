package com.mjbaig.ijlsabot.config

import com.mjbaig.ijlsabot.exception.NullConfigurationException
import discord4j.core.DiscordClientBuilder
import discord4j.core.GatewayDiscordClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@PropertySource("classpath:secrets.properties")
open class DiscordConfig {

    @Bean
    open fun discordClient(environment: Environment, basicWebClient: WebClient): GatewayDiscordClient {

        val token = environment.getProperty("token") ?: throw NullConfigurationException("token is null")

        return DiscordClientBuilder.create(token).build().login().block()
                ?: throw NullConfigurationException("discord client is null")
    }

}
