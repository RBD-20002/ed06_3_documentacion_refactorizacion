package org.ed06.model;
import java.util.Arrays;

/**
 * Clase para manejar entrada de datos de tipo texto con validación básica
 */
public class DatoTexto extends EntradaDatos<String> {

    /**
     * Constructor para DatoTexto
     * @param campo Nombre del campo que se está solicitando
     * @param femenino Indica si el campo es de género femenino para el mensaje de error
     */
    public DatoTexto(String campo, boolean femenino) {
        String articulo = femenino ? "La" : "El";
        setMensajesError(Arrays.asList(articulo+" "+campo+" no puede dejarse vacio"));
    }

    /**
     * Convierte la entrada de texto (no realiza conversión real)
     * @param entrada Texto ingresado por el usuario
     * @return El mismo texto sin modificaciones
     */
    @Override
    protected String convertir(String entrada) {
        return entrada;
    }

    /**
     * Valida que el texto no esté vacío
     * @param valor Texto a validar
     * @return true si el texto no es nulo ni vacío, false en caso contrario
     */
    @Override
    protected boolean validar(String valor) {
        return valor != null && !valor.isEmpty();
    }
}