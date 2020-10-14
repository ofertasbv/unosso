package com.unosso.api.core.model;

public enum Tamanho {

    PEQUENO("PEQUENO"),
    MEDIO("MEDIO"),
    GRANDE("GRANDE"),
    OUTRO("OUTRO");

    private String descricao;

    Tamanho(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
