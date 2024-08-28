package com.example.aula.model.repository;

import com.example.aula.model.entity.Produto;
import com.example.aula.model.entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleRepository {

    @PersistenceContext  //informa ao container do Spring a responsabilidade de gerenciar a dependência
    private EntityManager em;

    public void save(Role role){
        em.persist(role);
    }

    public Role role(Long id){
        return em.find(Role.class, id);
    }
    public List<Role> findByName(String name){
        Query query = em.createQuery("from Role where nome = :name");
        query.setParameter("name", name);
        return query.getResultList();
    }

}
