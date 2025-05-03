package org.ed06.model;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Clase que representa una reserva de hotel, con información sobre
 * la habitación, el cliente, fechas y coste dependiendo la habitación
 */
public class Reserva {
    private final int id;
    private final Habitacion habitacion;
    private final Cliente cliente;
    private final LocalDate fechaInicio;
    private final LocalDate fechaFin;
    private final double precioTotal;

    /**
     * Constructor para crear una reserva
     * @param id ID única de la reserva
     * @param habitacion Habitación reservada
     * @param cliente Cliente que relaiza la reserva
     * @param fechaInicio Fecha de inicio de la reserva
     * @param fechaFin Fecha de fin de la reserva
     */
    public Reserva(int id, Habitacion habitacion, Cliente cliente, LocalDate fechaInicio, LocalDate fechaFin) {
        this.id = id;
        this.habitacion = habitacion;
        this.cliente = cliente;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precioTotal = calcularPrecioFinal();
    }

    //Getters
    public int getId() {
        return id;
    }
    public Habitacion getHabitacion() {
        return habitacion;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    public LocalDate getFechaFin() {
        return fechaFin;
    }
    public double getPrecioTotal() {
        return precioTotal;
    }

    /**
     * Calcula el precio final con descuento aplicado
     * @return Precio total después de descuentos correspondientes
     */
    private double calcularPrecioFinal() {
        int dias = fechaInicio.until(fechaFin).getDays();
        double precio = habitacion.getPrecioBase() * dias;

        // Aplicar descuento VIP del 10% si corresponde
        if (cliente.isEsVip()) {
            precio -= precio * 0.10;
        }

        // Aplicar descuento adicional del 5% para estadías largas
        if (dias > 7) {
            precio -= precio * 0.05;
        }
        return precioTotal;
    }

    /**
     * Representa la información una vez creada la reserva
     */
    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", habitacion=" + habitacion +
                ", cliente=" + cliente +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", precioTotal=" + precioTotal +
                '}';
    }
}
