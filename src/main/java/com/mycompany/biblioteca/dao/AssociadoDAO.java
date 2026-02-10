/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.biblioteca.dao;

import com.mycompany.biblioteca.Clases.Associado;
import com.mycompany.biblioteca.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

/**
 * s
 *
 * @author marin
 */
public class AssociadoDAO {

    public void create(Associado associado) {

        EntityManager em = JPAUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();              // Comienza la transacción
            em.persist(associado);  // Guarda el objeto en la BD
            tx.commit();            // Confirma los cambios
        } catch (Exception e) {
            // Si ocurre un error, deshacer cambios
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            // Cerrar el EntityManager siempre
            em.close();
        }
    }

    /**
     * Actualiza un associado existente en la base de datos.
     */
    public void update(Associado associado) {

        EntityManager em = JPAUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();             // Comienza la transacción
            em.merge(associado);    // Actualiza el associado
            tx.commit();            // Confirma los cambios
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();      // Revierte cambios si hay error
            }
            e.printStackTrace();
        } finally {
            em.close();             // Cierra el EntityManager
        }
    }

    /**
     * Elimina un associado de la base de datos.
     */
    public void delete(Integer id) {
        EntityManager em = JPAUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // Buscamos el asociado directamente por el ID recibido
            Associado a = em.find(Associado.class, id);
            if (a != null) {
                em.remove(a);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Error al eliminar asociado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    /**
     * Devuelve una lista con todos los associados de la base de datos.
     */
    public List<Associado> findAll() {

        EntityManager em = JPAUtil.getEmf().createEntityManager();
        List<Associado> lista;

        try {
            lista = em.createQuery(
                    "FROM Associado", Associado.class
            ).getResultList();
        } finally {
            em.close();
        }

        return lista;
    }

    public List<Associado> findByName(String nombre) {
        EntityManager em = JPAUtil.getEmf().createEntityManager();
        try {
            return em.createQuery("SELECT a FROM Associado a WHERE a.nombre LIKE :nombre", Associado.class)
                    .setParameter("nombre", "%" + nombre + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Associado findById(Integer id) {
        EntityManager em = JPAUtil.getEmf().createEntityManager();
        try {
            return em.find(Associado.class, id);
        } finally {
            em.close();
        }
    }
}
