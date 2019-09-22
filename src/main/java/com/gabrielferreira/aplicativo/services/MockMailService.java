package com.gabrielferreira.aplicativo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public class MockMailService extends AbstractEmailService {

    private static final Logger log = LoggerFactory.getLogger(MockMailService.class);


    @Override
    public void enviarEmail(SimpleMailMessage mensagem) {
        log.info("Teste de envio de email");
        log.info(mensagem.toString());
        log.info("Email enviado");
    }

    @Override
    public void enviarEmailHtml(MimeMessage mimeMessage) {
        log.info("Teste de envio de email");
        log.info(mimeMessage.toString());
        log.info("Email enviado");
    }
}
