package org.ed06.model;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Clase especializada en el manejo de entrada y validación de fechas.
 * Permite convertir cadenas de texto a objetos LocalDate según un formato específico.
 */
public class DatoFecha extends EntradaDatos<LocalDate> {

    private final DateTimeFormatter formato;

    /**
     * Constructor que inicializa el formateador de fechas con un patrón específico.
     * @param campo Nombre del campo de fecha (para mensajes de error descriptivos)
     * @param patron Patrón de formato de fecha (ej. "dd/MM/yyyy")
     * @throws IllegalArgumentException si el patrón es nulo o inválido
     */
    public DatoFecha(String campo, String patron){
        formato = DateTimeFormatter.ofPattern(patron);
        setMensajesError(List.of("Fecha invalida"));
    }

    /**
     * Convierte una cadena de texto a un objeto LocalDate según el formato especificado.
     * @param entrada Cadena de texto con la fecha a convertir
     * @return Objeto LocalDate representando la fecha
     * @throws DateTimeParseException si la cadena no coincide con el formato esperado
     */
    @Override
    protected LocalDate convertir(String entrada) throws DateTimeParseException {
        return LocalDate.parse(entrada,formato);
    }
}
