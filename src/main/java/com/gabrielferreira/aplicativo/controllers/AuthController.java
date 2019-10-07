package com.gabrielferreira.aplicativo.controllers;

import com.gabrielferreira.aplicativo.dto.EmailDTO;
import com.gabrielferreira.aplicativo.seguranca.JWTUtil;
import com.gabrielferreira.aplicativo.seguranca.Usuario;
import com.gabrielferreira.aplicativo.services.AuthService;
import com.gabrielferreira.aplicativo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        Usuario usuario = UserService.authenticated();
        String token = jwtUtil.generateToken(usuario.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public ResponseEntity<Void> enviarNovaSenha(@Valid @RequestBody EmailDTO emailDTO) {
        authService.enviarNovaSenha(emailDTO.getEmail());
        return ResponseEntity.noContent().build();
    }
}
