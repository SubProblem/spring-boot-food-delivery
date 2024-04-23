package com.subproblem.notificationservice.consumer


import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RabbitMQConfig(
    @Value("\${spring.rabbitmq.queue.name}")
    private val queueName: String,
    @Value("\${spring.rabbitmq.exchange.name}")
    private val topicExchange: String,
    @Value("\${spring.rabbitmq.routing.key}")
    private val routingName: String
) {

    @Bean
    fun queue(): Queue =
        Queue(queueName, true)

    @Bean
    fun topicExchange(): TopicExchange =
        TopicExchange(topicExchange)

    @Bean
    fun binding(queue: Queue, topicExchange: TopicExchange): Binding =
        BindingBuilder
            .bind(queue())
            .to(topicExchange())
            .with(routingName)

    @Bean
    fun template(connectionFactory: ConnectionFactory): AmqpTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = jsonMessageConverter()
        return rabbitTemplate
    }

    @Bean
    fun jsonMessageConverter(): MessageConverter =
        Jackson2JsonMessageConverter()

}