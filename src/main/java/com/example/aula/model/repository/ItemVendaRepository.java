package com.example.aula.model.repository;

import com.example.aula.model.entity.ItemVenda;
import com.example.aula.model.entity.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemVendaRepository {

    @PersistenceContext  //informa ao container do Spring a responsabilidade de gerenciar a dependência
    private EntityManager em;

    public void save(ItemVenda itemVenda){
        em.persist(itemVenda);
    }

    public ItemVenda item(Long id){
        return em.find(ItemVenda.class, id);
    }

    public List<ItemVenda> itens(){
        Query query = em.createQuery("from ItemVenda");
        return query.getResultList();
    }

    public void remove(Long id){
        ItemVenda itemVenda = em.find(ItemVenda.class, id);
        em.remove(itemVenda);
    }

    public void update(ItemVenda itemVenda){
        em.merge(itemVenda);
    }
}
