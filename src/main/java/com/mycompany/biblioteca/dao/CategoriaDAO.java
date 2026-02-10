package com.mycompany.biblioteca.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;
import com.mycompany.biblioteca.Clases.Categoria;
import com.mycompany.biblioteca.JPAUtil;

public class CategoriaDAO {

    public void crearCategoria(Categoria categoria) {
        EntityManager em = JPAUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(categoria);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void eliminarCategoria(int categoriaId) {
        EntityManager em = JPAUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Categoria categoria = em.find(Categoria.class, categoriaId);
            if (categoria != null) {
                em.remove(categoria);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<Categoria> obtenerTodasLasCategorias() {
        EntityManager em = JPAUtil.getEmf().createEntityManager();
        List<Categoria> categorias = null;
        try {
            TypedQuery<Categoria> query = em.createQuery(
                "SELECT c FROM Categoria c", Categoria.class);
            categorias = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return categorias;
    }

    public Categoria obtenerCategoriaPorNombre(String nombre) {
        EntityManager em = JPAUtil.getEmf().createEntityManager();
        Categoria categoria = null;
        try {
            TypedQuery<Categoria> query = em.createQuery(
                "SELECT c FROM Categoria c WHERE c.nombre = :nombre", Categoria.class);
            query.setParameter("nombre", nombre);
            categoria = query.getSingleResult();
        } catch (Exception e) {
            categoria = null;
        } finally {
            em.close();
        }
        return categoria;
    }

    public void actualizarCategoria(Categoria categoria) {
        EntityManager em = JPAUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(categoria);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
