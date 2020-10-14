/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unosso.api.core.model;

/**
 *
 * @author fabio
 */
public enum StatusPedido {

    ORCAMENTO("Orçamento"),
    EMITIDA("Emitida"),
    CANCELADA("Cancelada");

    private String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
