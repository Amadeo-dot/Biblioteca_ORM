package com.mycompany.biblioteca.dao;

import com.mycompany.biblioteca.Clases.Associado;
import com.mycompany.biblioteca.Clases.Libro;
import com.mycompany.biblioteca.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    public void crearReserva(Integer idLibro, Integer idAssociado) {
        EntityManager em = JPAUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Libro libro = em.find(Libro.class, idLibro);
            Associado socio = em.find(Associado.class, idAssociado);

            if (libro != null && socio != null) {
                if (libro.getStock() > 0) {
                    libro.getReservados().add(socio);
                    socio.getLibrosReservados().add(libro);

                    libro.setStock(libro.getStock() - 1);

                    em.merge(libro);
                    em.merge(socio);
                    tx.commit();
                    System.out.println("Reserva realizada con éxito.");
                } else {
                    System.out.println("Error: No hay stock disponible.");
                }
            } else {
                System.out.println("Error: Libro o Asociado no encontrado.");
            }
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void eliminarReserva(Integer idLibro, Integer idAssociado) {
        EntityManager em = JPAUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Libro libro = em.find(Libro.class, idLibro);
            Associado socio = em.find(Associado.class, idAssociado);

            if (libro != null && socio != null) {
                libro.getReservados().remove(socio);
                socio.getLibrosReservados().remove(libro);

                libro.setStock(libro.getStock() + 1);

                em.merge(libro);
                em.merge(socio);
                tx.commit();
            }
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<Libro> obtenerTodasLasReservas() {
        EntityManager em = JPAUtil.getEmf().createEntityManager();
        try {
            TypedQuery<Libro> query = em.createQuery(
                    "SELECT DISTINCT l FROM Libro l JOIN FETCH l.reservados", Libro.class);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }
}
