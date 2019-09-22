package com.gabrielferreira.aplicativo.services;

import com.gabrielferreira.aplicativo.dominio.Pedido;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void enviarEmailConfirmacaoPedido(Pedido pedido);

    void enviarEmail(SimpleMailMessage mensagem);
}
