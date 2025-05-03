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
     * Enumeracion que representa los tipos de
     * habitacion con su capacidad maxima
     */
    public enum TipoHabitacion {
        SIMPLE(1),
        DOBLE(2),
        SUITE(4),
        LITERAS(8);
        private final int capacidadMaxima;
        /**
         * Constructor de la capacidad de habitaciones
         * @param capacidadMaxima
         */
        TipoHabitacion(int capacidadMaxima) {
            this.capacidadMaxima = capacidadMaxima;
        }
        /**
         * Obtiene la capacidad maxima
         * @return capacidad maxima
         */
        public int getCapacidadMaxima() {
            return capacidadMaxima;
        }
    }

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
     * Reserva la habitación si esta disponible
     * @throws IllegalStateException si la habitación no esta disponible
     */
    public void reservar() {
        if (disponible) {
            System.out.println("Habitación #" + numero + " ya reservada");
        }
        disponible = true;
    }

    /**
     * Modifica el estado a disponible de una habitación
     */
    public void liberar() {
        disponible = true;
    }

    /**
     * Representa la información de la habitación
     */
    @Override
    public String toString() {
        return "|---------------------|" +
                "\nHabitacion: " +
                "\n -Numero: " + numero +
                "\n -Tipo: " + tipo +
                "\n -Precio Base: " + precioBase +
                "\n -Disponible: " + disponible;
    }
}
