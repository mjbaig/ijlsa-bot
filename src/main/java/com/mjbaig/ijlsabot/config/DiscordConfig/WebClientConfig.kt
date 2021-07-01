package com.mjbaig.ijlsabot.config.DiscordConfig

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
open class WebClientConfig {

    @Bean
    open fun discordTokenWebclient(): WebClient {
        return WebClient.create()
    }



}