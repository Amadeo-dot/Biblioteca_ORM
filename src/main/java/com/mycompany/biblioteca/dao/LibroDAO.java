package com.mycompany.biblioteca.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;
import com.mycompany.biblioteca.Clases.Libro;
import com.mycompany.biblioteca.JPAUtil;

public class LibroDAO {

    public void crearLibro(Libro libro) {
        EntityManager em = JPAUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(libro);
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

    public void eliminarLibro(Integer libroId) {
        EntityManager em = JPAUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Libro libro = em.find(Libro.class, libroId);
            if (libro != null) {
                em.remove(libro);
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

    public List<Libro> obtenerTodosLosLibros() {
        EntityManager em = JPAUtil.getEmf().createEntityManager();
        List<Libro> libros = null;
        try {
            TypedQuery<Libro> query = em.createQuery(
                "SELECT l FROM Libro l", Libro.class);
            libros = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return libros;
    }

    public Libro obtenerLibroPorId(Integer libroId) {
        EntityManager em = JPAUtil.getEmf().createEntityManager();
        Libro libro = null;
        try {
            libro = em.find(Libro.class, libroId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return libro;
    }

    public List<Libro> obtenerLibrosPorCategoria(Integer categoriaId) {
        EntityManager em = JPAUtil.getEmf().createEntityManager();
        List<Libro> libros = null;
        try {
            TypedQuery<Libro> query = em.createQuery(
                "SELECT l FROM Libro l WHERE l.categoria.id = :categoriaId", Libro.class);
            query.setParameter("categoriaId", categoriaId);
            libros = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return libros;
    }
    
    public void actualizarLibro(Libro libro) {
        EntityManager em = JPAUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(libro);
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