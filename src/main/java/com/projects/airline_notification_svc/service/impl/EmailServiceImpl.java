package com.projects.airline_notification_svc.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service dedicated to sending emails using Spring's JavaMailSender.
 * This component handles the construction and sending of simple text emails.
 * It is configured via application.yml.
 */
@Service
public class EmailServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    // Spring's abstraction for sending emails. It's automatically configured
    // by Spring Boot when 'spring-boot-starter-mail' is on the classpath.
    private final JavaMailSender mailSender;

    // The 'from' email address, injected from the application.yml file.
    // This ensures your sender email isn't hardcoded.
    private final String fromEmail;

    /**
     * Constructs the EmailService with required dependencies.
     * Using constructor injection is a best practice.
     *
     * @param mailSender The auto-configured JavaMailSender bean.
     * @param fromEmail  The sender's email address injected from properties.
     */
    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, @Value("${spring.mail.username}") String fromEmail) {
        this.mailSender = mailSender;
        this.fromEmail = fromEmail;
    }

    /**
     * Sends a simple text email to a single recipient.
     *
     * @param to      The recipient's email address.
     * @param subject The subject line of the email.
     * @param body    The plain text content (body) of the email.
     */
    public void sendEmail(String to, String subject, String body) {
        // We use a try-catch block to gracefully handle any exceptions that may occur
        // during the email sending process, such as authentication failures or network issues.
        try {
            // SimpleMailMessage is a helper class for creating a simple email.
            // For emails with attachments or HTML content, MimeMessageHelper would be used.
            SimpleMailMessage message = new SimpleMailMessage();

            // Set the sender's address.
            message.setFrom(fromEmail);

            // Set the recipient's address.
            message.setTo(to);

            // Set the email subject.
            message.setSubject(subject);

            // Set the email body content.
            message.setText(body);

            // Send the email using the configured mail sender.
            mailSender.send(message);

            LOGGER.info("Email sent successfully to {}", to);

        } catch (MailException e) {
            // Log any errors that occur. This is crucial for debugging production issues.
            LOGGER.error("Failed to send email to {}. Error: {}", to, e.getMessage());
        }
    }
}