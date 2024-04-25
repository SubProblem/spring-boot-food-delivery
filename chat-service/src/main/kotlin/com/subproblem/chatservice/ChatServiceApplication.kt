package com.subproblem.chatservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class ChatServiceApplication

fun main(args: Array<String>) {
	runApplication<ChatServiceApplication>(*args)
}
