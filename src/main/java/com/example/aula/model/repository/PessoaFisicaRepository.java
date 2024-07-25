package com.example.aula.model.repository;

import com.example.aula.model.entity.PessoaFisica;
import com.example.aula.model.entity.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PessoaFisicaRepository {

    @PersistenceContext  //informa ao container do Spring a responsabilidade de gerenciar a dependência
    private EntityManager em;


    public void save(PessoaFisica pessoaFisica){
        em.persist(pessoaFisica);
    }

    public PessoaFisica pessoaFisica(Long id){
        return em.find(PessoaFisica.class, id);
    }

    public List<PessoaFisica> pessoaFisica(){
        Query query = em.createQuery("from PessoaFisica");
        return query.getResultList();
    }

    public List<PessoaFisica> buscarpessoaFisica(String nome){
        String nomeFormated = nome.toLowerCase();
        Query query = em.createQuery("from PessoaFisica where LOWER(nome) like :nome");
        query.setParameter("nome", "%"+nomeFormated+"%");
        return query.getResultList();
    }

    public PessoaFisica buscarPorCpf(String cpf) {
        Query query = em.createQuery("from PessoaFisica where cpf = :cpf");
        query.setParameter("cpf", cpf);
        return (PessoaFisica) query.getResultList().stream().findFirst().get();
    }


    public void remove(Long id){
        PessoaFisica pessoaFisica = em.find(PessoaFisica.class, id);
        em.remove(pessoaFisica);
    }

    public void update(PessoaFisica pessoaFisica){
        em.merge(pessoaFisica);
    }
}
