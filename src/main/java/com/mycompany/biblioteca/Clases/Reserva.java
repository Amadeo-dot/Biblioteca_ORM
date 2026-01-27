
package com.mycompany.biblioteca.Clases;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "RESERVAS")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RESERVA")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_LIBRO", nullable = false)
    private Libro libro;

    @ManyToOne
    @JoinColumn(name = "ID_ASSOCIADO", nullable = false)
    private Associado asociado;

    @Column(name = "FECHA_RESERVA", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaReserva;

    @Column(name = "FECHA_DEVOLUCION")
    @Temporal(TemporalType.DATE)
    private Date fechaDevolucion;

    public Reserva() {
    }

    public Reserva(Integer id, Libro libro, Associado asociado, Date fechaReserva, Date fechaDevolucion) {
        this.id = id;
        this.libro = libro;
        this.asociado = asociado;
        this.fechaReserva = fechaReserva;
        this.fechaDevolucion = fechaDevolucion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Associado getAsociado() {
        return asociado;
    }

    public void setAsociado(Associado asociado) {
        this.asociado = asociado;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    //to string con las fechas en formato dd/MM/yyyy
    @Override
    public String toString() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        String fechaReservaStr = (fechaReserva != null) ? sdf.format(fechaReserva) : "N/A";
        String fechaDevolucionStr = (fechaDevolucion != null) ? sdf.format(fechaDevolucion) : "N/A";
        return "Reserva{" + "id=" + id + ", libro=" + libro.getTitulo() + ", asociado=" + asociado.getNombre() + ", fechaReserva=" + fechaReservaStr + ", fechaDevolucion=" + fechaDevolucionStr + '}';
    }

    
}
