package com.example.there4u.service.email;

import io.github.cdimascio.dotenv.Dotenv;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public String buildVerificationEmail(String receiver, String email, String link) {
        return ("Hello, %s\n\n" +
                "Welcome to there4u!\n\n" +
                "We received a registration with the following email: \"%s\"\n\n" +
                "Note that this is an automatic message regarding your security and you might need to reply/respond in a timely manner.\n" +
                "To proceed with the next steps, we require you to verify your email credentials by logging in through our secure portal: %s\n\n" +
                "If the URL above is not working/visible it is probably expired by now.\n\n" +
                "Best regards,\n" +
                "There4U Team").formatted(receiver, email, link);
    }

    public void sendMail(String to, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setFrom(System.getProperty("EMAIL"));
            message.setSubject("Email Verification");
            message.setText(content);
            log.info("Sending email to {}", to);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", to, e.getMessage(), e);
        }
    }
}
