package com.example.aula.model.repository;

import com.example.aula.model.entity.Produto;
import com.example.aula.model.entity.Venda;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class VendaRepository {

    @PersistenceContext  //responsabilidade de gerenciar a dependência
    private EntityManager em;

    public void save(Venda venda){
        em.persist(venda);
    }

    public Venda venda(java.lang.Long id){
        return em.find(Venda.class, id);
    }

    public List<Venda> vendas(){
        Query query = em.createQuery("from Venda");
        return query.getResultList();
    }


    public List<Venda> findByData(LocalDate data) {
        Query query = em.createQuery("from Venda v where v.dataHora = :data");
        query.setParameter("data", data);
        return query.getResultList();
    }

    public List<Venda> findByNome(String nome) {

        String nomeFormated = nome.toLowerCase();
        Query query = em.createQuery("from Venda v where LOWER(v.pessoa.nome) like :nome");
        query.setParameter("nome", "%"+nomeFormated+"%");
        return query.getResultList();
    }


    public List<Produto> obterProdutos() {
        // Exemplo de consulta JPQL para obter todos os itens de venda
        TypedQuery<Produto> query = em.createQuery("FROM Produto ", Produto.class);
        return query.getResultList();
    }

}
