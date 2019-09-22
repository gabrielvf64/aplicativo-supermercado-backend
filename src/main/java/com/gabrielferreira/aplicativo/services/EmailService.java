package com.gabrielferreira.aplicativo.services;

import com.gabrielferreira.aplicativo.dominio.Pedido;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public interface EmailService {

    void enviarEmailConfirmacaoPedido(Pedido pedido);

    void enviarEmail(SimpleMailMessage mensagem);

    void enviarEmailHtmlConfirmacaoPedido(Pedido pedido);

    void enviarEmailHtml(MimeMessage mimeMessage);
}
