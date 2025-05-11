package org.ed06.model;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Predicate;

/**
 * Clase abstracta base para el manejo de entrada de datos con validación.
 * Proporciona funcionalidad común para leer y validar datos de diferentes tipos.
 * @param <T> Tipo de dato que será manejado por la implementación concreta
 */
public abstract class EntradaDatos<T> {
    protected static final Scanner sc = new Scanner(System.in);
    protected List<String> mensajesError = new ArrayList<>();
    private Predicate<T> validacionExtra = valor -> true;
    private Random random = new Random();

    /**
     * Constructor que inicializa la lista de mensajes de error con un mensaje por defecto.
     */
    public EntradaDatos() {
        mensajesError.add("Entrada inválida. Intente de nuevo.");
    }

    /**
     * Establece los mensajes de error personalizados.
     * @param mensajes Lista de mensajes de error a utilizar
     * @throws IllegalArgumentException si la lista de mensajes es nula o vacía
     */
    public void setMensajesError(List<String> mensajes) {
        if(mensajes != null && !mensajes.isEmpty())
            this.mensajesError = mensajes;
    }

    /**
     * Establece una validación adicional personalizada.
     * @param validacion Predicado que representa la validación adicional
     */
    public void setValidacionExtra(Predicate<T> validacion) {
        if (validacion != null)
            this.validacionExtra = validacion;
    }

    /**
     * Obtiene un mensaje de error aleatorio de la lista disponible.
     * @return Mensaje de error seleccionado aleatoriamente
     */
    protected String getMensajeError() {
        return mensajesError.get(random.nextInt(mensajesError.size()));
    }

    /**
     * Lee y valida una entrada del usuario hasta que sea correcta.
     * @param prompt Mensaje que se muestra al usuario para solicitar la entrada
     * @return Valor convertido y validado
     * @throws IllegalStateException si ocurre un error inesperado durante la lectura
     */
    public T leer(String prompt) {
        T valor = null;
        boolean valido = false;
        do {
            System.out.print(prompt);
            String entrada = sc.nextLine().trim();
            try {
                valor = convertir(entrada);
                if (!validar(valor) || !validacionExtra.test(valor)) {
                    System.out.println(getMensajeError());
                } else {
                    valido = true;
                }
            } catch(Exception e) {
                System.out.println(getMensajeError());
            }
        } while(!valido);
        return valor;
    }

    /**
     * Convierte una cadena de entrada al tipo de dato específico.
     * @param entrada Cadena de texto a convertir
     * @return Valor convertido
     * @throws Exception si la conversión falla
     */
    protected abstract T convertir(String entrada) throws Exception;

    /**
     * Realiza la validación básica del valor convertido.
     * @param valor Valor a validar
     * @return true si el valor es válido, false en caso contrario
     */
    protected boolean validar(T valor) {
        return true;
    }
}