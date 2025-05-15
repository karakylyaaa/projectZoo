/*
package com.example.projectzoo.Network;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendTemporaryPassword(String email, String temporaryPassword) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Ваш временный пароль для восстановления");
            message.setText("Ваш временный пароль: " + temporaryPassword);
            javaMailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }
}
*/
