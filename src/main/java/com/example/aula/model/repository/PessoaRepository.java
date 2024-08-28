package com.example.aula.model.repository;

import com.example.aula.model.entity.Pessoa;
import com.example.aula.model.entity.PessoaFisica;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PessoaRepository {

    @PersistenceContext  //informa ao container do Spring a responsabilidade de gerenciar a dependência
    private EntityManager em;


    public void save(Pessoa pessoa){
        em.persist(pessoa);
    }

    public Pessoa pessoa(Long id){
        return em.find(Pessoa.class, id);
    }

    public List<Pessoa> pessoa(){
        Query query = em.createQuery("from Pessoa");
        return query.getResultList();
    }

    public List<Pessoa> buscarpessoa(String nome){
        String nomeFormated = nome.toLowerCase();
        Query query = em.createQuery("from Pessoa where LOWER(nome) like :nome");
        query.setParameter("nome", "%"+nomeFormated+"%");
        return query.getResultList();
    }

    public void remove(Long id){
        Pessoa pessoaFisica = em.find(Pessoa.class, id);
        em.remove(pessoaFisica);
    }

    public void update(Pessoa pessoa){
        em.merge(pessoa);
    }
}
