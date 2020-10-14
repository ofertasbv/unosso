package com.unosso.api.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "produto", schema = "oferta")
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

//    @SKU
    @NotBlank
    private String sku;

    @NotNull(message = "O nome é obrigatório")
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull(message = "A descrição é obrigatória")
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull(message = "A foto em de capa é obrigatória")
    @Column(name = "foto", nullable = false)
    private String foto;

    @Column(name = "favorito", nullable = true)
    private boolean favorito;

    @NotNull(message = "A data de registro é obrigatória")
    @Column(name = "data_registro", nullable = false)
    private LocalDate dataRegistro;

    @Column(name = "codigo_barra", nullable = false)
    private String codigoBarra;

    @Column(name = "status", nullable = true)
    private boolean status;

    @Column(name = "novo", nullable = true)
    private boolean novo;

    @Column(name = "destaque", nullable = true)
    private boolean destaque;

    @NotNull(message = "A medida é obrigatória")
    @Enumerated(EnumType.STRING)
    private Medida medida = Medida.OUTRO;

    @NotNull(message = "A origem é obrigatória")
    @Enumerated(EnumType.STRING)
    private Origem origem;

    @NotNull(message = "O tamanho é obrigatório")
    @Enumerated(EnumType.STRING)
    private Tamanho tamanho;

    @Column(name = "cor", nullable = true)
    private String cor;

    @NotNull(message = "O desconto é obrigatório")
    @DecimalMax(value = "100.0", message = "O valor do desconto deve ser menor que 100")
    @Column(name = "desconto")
    private BigDecimal desconto;

    @JsonIgnoreProperties({"categoria", "produtos"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subcategoria_id", columnDefinition = "Integer", foreignKey = @ForeignKey(name = "fk_produto_subcategoria"), nullable = false)
    private SubCategoria subCategoria;

    @JsonIgnoreProperties({"produtos", "loja"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promocao_id", foreignKey = @ForeignKey(name = "fk_produto_promocao"), nullable = false)
    private Promocao promocao;

    @JsonIgnoreProperties({"promocaos", "endereco", "produtos", "usuario"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loja_id", columnDefinition = "Integer", foreignKey = @ForeignKey(name = "fk_produto_loja"), nullable = false)
    private Loja loja;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "produto_arquivo",
            joinColumns = @JoinColumn(name = "produto_id"),
            inverseJoinColumns = @JoinColumn(name = "arquivo_id"),
            foreignKey = @ForeignKey(name = "fk_produto_id"),
            inverseForeignKey = @ForeignKey(name = "fk_arquivo_id"))
    private List<Arquivo> arquivos = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.ALL, CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "estoque_id", foreignKey = @ForeignKey(name = "fk_produto_estoque"))
    private Estoque estoque = new Estoque();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "marca_id", foreignKey = @ForeignKey(name = "fk_produto_marca"), nullable = false)
    private Marca marca;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public Medida getMedida() {
        return medida;
    }

    public void setMedida(Medida medida) {
        this.medida = medida;
    }

    public SubCategoria getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(SubCategoria subCategoria) {
        this.subCategoria = subCategoria;
    }

    public Loja getLoja() {
        return loja;
    }

    public void setLoja(Loja loja) {
        this.loja = loja;
    }

    public List<Arquivo> getArquivos() {
        return arquivos;
    }

    public void setArquivos(List<Arquivo> arquivos) {
        this.arquivos = arquivos;
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public boolean isDestaque() {
        return destaque;
    }

    public void setDestaque(boolean destaque) {
        this.destaque = destaque;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public boolean isNovo() {
        return novo;
    }

    public void setNovo(boolean novo) {
        this.novo = novo;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public Origem getOrigem() {
        return origem;
    }

    public void setOrigem(Origem origem) {
        this.origem = origem;
    }

    public Tamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Produto other = (Produto) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    public Promocao getPromocao() {
        return promocao;
    }

    public void setPromocao(Promocao promocao) {
        this.promocao = promocao;
    }

}
