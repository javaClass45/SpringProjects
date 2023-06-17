package com.email.mailsender.service;

public interface EmailSenderService {
    void sendEmail(String to, String subject, String message);
}
