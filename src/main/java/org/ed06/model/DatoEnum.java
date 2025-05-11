package org.ed06.model;
import java.util.Arrays;
import java.util.List;

/**
 * Clase genérica para manejar entrada y validación de valores enumerados (enum).
 * Permite convertir cadenas de texto a valores de enumeración y validar que sean opciones válidas.
 * @param <E> Tipo de enumeración que manejará esta instancia, debe ser una subclase de Enum
 */
public class DatoEnum <E extends Enum<E>> extends EntradaDatos<E>{
    private final Class<E> datoEnum;

    /**
     * Constructor que inicializa el validador para un tipo enum específico.
     * @param datoEnum Clase del enum que se utilizará para la conversión y validación
     * @throws IllegalArgumentException si datoEnum es null
     */
    public DatoEnum(Class<E> datoEnum) {
        this.datoEnum = datoEnum;
        setMensajesError(List.of("Opcion invalida, opciones: "+ Arrays.toString(datoEnum.getEnumConstants())));
    }

    /**
     * Convierte una cadena de texto al valor enum correspondiente.
     * @param entrada Cadena de texto a convertir
     * @return Valor enum correspondiente a la entrada
     * @throws IllegalArgumentException si la cadena no coincide con ningún valor del enum
     */
    @Override
    protected E convertir(String entrada){
        return Enum.valueOf(datoEnum, entrada.trim());
    }
}
