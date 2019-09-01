package com.gabrielferreira.aplicativo.dominio.enums;

public enum EstadoPagamento {
    PENDENTE(1, "Pendente"),
    QUITADO(2, "Quitado"),
    CANCELADO(3, "Cancelado");

    private int codigo;
    private String descricao;

    private EstadoPagamento(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static EstadoPagamento converterParaEnum(Integer codigo) {

        if (codigo == null) {
            return null;
        }

        for (EstadoPagamento i : EstadoPagamento.values()) {
            if (codigo.equals(i.getCodigo())) {
                return i;
            }
        }
        throw new IllegalArgumentException("Id inválido: " + codigo);
    }
}
