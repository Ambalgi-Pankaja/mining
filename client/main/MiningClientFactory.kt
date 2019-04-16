package com.acomodeo.mining.client

import com.acomodeo.utils.clients.RestClientFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MiningClientFactory(
    private val miningProperties: MiningProperties,
    private val restClientFactory: RestClientFactory
) {

    @Bean
    @ConditionalOnProperty("aco.mining.url")
    fun miningClient(): MiningClient = restClientFactory {
        url(miningProperties.url)
        build()
    }
}