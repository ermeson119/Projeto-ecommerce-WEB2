package com.example.aula.model.repository;

import com.example.aula.model.entity.PessoaJuridica;
import com.example.aula.model.entity.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ProdutoRepository {

    @PersistenceContext  //informa ao container do Spring a responsabilidade de gerenciar a dependência
    private EntityManager em;

    public void save(Produto produto){
        em.persist(produto);
    }

    public Produto produto(Long id){
        return em.find(Produto.class, id);
    }

    public List<Produto> produtos(){
        Query query = em.createQuery("from Produto");
        return query.getResultList();
    }

    public void remove(Long id){
        Produto produto = em.find(Produto.class, id);
        em.remove(produto);
    }

    public List<Produto> buscarProduto(String descricao){
        String descricaoFormated = descricao.toLowerCase();
        Query query = em.createQuery("from Produto where LOWER(descricao) like :descricao");
        query.setParameter("descricao", "%"+descricaoFormated+"%");
        return query.getResultList();
    }


    public void removeSession(Long id){
        Produto produto = em.find(Produto.class, id);
        em.remove(produto);
    }

    public void update(Produto produto){
        em.merge(produto);
    }
}
