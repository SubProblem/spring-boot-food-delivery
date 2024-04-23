package com.subproblem.notificationservice.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class MailService(
    private val mailSender: JavaMailSender,
    @Value("\${spring.mail.username}")
    private val senderEmail: String,
) {

    private val logger = LoggerFactory.getLogger(MailService::class.java)

    fun sendEmail(email: String, subject: String, body: String) {
        val message = SimpleMailMessage().apply {
            this.from = senderEmail
            this.subject = subject
            this.text = body
            this to email
        }

        // Configure your email and password in application.yaml to send mail
        // mailSender.send(message)
        logger.info("email: {}", message)
    }
}
