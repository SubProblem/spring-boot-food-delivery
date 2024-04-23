package com.subproblem.orderservice.producer

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import kotlin.math.log

@Service
class Producer(
    private val rabbitTemplate: RabbitTemplate,
    @Value("\${spring.rabbitmq.exchange.name}")
    private val topicExchange: String,
    @Value("\${spring.rabbitmq.routing.key}")
    private val routingKey: String,
) {


    private val logger = LoggerFactory.getLogger(Producer::class.java)

    fun sendMessage(message: NotificationMessage) {
        logger.info("TopicExchange {}, RoutingKey {}", topicExchange, routingKey)
        logger.info("Notification  --> {}", message)
        rabbitTemplate.convertAndSend(topicExchange, routingKey, message)
    }
}