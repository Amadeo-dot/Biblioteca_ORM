/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.biblioteca;

import com.mycompany.biblioteca.Clases.Associado;
import com.mycompany.biblioteca.Clases.Categoria;
import com.mycompany.biblioteca.Clases.Libro;
import com.mycompany.biblioteca.dao.AssociadoDAO;
import com.mycompany.biblioteca.dao.CategoriaDAO;
import com.mycompany.biblioteca.dao.LibroDAO;
import com.mycompany.biblioteca.dao.ReservaDAO;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author alumne
 */
public class Biblioteca {

    private static final Scanner lector = new Scanner(System.in);
    private static final AssociadoDAO associadoDAO = new AssociadoDAO();
    private static final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private static final LibroDAO libroDAO = new LibroDAO();
    private static final ReservaDAO reservaDAO = new ReservaDAO();

    public static void main(String[] args) {
        int opcion = 1;
        while (opcion != 0) {
            System.out.println("\n=== GESTIÓN DE BIBLIOTECA ===");
            System.out.println("1. Gestión de Libros");
            System.out.println("2. Gestión de Associados");
            System.out.println("3. Gestión de Categorias");
            System.out.println("4. Gestión de Reservas");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = lector.nextInt();
            lector.nextLine();

            switch (opcion) {
                case 1 ->
                    gestionLibros();
                case 2 ->
                    gestionAssociados();
                case 3 ->
                    gestionCategorias();
                case 4 ->
                    gestionReserva();
                case 0 ->
                    System.out.println("");
                default ->
                    System.out.println("Opción no válida.");
            }
        }
        System.out.println("Bye bye");
    }

    private static void gestionLibros() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- MENÚ LIBROS ---");
            System.out.println("1. Listar todos los libros");
            System.out.println("2. Crear nuevo libro");
            System.out.println("3. Actualizar libro existente");
            System.out.println("4. Eliminar libro");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione: ");
            opcion = lector.nextInt();
            lector.nextLine();

            switch (opcion) {
                case 1 -> {
                    List<Libro> libros = libroDAO.obtenerTodosLosLibros();
                    System.out.println("\nID | TÍTULO | AUTOR | STOCK | CATEGORÍA");
                    for (Libro l : libros) {
                        String nombreCat = (l.getCategoria() != null) ? l.getCategoria().getNombre() : "Sin categoría";
                        System.out.printf("%d | %s | %s | %d | %s%n",
                                l.getId_libro(), l.getTitulo(), l.getAutor(), l.getStock(), nombreCat);
                    }
                }
                case 2 -> {
                    Libro nuevo = new Libro();
                    System.out.print("Título: ");
                    nuevo.setTitulo(lector.nextLine());
                    System.out.print("Autor: ");
                    nuevo.setAutor(lector.nextLine());
                    System.out.print("Stock inicial: ");
                    nuevo.setStock(lector.nextInt());
                    lector.nextLine();

                    System.out.print("Categoría: ");
                    String idCat = lector.nextLine();
                    Categoria cat = categoriaDAO.obtenerCategoriaPorNombre(idCat);
                    if (cat != null) {
                        nuevo.setCategoria(cat);
                        libroDAO.crearLibro(nuevo);
                        System.out.println("Libro creado correctamente.");
                    } else {
                        System.out.println("Error: La categoría no existe. Creación cancelada.");
                    }
                }
                case 3 -> {
                    System.out.print("ID del libro a modificar: ");
                    int idMod = lector.nextInt();
                    lector.nextLine();
                    Libro libroMod = libroDAO.obtenerLibroPorId(idMod);

                    if (libroMod != null) {
                        System.out.print("Nuevo título (" + libroMod.getTitulo() + "): ");
                        libroMod.setTitulo(lector.nextLine());
                        System.out.print("Nuevo autor (" + libroMod.getAutor() + "): ");
                        libroMod.setAutor(lector.nextLine());
                        System.out.print("Nuevo stock (" + libroMod.getStock() + "): ");
                        libroMod.setStock(lector.nextInt());

                        libroDAO.actualizarLibro(libroMod);
                        System.out.println("Libro actualizado.");
                    } else {
                        System.out.println("Libro no encontrado.");
                    }
                }
                case 4 -> {
                    System.out.print("ID del libro a eliminar: ");
                    int idEli = lector.nextInt();
                    libroDAO.eliminarLibro(idEli);
                    System.out.println("Operación de eliminación realizada.");
                }
                case 0 ->
                    System.out.println("");
                default ->
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void gestionAssociados() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- MENÚ ASOCIADOS ---");
            System.out.println("1. Listar todos los asociados");
            System.out.println("2. Registrar nuevo asociado");
            System.out.println("3. Actualizar datos de asociado");
            System.out.println("4. Dar de baja asociado");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione: ");
            opcion = lector.nextInt();
            lector.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1 -> {
                    List<Associado> lista = associadoDAO.findAll();
                    System.out.println("\nID | NOMBRE COMPLETO | EMAIL | TELÉFONO");
                    System.out.println("------------------------------------------");
                    for (Associado a : lista) {
                        System.out.printf("%d | %s %s | %s | %s%n",
                                a.getId(), a.getNombre(), a.getApellidos(), a.getEmail(), a.getTelefono());
                    }
                }
                case 2 -> {
                    Associado nuevo = new Associado();
                    System.out.print("Nombre: ");
                    nuevo.setNombre(lector.nextLine());
                    System.out.print("Apellidos: ");
                    nuevo.setApellidos(lector.nextLine());
                    System.out.print("Email: ");
                    nuevo.setEmail(lector.nextLine());
                    System.out.print("Teléfono: ");
                    nuevo.setTelefono(lector.nextLine());

                    associadoDAO.create(nuevo);
                    System.out.println("Asociado registrado correctamente.");
                }
                case 3 -> {
                    System.out.print("ID del asociado a modificar: ");
                    int idMod = lector.nextInt();
                    lector.nextLine();
                    Associado socio = associadoDAO.findById(idMod);

                    if (socio != null) {
                        System.out.print("Nuevo Nombre (" + socio.getNombre() + "): ");
                        socio.setNombre(lector.nextLine());
                        System.out.print("Nuevos Apellidos (" + socio.getApellidos() + "): ");
                        socio.setApellidos(lector.nextLine());
                        System.out.print("Nuevo Email (" + socio.getEmail() + "): ");
                        socio.setEmail(lector.nextLine());

                        associadoDAO.update(socio);
                        System.out.println("Datos actualizados.");
                    } else {
                        System.out.println("Asociado no encontrado.");
                    }
                }
                case 4 -> {
                    System.out.print("ID del asociado a eliminar: ");
                    int idEli = lector.nextInt();
                    associadoDAO.delete(idEli);
                    System.out.println("Eliminado.");
                }
                case 0 ->
                    System.out.println("Regresando...");
                default ->
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void gestionCategorias() {
        int opcion = 1;
        while (opcion != 0) {
            System.out.println("\n--- MENÚ CATEGORÍAS ---");
            System.out.println("1. Listar todas las categorías");
            System.out.println("2. Crear nueva categoría");
            System.out.println("3. Actualizar categoría");
            System.out.println("4. Eliminar categoría");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione: ");
            opcion = lector.nextInt();
            lector.nextLine();

            switch (opcion) {
                case 1 -> {
                    List<Categoria> categorias = categoriaDAO.obtenerTodasLasCategorias();
                    System.out.println("\nID | NOMBRE");
                    System.out.println("------------");
                    for (Categoria c : categorias) {
                        System.out.printf("%d | %s%n", c.getId(), c.getNombre());
                    }
                }
                case 2 -> {
                    Categoria nueva = new Categoria();
                    System.out.print("Nombre de la nueva categoría: ");
                    nueva.setNombre(lector.nextLine());

                    categoriaDAO.crearCategoria(nueva);
                    System.out.println("Categoría '" + nueva.getNombre() + "' creada con éxito.");
                }
                case 3 -> {
                    System.out.print("Categoría a modificar: ");
                    String idMod = lector.nextLine();
                    lector.nextLine();
                    Categoria catMod = categoriaDAO.obtenerCategoriaPorNombre(idMod);

                    if (catMod != null) {
                        System.out.print("Nuevo nombre (Actual: " + catMod.getNombre() + "): ");
                        catMod.setNombre(lector.nextLine());

                        categoriaDAO.actualizarCategoria(catMod);
                        System.out.println("Categoría actualizada correctamente.");
                    } else {
                        System.out.println("Error: Categoría no encontrada.");
                    }
                }
                case 4 -> {
                    System.out.print("ID de la categoría a eliminar: ");
                    int idEli = lector.nextInt();
                    categoriaDAO.eliminarCategoria(idEli);
                    System.out.println("Eliminado");
                }
                case 0 ->
                    System.out.println("");
                default ->
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void gestionReserva() {
        int opcion = 1;
        while (opcion != 0) {
            System.out.println("\n--- MENÚ DE RESERVAS ---");
            System.out.println("1. Crear reserva");
            System.out.println("2. Eliminar reserva");
            System.out.println("3. Ver reservas por Libro");
            System.out.println("4. Ver reservas por Asociado");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione: ");
            opcion = lector.nextInt();
            lector.nextLine();

            switch (opcion) {
                case 1 -> {
                    System.out.print("ID del Asociado: ");
                    int idSocio = lector.nextInt();
                    System.out.print("ID del Libro: ");
                    int idLibro = lector.nextInt();
                    reservaDAO.crearReserva(idLibro, idSocio);
                }
                case 2 -> {
                    System.out.print("ID del Asociado: ");
                    int idSocio = lector.nextInt();
                    System.out.print("ID del Libro a devolver: ");
                    int idLibro = lector.nextInt();
                    reservaDAO.eliminarReserva(idLibro, idSocio);
                }
                case 3 -> {
                    System.out.print("ID del Libro: ");
                    int idLibro = lector.nextInt();
                    Libro libro = libroDAO.obtenerLibroPorId(idLibro);
                    if (libro != null) {
                        System.out.println("Asociados que tienen reservado '" + libro.getTitulo() + "':");
                        if (libro.getReservados().isEmpty()) {
                            System.out.println("No hay reservas para este libro.");
                        } else {
                            for (Associado a : libro.getReservados()) {
                                System.out.println("- " + a.getNombre() + " " + a.getApellidos() + " (Email: " + a.getEmail() + ")");
                            }
                        }
                    } else {
                        System.out.println("Libro no encontrado.");
                    }
                }
                case 4 -> {
                    System.out.print("ID del Asociado: ");
                    int idSocio = lector.nextInt();
                    Associado socio = associadoDAO.findById(idSocio);
                    if (socio != null) {
                        System.out.println("Libros reservados por " + socio.getNombre() + ":");
                        if (socio.getLibrosReservados().isEmpty()) {
                            System.out.println("Este asociado no tiene libros.");
                        } else {
                            for (Libro l : socio.getLibrosReservados()) {
                                System.out.println("- " + l.getTitulo() + " | Autor: " + l.getAutor());
                            }
                        }
                    } else {
                        System.out.println("Asociado no encontrado.");
                    }
                }
                case 5 -> {
                    List<Libro> librosReservados = reservaDAO.obtenerTodasLasReservas();

                    if (librosReservados.isEmpty()) {
                        System.out.println("No hay reservas registradas.");
                    } else {
                        System.out.println("ID LIBRO | TÍTULO | ASOCIADO");
                        for (Libro l : librosReservados) {
                            for (Associado a : l.getReservados()) {
                                System.out.printf("%d | %s | %s %s%n",
                                        l.getId_libro(), l.getTitulo(), a.getNombre(), a.getApellidos());
                            }
                        }
                    }
                }
                case 0 ->
                    System.out.println("");
                default ->
                    System.out.println("Opción no válida.");
            }
        }
    }
}
