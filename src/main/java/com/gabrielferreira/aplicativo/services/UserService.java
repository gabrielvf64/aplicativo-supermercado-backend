package com.gabrielferreira.aplicativo.services;

import com.gabrielferreira.aplicativo.seguranca.Usuario;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserService {

    public static Usuario authenticated() {
        try {
            return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}
