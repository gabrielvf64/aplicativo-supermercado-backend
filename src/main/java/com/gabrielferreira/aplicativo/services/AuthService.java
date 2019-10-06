package com.gabrielferreira.aplicativo.services;

import com.gabrielferreira.aplicativo.dominio.Cliente;
import com.gabrielferreira.aplicativo.repositories.ClienteRepository;
import com.gabrielferreira.aplicativo.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BCryptPasswordEncoder senhaEncodada;

    @Autowired
    private EmailService emailService;

    private Random aleatorio = new Random();

    public void enviarNovaSenha(String email) {
        Cliente cliente = clienteRepository.findByEmail(email);

        if (cliente == null) {
            throw new ObjectNotFoundException("Email não encontrado");
        }

        String novaSenha = gerarNovaSenha();
        cliente.setSenha(senhaEncodada.encode(novaSenha));

        clienteRepository.save(cliente);
        emailService.enviarEmailNovaSenha(cliente, novaSenha);
    }

    private String gerarNovaSenha() {
        char[] vetor = new char[10];

        for (int i = 0; i < 10; i++) {
            vetor[i] = gerarCaracter();
        }

        return new String(vetor);
    }

    private char gerarCaracter() {
        int opicional = aleatorio.nextInt(3);

        if (opicional == 0) {
            return (char) (aleatorio.nextInt(10) + 48);
        } else if (opicional == 1) {
            return (char) (aleatorio.nextInt(26) + 65);
        } else {
            return (char) (aleatorio.nextInt(26) + 97);
        }
    }
}
