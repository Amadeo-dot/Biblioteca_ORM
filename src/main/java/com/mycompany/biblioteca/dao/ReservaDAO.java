
package com.mycompany.biblioteca.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;
import com.mycompany.biblioteca.modelo.Reserva;

public class ReservaDAO {
    
//crear reserva
public void crearReserva(Reserva reserva) {
EntityManager em = JPAUtil.getEmf().createEntityManager();
EntityTransaction tx = em.getTransaction();
try {
    tx.begin();
    em.persist(reserva);
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

//eliminar reserva
public void eliminarReserva(Long reservaId) {
EntityManager em = JPAUtil.getEmf().createEntityManager();
EntityTransaction tx = em.getTransaction(); 
try {
    tx.begin();
    Reserva reserva = em.find(Reserva.class, reservaId);
    if (reserva != null) {
        em.remove(reserva);
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

//devolver toadas las reservas
public List<Reserva> obtenerTodasLasReservas() {
EntityManager em = JPAUtil.getEmf().createEntityManager();
List<Reserva> reservas = null;
try {
    TypedQuery<Reserva> query = em.createQuery(
        "SELECT r FROM Reserva r", Reserva.class);
    reservas = query.getResultList();
} catch (Exception e) {
    e.printStackTrace();
} finally {
    em.close(); 
}
return reservas;
}


//obtener el id de la reserva
public Reserva obtenerReservaPorId(Long reservaId) {
EntityManager em = JPAUtil.getEmf().createEntityManager();
Reserva reserva = null;
try {
    reserva = em.find(Reserva.class, reservaId);
} catch (Exception e) {
    e.printStackTrace();
} finally {
    em.close(); 
}
return reserva;
}


// devolver fechas de reserva para ese libro
public List<Reserva> obtenerReservasPorLibro(Long libroId) {
EntityManager em = JPAUtil.getEmf().createEntityManager();
List<Reserva> reservas = null; 
try {
    TypedQuery<Reserva> query = em.createQuery(
        "SELECT r FROM Reserva r WHERE r.libro.id = :libroId", Reserva.class);
    query.setParameter("libroId", libroId);
    reservas = query.getResultList();
} catch (Exception e) {
    e.printStackTrace();
} finally {
    em.close(); 

}
return reservas;
} 



//devolver reservas por nombre de libro solo las fechas
public List<Reserva> obtenerReservasPorNombreDeLibro(String nombreLibro) {
EntityManager em = JPAUtil.getEmf().createEntityManager();
List<Reserva> reservas = null;
try {
    TypedQuery<Reserva> query = em.createQuery(
        "SELECT r FROM Reserva r WHERE r.libro.titulo = :nombreLibro", Reserva.class);
    query.setParameter("nombreLibro", nombreLibro);
    reservas = query.getResultList();
} catch (Exception e) {
    e.printStackTrace();
} finally {
    em.close();     
}
return reservas;
} 

}

    
    

