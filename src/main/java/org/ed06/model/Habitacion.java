package org.ed06.model;

/**
 * Clase que representa una habitación de un hotel
 */
public class Habitacion {
    private final int numero;
    private final TipoHabitacion tipo; // "SIMPLE", "DOBLE", "SUITE", "LITERAS"
    private double precioBase;
    private boolean disponible;

    /**
     * Constructor para crear un objeto habitación
     * @param numero Número de habitación que séra único
     * @param tipo Tpo de habitación que solo puede ser (SUITE-LITERA-SIMPLE-DOBLE)
     * @param precioBase El precio que séra sujeto al tipo de habitación
     * @param disponible El estado de la habitación (DISPONIBLE-NO DISPONIBLE)
     */
    public Habitacion(int numero, TipoHabitacion tipo, double precioBase, boolean disponible) {
        this.numero = numero;
        this.tipo = tipo;
        this.precioBase = precioBase;
        this.disponible = disponible;
    }

    //Getters
    public int getNumero() {
        return numero;
    }
    public TipoHabitacion getTipo() {
        return tipo;
    }
    public double getPrecioBase() {
        return precioBase;
    }
    public boolean isDisponible() {
        return disponible;
    }

    /**
     * Reserva la habitación si está disponible
     * @throws IllegalStateException si la habitación no está disponible
     */
    public void reservar() {
        if (!disponible) {
            throw new IllegalStateException("La habitación no está disponible");
        }
        disponible = false;
        System.out.println("Habitación #" + numero + " reservada");
    }

    /**
     * Libera la habitación, cambiando su estado a disponible
     */
    public void liberar() {
        disponible = true;
    }

}
