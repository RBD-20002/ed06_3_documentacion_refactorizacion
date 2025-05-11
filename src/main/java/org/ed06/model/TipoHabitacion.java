package org.ed06.model;

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
      *
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

