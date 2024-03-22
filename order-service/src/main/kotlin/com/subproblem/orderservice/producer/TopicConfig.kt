package com.subproblem.orderservice.producer

import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Configuration

@Configuration
class TopicConfig {

    fun queue(): Queue =
        Queue("orderQueue")
}