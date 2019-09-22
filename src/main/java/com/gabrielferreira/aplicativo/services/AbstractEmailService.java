package com.gabrielferreira.aplicativo.services;

import com.gabrielferreira.aplicativo.dominio.Pedido;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

public abstract class AbstractEmailService implements EmailService {

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
}
