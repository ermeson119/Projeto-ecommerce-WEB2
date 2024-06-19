package com.example.aula.model.entity;

import jakarta.persistence.*;

import java.io.Serializable;


@Entity
public class ItemVenda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantidade;
    @ManyToOne
    private Venda venda;
    @ManyToOne
    private Produto produto;

    public ItemVenda(Long id, Integer quantidade, Produto produto, Venda venda) {
        this.id = id;
        this.quantidade = quantidade;
        this.produto = produto;
        this.venda = venda;
    }

    public ItemVenda() {}

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produtos) {
        this.produto = produtos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Double getTotal() {
        return produto.getValor().doubleValue() * this.getQuantidade();
    }
}
