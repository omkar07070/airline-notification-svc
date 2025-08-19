package com.projects.airline_notification_svc.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class EmailServiceImpl {



    private final JavaMailSender mailSender;

    // The 'from' email address, injected from the application.yml file.
    // This ensures your sender email isn't hardcoded.
    private final String fromEmail;


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

            log.info("Email sent successfully to {}", to);

        } catch (MailException e) {
            // Log any errors that occur. This is crucial for debugging production issues.
            LOGGER.error("Failed to send email to {}. Error: {}", to, e.getMessage());
        }
    }
}