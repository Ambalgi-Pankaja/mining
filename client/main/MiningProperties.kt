package com.acomodeo.mining.client

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("aco.mining")
class MiningProperties {
    lateinit var url: String
}