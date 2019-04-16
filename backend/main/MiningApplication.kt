package com.acomodeo.mining.backend

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class MiningApplication

fun main(args: Array<String>) {
SpringApplication.run(MiningApplication::class.java, *args)
}
