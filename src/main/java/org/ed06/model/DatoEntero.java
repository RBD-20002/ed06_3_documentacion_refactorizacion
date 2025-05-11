package org.ed06.model;
import java.util.Arrays;

/**
 * Clase especializada en la entrada y validación de datos numéricos enteros.
 * Hereda de la clase genérica EntradaDatos<Integer> para manejar valores enteros.
 * Permite definir un rango válido entre un valor mínimo y máximo inclusive.
 */
public class DatoEntero extends EntradaDatos<Integer>{
    private int minimo;
    private int maximo;

    /**
     * Constructor que inicializa el validador de números enteros con rango específico.
     * @param campo Nombre del campo que se está validando (para mensajes de error)
     * @param minimo Valor mínimo permitido (inclusive)
     * @param maximo Valor máximo permitido (inclusive)
     * @throws IllegalArgumentException si el mínimo es mayor que el máximo
     */
    public DatoEntero(String campo, int minimo, int maximo) {
        this.minimo = minimo;
        this.maximo = maximo;
        setMensajesError(Arrays.asList("El campo solo puede estar entre: "+minimo+" y "+maximo));
    }

    /**
     * Convierte una cadena de texto a su representación numérica entera.
     * @param entrada Cadena de texto a convertir
     * @return Valor entero convertido
     * @throws NumberFormatException si la cadena no contiene un número entero válido
     */
    @Override
    protected Integer convertir(String entrada) throws Exception {
        return Integer.parseInt(entrada);
    }

    /**
     * Valida que el número esté dentro del rango especificado.
     * @param valor Número a validar
     * @return true si el valor está dentro del rango [minimo, maximo], false en caso contrario
     */
    @Override
    protected boolean validar(Integer valor){
        return valor >= minimo && valor <= maximo;
    }
}
