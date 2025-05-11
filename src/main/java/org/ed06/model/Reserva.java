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
     * Calcula el precio final aplicando descuentos por VIP y estadías largas
     * @return Precio total con descuentos aplicados
     */
    private double calcularPrecioFinal() {
        int dias = (int) ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        double precio = habitacion.getPrecioBase() * dias;

        if (cliente.isEsVip()) {
            precio *= 0.90; // Descuento 10% VIP
        }

        if (dias > 7) {
            precio *= 0.95; // Descuento adicional 5% por estadía larga
        }
        return precio;
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
