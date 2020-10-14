package com.unosso.api.core.model;

public enum Origem {

    NACIONAL("NACIONAL"),
    INTERNACIONAL("INTERNACIONAL");

    private String descricao;

    Origem(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
