package org.ed06.model;

/**
 * Clase que representa un cliente con sus datos y su estado VIP
 */
public class Cliente {

    public int id; /** Identificador único del cliente */
    public String nombre; /** Nombre completo del cliente */
    public String dni; /** Documento con formato español del cliente */
    public String email; /** Dirección de correo del cliente */
    public boolean esVip; /** Indica si el cliente tiene beneficios especiales */

    /**
     * Constructor para crear un objeto Cliente con validación de datos
     * @param id Identificador único del cliente
     * @param nombre Nombre completo del cliente
     * @param dni DNI con validación con formato español (8 números + 1 letra)
     * @param email Email con un formato válido
     * @param esVip Indica si es VIP o no es VIP
     */
    public Cliente(int id, String nombre, String dni, String email, boolean esVip) {
        this.id = id;
        if(validarNombre(nombre)) this.nombre = nombre;
        if(validarDni(dni)) this.dni = dni;
        if(validarEmail(email)) this.email = email;
        this.esVip = esVip;
    }

    //Getters
    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public String getDni() {
        return dni;
    }
    public String getEmail() {
        return email;
    }
    public boolean isEsVip() {
        return esVip;
    }

    //Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setEsVip(boolean esVip) {
        this.esVip = esVip;
    }

    /**
     * Válida que el nombre cumple con lo mínimo establecido
     * @return true Si el nombre es válido
     * @throws IllegalArgumentException Si el nombre es nulo,
     * vacío o tiene menos de 3 caracteres
     */
    private boolean validarNombre(String nombre) {
        // Comprobamos que el nombre no sea nulo, esté vacío y tenga
        // al menos 3 caracteres eliminando espacios iniciales y finales
        if (nombre == null || nombre.trim().length() < 3) {
            throw new IllegalArgumentException("El nombre no es válido");
        }
        return true;
    }

    /**
     * Válida que el email este como el formato establecido
     * @return true Si el email es valido
     * @throws  IllegalArgumentException si el email no es valido
     */
    private boolean validarEmail(String email) {
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            throw new IllegalArgumentException("El email no es válido");
        }
        return true;
    }

    /**
     * @return true Si el DNI cumple con el formato
     * Válida que el DNI tenga el formato establecido
     * @throws IllegalArgumentException si el DNI no es valido
     */
    public boolean validarDni(String dni) {
        if (!dni.matches("[0-9]{8}[A-Z]")) {
            throw new IllegalArgumentException("El DNI no es válido");
        }
        return true;
    }

    /**
     * Representa la información del cliente
     */
    @Override
    public String toString() {
        return "|--------------------------|" +
                "\nCliente: " +
                "\n -ID: " + id +
                "\n -Nombre: " + nombre +
                "\n -DNI: " + dni +
                "\n -Email: " + email +
                "\n -Vip: " + esVip;
    }
}