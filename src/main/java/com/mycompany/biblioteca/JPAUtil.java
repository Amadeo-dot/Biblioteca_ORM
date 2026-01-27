package com.mycompany.biblioteca;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("appPU");

    public static EntityManagerFactory getEmf() { return emf; }

    public static void close() {
        if (emf != null && emf.isOpen()) emf.close();
    }
}
