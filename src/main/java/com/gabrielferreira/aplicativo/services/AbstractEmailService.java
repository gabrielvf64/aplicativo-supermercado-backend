package com.gabrielferreira.aplicativo.services;

import com.gabrielferreira.aplicativo.dominio.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public abstract class AbstractEmailService implements EmailService {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${default.sender}")
    private String remetente;

    @Override
    public void enviarEmailConfirmacaoPedido(Pedido pedido) {
        SimpleMailMessage mensagem = criarEmailConfirmacaoPedido(pedido);
        enviarEmail(mensagem);
    }

    protected SimpleMailMessage criarEmailConfirmacaoPedido(Pedido pedido) {
        SimpleMailMessage mensagem = new SimpleMailMessage();

        mensagem.setTo(pedido.getCliente().getEmail());
        mensagem.setFrom(remetente);
        mensagem.setSubject("Pedido confirmado. Número do Pedido: " + pedido.getId());
        mensagem.setSentDate(new Date(System.currentTimeMillis()));
        mensagem.setText(pedido.toString());

        return mensagem;
    }

    protected String criarTemplateHtmlConfirmacaoPedido(Pedido pedido) {
        Context context = new Context();
        context.setVariable("pedido", pedido);

        return templateEngine.process("email/confirmacaoPedido", context);
    }

    @Override
    public void enviarEmailHtmlConfirmacaoPedido(Pedido pedido) {
        try {
            MimeMessage mimeMessage = criarEmailHtmlConfirmacaoPedido(pedido);
            enviarEmailHtml(mimeMessage);
        } catch (MessagingException e) {
            enviarEmailConfirmacaoPedido(pedido);
        }
    }

    protected MimeMessage criarEmailHtmlConfirmacaoPedido(Pedido pedido) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setTo(pedido.getCliente().getEmail());
        mimeMessageHelper.setFrom(remetente);
        mimeMessageHelper.setSubject("Confirmação de pedido");
        mimeMessageHelper.setSentDate(new Date(System.currentTimeMillis()));
        mimeMessageHelper.setText(criarTemplateHtmlConfirmacaoPedido(pedido), true);

        return mimeMessage;
    }
}
