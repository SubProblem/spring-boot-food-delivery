package com.subproblem.notificationservice.consumer

import com.subproblem.notificationservice.service.MailService
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class Consumer(
    @Value("\${spring.rabbitmq.queue.name}")
    private val queueName: String,
    private val mailService: MailService
) {

    private val logger = LoggerFactory.getLogger(Consumer::class.java)

    @RabbitListener(queues = ["\${spring.rabbitmq.queue.name}"])
    fun sendEmail(message: NotificationMessage) {
        logger.info("Order message: {}", message)
        val subject = "Thank You for Your Order"
        val body = "Your order was made at ${message.dateOfOrder}"
        mailService.sendEmail(message.email, subject, body)
    }
}