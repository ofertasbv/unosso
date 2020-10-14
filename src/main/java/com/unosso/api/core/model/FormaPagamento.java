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
public enum FormaPagamento {
    PAYPAL("PAYPAL"),
    PAGUE_SEGURO("PAGUE_SEGURO"),
    CARTAO_CREDITO("CARTAO_CREDITO"),
    BOLETO_BANCARIO("BOLETO_BANCARIO"),
    TRANSFERENCIA_BANCARIA("TRANSFERENCIA_BANCARIA");

    private String descricao;

    FormaPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
