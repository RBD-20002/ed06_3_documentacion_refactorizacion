package org.ed06.model;
import java.util.Arrays;

/**
 * Clase especializada en la entrada y validación de datos numéricos de tipo double.
 * Hereda de la clase genérica EntradaDatos<Double> para manejar valores decimales.
 * Permite definir un rango válido entre un valor mínimo y máximo.
 */
public class DatoDouble extends EntradaDatos<Double> {
    private final double minimo;
    private final double maximo;

    /**
     * Constructor que inicializa el validador de números double con rango específico.
     * @param campo Nombre del campo que se está validando (para mensajes de error)
     * @param minimo Valor mínimo permitido (inclusive)
     * @param maximo Valor máximo permitido (inclusive)
     * @throws IllegalArgumentException si el mínimo es mayor que el máximo
     */
    public DatoDouble(String campo, double minimo, double maximo) {
        this.minimo = minimo;
        this.maximo = maximo;
        setMensajesError(Arrays.asList("El " + campo + " debe ser un número entre " + minimo + " y " + maximo));
    }

    /**
     * Convierte una cadena de texto a su representación numérica double.
     * @param entrada Cadena de texto a convertir
     * @return Valor double convertido
     * @throws NumberFormatException si la cadena no contiene un número válido
     */
    @Override
    protected Double convertir(String entrada) throws NumberFormatException {
        return Double.parseDouble(entrada);
    }

    /**
     * Valida que el número esté dentro del rango especificado.
     * @param valor Número a validar
     * @return true si el valor está dentro del rango [minimo, maximo], false en caso contrario
     */
    @Override
    protected boolean validar(Double valor) {
        return valor >= minimo && valor <= maximo;
    }
}