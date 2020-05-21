package com.yaf.florabasket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 *
 * @date 21.05.2020
 */
@Component
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String covere, String mailAddress) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(mailAddress);
        mail.setFrom(mailAddress);
        mail.setSubject("Discount");
        mail.setText(covere);
        javaMailSender.send(mail);
    }

}
