package com.mjbaig.ijlsabot.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient

@Configuration
open class WebClientConfig {

    @Bean
    open fun discordTokenWebclient(): WebClient {
        return WebClient
                .builder()
                .exchangeStrategies(
                        ExchangeStrategies
                                .builder()
                                .codecs{
                                    it.defaultCodecs().maxInMemorySize(16*1024*1024)}.build()).build()
    }



}