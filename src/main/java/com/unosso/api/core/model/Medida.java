package com.unosso.api.core.model;

public enum Medida {

    UNIDADE("UNIDADE"),
    PECA("PECA"),
    QUILOGRAMA("QUILOGRAMA"),
    OUTRO("OUTRO");

    private String descricao;

    Medida(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
