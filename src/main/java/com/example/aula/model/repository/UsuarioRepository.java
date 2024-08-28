package com.example.aula.model.repository;

import com.example.aula.model.entity.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsuarioRepository {

    @PersistenceContext
    private EntityManager em;


    public Usuario usuario(Long id){
        return em.find(Usuario.class, id);
    }

    public void save(Usuario usuario){
        em.persist(usuario);
    }

    public Usuario findByNome(String username) {
        String nomeFormated = username.toLowerCase();
        Query query = em.createQuery("from Usuario u where LOWER(u.username) like :nome");
        query.setParameter("nome", "%"+nomeFormated+"%");
        return (Usuario) query.getSingleResult();
    }

    public List<Usuario> usuarios(){
        Query query = em.createQuery("from Usuario");
        return query.getResultList();
    }

    public void remove(Long id){
        Usuario usuario = em.find(Usuario.class, id);
        em.remove(usuario);
    }


    public void update(Usuario usuario){
        em.merge(usuario);
    }

}
