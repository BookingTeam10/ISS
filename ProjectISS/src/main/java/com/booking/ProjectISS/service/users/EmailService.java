package com.booking.ProjectISS.service.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("matijap59@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        try {
            mailSender.send(message);
            System.out.println("Email je uspešno poslat.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Greška prilikom slanja emaila: " + e.getMessage());
        }
    }
}
