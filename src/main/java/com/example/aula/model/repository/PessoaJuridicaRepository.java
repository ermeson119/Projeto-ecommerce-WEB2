package com.example.aula.model.repository;

import com.example.aula.model.entity.Pessoa;
import com.example.aula.model.entity.PessoaFisica;
import com.example.aula.model.entity.PessoaJuridica;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PessoaJuridicaRepository {

    @PersistenceContext  //informa ao container do Spring a responsabilidade de gerenciar a dependência
    private EntityManager em;

    public void save(PessoaJuridica pessoaJuridica){
        em.persist(pessoaJuridica);
    }

    public PessoaJuridica pessoaJuridica(Long id){
        return em.find(PessoaJuridica.class, id);
    }

    public List<PessoaJuridica> pessoaJuridica(){
        Query query = em.createQuery("from PessoaJuridica");
        return query.getResultList();
    }

    public List<PessoaJuridica> buscarpessoaJuridica(String nome){
        String nomeFormated = nome.toLowerCase();
        Query query = em.createQuery("from PessoaJuridica where LOWER(nome) like :nome");
        query.setParameter("nome", "%"+nomeFormated+"%");
        return query.getResultList();
    }

    public PessoaJuridica buscarPorCnpj(String cnpj) {
        Query query = em.createQuery("from PessoaJuridica where cnpj = :cnpj");
        query.setParameter("cnpj", cnpj);
        return (PessoaJuridica) query.getResultList().stream().findFirst().get();
    }


    public void remove(Long id){
        PessoaJuridica pessoaJuridica = em.find(PessoaJuridica.class, id);
        em.remove(pessoaJuridica);
    }

    public void update(PessoaJuridica pessoaJuridica){
        em.merge(pessoaJuridica);
    }
}
