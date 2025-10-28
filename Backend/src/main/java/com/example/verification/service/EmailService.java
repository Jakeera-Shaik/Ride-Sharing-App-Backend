package com.example.verification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String to, String code) {
        try {
            // ✅ Defensive validation
            if (to == null || to.isBlank()) {
                throw new IllegalArgumentException("Recipient email address is missing or invalid.");
            }

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to.trim());
            message.setSubject("Your Verification Code");
            message.setText("Dear user,\n\nYour verification code is: " + code + 
                            "\n\nPlease enter this code to verify your account.\n\nThank you!");

            mailSender.send(message);

            System.out.println("✅ Verification email sent successfully to: " + to);
        } catch (Exception e) {
            System.err.println("❌ Failed to send email: " + e.getMessage());
            throw new RuntimeException("Error sending email: " + e.getMessage());
        }
    }
}

