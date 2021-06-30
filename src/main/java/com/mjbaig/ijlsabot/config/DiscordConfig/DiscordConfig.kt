package com.mjbaig.ijlsabot.config.DiscordConfig

import com.mjbaig.ijlsabot.exception.NullConfigurationException
import discord4j.core.DiscordClientBuilder
import discord4j.core.GatewayDiscordClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment

@Configuration
@PropertySource("classpath:secrets.properties")
open class DiscordConfig {


    @Bean
    open fun discordClient(@Autowired environment: Environment): GatewayDiscordClient {

        val clientId = environment.getProperty("clientId") ?: throw NullConfigurationException("client id is null")

        val client = DiscordClientBuilder.create(clientId).build().login().block()
                ?: throw NullConfigurationException("Discord Client was null")

        client.onDisconnect().block()

        return client
    }


}
