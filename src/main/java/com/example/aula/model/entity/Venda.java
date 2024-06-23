package com.example.aula.model.entity;

import jakarta.persistence.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("session")
@Entity
public class Venda  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private java.lang.Long Id;
    private LocalDate dataHora;

    @OneToMany(mappedBy = "venda",cascade = CascadeType.ALL)
    private List<ItemVenda> itemVendas = new ArrayList<>();

    @ManyToOne
    private Pessoa pessoa;


    public List<ItemVenda> getItemVendas() {
        return itemVendas;
    }

    public void setItemVendas(List<ItemVenda> itemVendas) {
        this.itemVendas = itemVendas;
    }

    public double getTotalFinal() {
        Double total = 0.0;
        for(ItemVenda item : itemVendas){
            total += item.getTotal();
        }
        return total;
    }


    public java.lang.Long getId() {
        return Id;
    }

    public void setId(java.lang.Long id) {
        Id = id;
    }

    public LocalDate getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDate dataHora) {
        this.dataHora = dataHora;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }


}
