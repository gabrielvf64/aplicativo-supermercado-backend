package com.gabrielferreira.aplicativo.dominio.enums;

public enum Perfil {
    ADMIN(1, "ROLE_ADMIN"),
    CLIENTE(2, "ROLE_CLIENTE");

    private int codigo;
    private String descricao;

    private Perfil(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Perfil converterParaEnum(Integer codigo) {

        if (codigo == null) {
            return null;
        }

        for (Perfil i : Perfil.values()) {
            if (codigo.equals(i.getCodigo())) {
                return i;
            }
        }
        throw new IllegalArgumentException("Id inválido: " + codigo);
    }
}
