/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unosso.api.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 *
 * @author fabio
 */
@Entity
@Table(name = "pedido")
@SuppressWarnings({"ConsistentAccessType", "IdDefinedInHierarchy"})
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_hora_entrega")
    private LocalDateTime dataHoraEntrega;

    @Column(name = "valor_frete")
    private BigDecimal valorFrete;

    @Column(name = "valor_desconto")
    private BigDecimal valorDesconto;

    @NotNull(message = "O valor total é obrigatório")
    @Column(name = "valor_total")
    private BigDecimal valorTotal = BigDecimal.ZERO;

    @JsonIgnoreProperties({"produto"})
    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
    private List<PedidoItem> pedidoItems = new ArrayList<>();

    @JsonIgnoreProperties({"enderecos", "usuario"})
    @ManyToOne
    @JoinColumn(name = "cliente_id", foreignKey = @ForeignKey(name = "fk_pedido_cliente"))
    private Cliente cliente;

    @JsonIgnoreProperties({"enderecos", "usuario"})
    @ManyToOne
    @JoinColumn(name = "loja_id", foreignKey = @ForeignKey(name = "fk_pedido_loja"))
    private Loja loja;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pedido")
    private StatusPedido statusPedido = StatusPedido.ORCAMENTO;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento")
    private FormaPagamento formaPagamento= FormaPagamento.BOLETO_BANCARIO;

    @Transient
    private LocalDate dataEntrega;

    @Transient
    private LocalTime horarioEntrega;

    @Transient
    public boolean isNovo() {
        return getId() == null;
    }

    @Transient
    public boolean isExistente() {
        return !isNovo();
    }

    @Transient
    public boolean isVazio() {
        return this.getPedidoItems().isEmpty();
    }

    @Transient
    public boolean isEmitido() {
        return StatusPedido.EMITIDA.equals(this.getStatusPedido());
    }

    public void adicionarItens(List<PedidoItem> itens) {
        this.pedidoItems = itens;
        this.pedidoItems.forEach(i -> i.setPedido(this));
    }

    public BigDecimal getValorTotalItens() {
        return getPedidoItems().stream()
                .map(PedidoItem::getValorTotal)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public void calcularValorTotal() {
        this.valorTotal = calcularValorTotal(getValorTotalItens(), getValorFrete(), getValorDesconto());
    }

    public Long getDiasCriacao() {
        LocalDate inicio = dataCriacao != null ? dataCriacao.toLocalDate() : LocalDate.now();
        return ChronoUnit.DAYS.between(inicio, LocalDate.now());
    }

    public boolean isSalvarPermitido() {
        return !statusPedido.equals(StatusPedido.CANCELADA);
    }

    public boolean isSalvarProibido() {
        return !isSalvarPermitido();
    }

    private BigDecimal calcularValorTotal(BigDecimal valorTotalItens, BigDecimal valorFrete, BigDecimal valorDesconto) {
        BigDecimal total = valorTotalItens
                .add(Optional.ofNullable(valorFrete).orElse(BigDecimal.ZERO))
                .subtract(Optional.ofNullable(valorDesconto).orElse(BigDecimal.ZERO));
        return total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataHoraEntrega() {
        return dataHoraEntrega;
    }

    public void setDataHoraEntrega(LocalDateTime dataHoraEntrega) {
        this.dataHoraEntrega = dataHoraEntrega;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public LocalTime getHorarioEntrega() {
        return horarioEntrega;
    }

    public void setHorarioEntrega(LocalTime horarioEntrega) {
        this.horarioEntrega = horarioEntrega;
    }

    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<PedidoItem> getPedidoItems() {
        return pedidoItems;
    }

    public void setPedidoItems(List<PedidoItem> pedidoItems) {
        this.pedidoItems = pedidoItems;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatus(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Loja getLoja() {
        return loja;
    }

    public void setLoja(Loja loja) {
        this.loja = loja;
    }

}
