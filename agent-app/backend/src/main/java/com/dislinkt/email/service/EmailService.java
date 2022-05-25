package com.dislinkt.email.service;

import com.dislinkt.email.model.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String username;
    private final JavaMailSender emailSender;

    public void sendEmail(Email email) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(email.getRecipient());
        message.setSubject(email.getSubject());
        message.setText(email.getContent());

        emailSender.send(message);
    }
}
