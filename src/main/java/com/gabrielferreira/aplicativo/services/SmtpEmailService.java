package com.gabrielferreira.aplicativo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends AbstractEmailService {

    @Autowired
    private MailSender mailSender;

    private static final Logger log = LoggerFactory.getLogger(SmtpEmailService.class);

    @Override
    public void enviarEmail(SimpleMailMessage mensagem) {
        log.info("Enviando email...");
        mailSender.send(mensagem);
        log.info("Email enviado");
    }
}
