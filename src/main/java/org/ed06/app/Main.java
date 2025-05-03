package org.ed06.app;
import org.ed06.model.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Clase principal que maneja la interfaz de usuario del sistema de hotel, controla el flujo principal de la aplicación y la interacción con el usuario.
 */
public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Hotel HOTEL = new Hotel("El mirador", "Calle Entornos de Desarrollo 6", "123456789");

    // Opciones del menú
    private static final int SALIR = 0;
    private static final int REGISTRAR_HABITACION = 1;
    private static final int LISTAR_HABITACIONES = 2;
    private static final int RESERVAR_HABITACION = 3;
    private static final int LISTAR_RESERVAS = 4;
    private static final int LISTAR_CLIENTES = 5;
    private static final int REGISTRAR_CLIENTE = 6;

    public static void main(String[] args) {
        try {
            inicializarDatosPrueba();
            ejecutarMenuPrincipal();
        } finally {
            SCANNER.close();
        }
    }

    /**
     * Inicializa datos de prueba para el hotel.
     */
    private static void inicializarDatosPrueba() {
        HOTEL.registrarHabitacion(Habitacion.TipoHabitacion.SIMPLE, 50.0);
        HOTEL.registrarHabitacion(Habitacion.TipoHabitacion.DOBLE, 80.0);
        HOTEL.registrarHabitacion(Habitacion.TipoHabitacion.SUITE, 120.0);
        HOTEL.registrarHabitacion(Habitacion.TipoHabitacion.LITERAS, 200.0);
    }

    /**
     * Control principal del menú de la aplicación.
     */
    private static void ejecutarMenuPrincipal() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción (0-6): ");
            procesarOpcion(opcion);
        } while (opcion != SALIR);
    }

    /**
     * Muestra las opciones disponibles del menú.
     */
    private static void mostrarMenu() {
        System.out.println("\n=== SISTEMA DE GESTIÓN HOTELERA ===");
        System.out.println("1. Registrar habitación");
        System.out.println("2. Listar habitaciones disponibles");
        System.out.println("3. Reservar habitación");
        System.out.println("4. Listar reservas");
        System.out.println("5. Listar clientes");
        System.out.println("6. Registrar cliente");
        System.out.println("0. Salir");
    }

    /**
     * Procesa la opción seleccionada por el usuario.
     * @param opcion Número de opción seleccionada
     */
    private static void procesarOpcion(int opcion) {
        switch (opcion) {
            case REGISTRAR_HABITACION -> registrarHabitacion();
            case LISTAR_HABITACIONES -> HOTEL.listarHabitacionesDisponibles();
            case RESERVAR_HABITACION -> reservarHabitacion();
            case LISTAR_RESERVAS -> HOTEL.listarReservas();
            case LISTAR_CLIENTES -> HOTEL.listarClientes();
            case REGISTRAR_CLIENTE -> registrarCliente();
            case SALIR -> System.out.println("Adíos");
            default -> System.out.println("Opción no válida");
        }
    }

    /**
     * Registra una nueva habitación en el sistema.
     */
    private static void registrarHabitacion() {
        System.out.println("|--- REGISTRO DE HABITACIÓN ---|");
        Habitacion.TipoHabitacion tipo = leerTipoHabitacion();
        double precio = leerDouble();
        HOTEL.registrarHabitacion(tipo, precio);
        System.out.println("Habitación registrada exitosamente");
    }

    /**
     * Realiza el proceso de reserva de una habitación.
     */
    private static void reservarHabitacion() {
        System.out.println("|--- RESERVA DE HABITACIÓN ---|");
        int clienteId = leerEntero("Ingrese ID del cliente: ");
        Habitacion.TipoHabitacion tipo = leerTipoHabitacion();
        LocalDate fechaEntrada = leerFecha("Ingrese fecha de entrada (yyyy-mm-dd): ");
        LocalDate fechaSalida = leerFecha("Ingrese fecha de salida (yyyy-mm-dd): ");
        if (fechaSalida.isBefore(fechaEntrada)) {
            System.out.println("Error: La fecha de salida debe ser posterior a la de entrada");
            return;
        }

        int numHabitacion = HOTEL.reservarHabitacion(clienteId, tipo, fechaEntrada, fechaSalida);

        if (numHabitacion > 0) {
            Habitacion habitacion = HOTEL.obtenerHabitacion(numHabitacion);
            System.out.println("Reserva exitosa:");
            System.out.println(habitacion);
        }
    }

    /**
     * Registra un nuevo cliente en el sistema.
     */
    private static void registrarCliente() {
        System.out.println("\n--- REGISTRO DE CLIENTE ---");
        String nombre = leerTexto("Ingrese nombre completo: ");
        String dni = leerTexto("Ingrese DNI (8 números + letra): ");
        String email = leerTexto("Ingrese email: ");
        boolean esVip = leerBoolean();

        try {
            HOTEL.registrarCliente(nombre, email, dni, esVip);
            System.out.println("Cliente registrado exitosamente");
        } catch (IllegalArgumentException e) {
            System.out.println("Error fallo inesperado");
        }
    }

    // ==================== MÉTODOS AUXILIARES SEGUROS ====================

    /**
     * Lee un número entero válido desde la entrada
     * @param mensaje Texto a mostrar al usuario
     * @return Número entero válido ingresado por el usuario
     */
    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(SCANNER.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número entero válido");
            }
        }
    }

    /**
     * Lee un double validado dentro de un rango.
     */
    private static double leerDouble() {
        while (true) {
            try {
                System.out.print("Ingrese el precio base: ");
                double valor = Double.parseDouble(SCANNER.nextLine());
                if (valor >= 1.0 && valor <= 10000.0) {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Dato introducido invalido");
            }
        }
    }

    /**
     * Lee texto validando que no esté vacío.
     */
    private static String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String input = SCANNER.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Error: Dato introducido invalido");
        }
    }

    /**
     * Lee una fecha validando el formato.
     */
    private static LocalDate leerFecha(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return LocalDate.parse(SCANNER.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Error: Dato introducido invalido");
            }
        }
    }

    /**
     * Lee un booleano a partir de respuesta sí/no.
     */
    private static boolean leerBoolean() {
        while (true) {
            String input = leerTexto("¿Es cliente VIP? (si/no): ").toLowerCase();
            if (input.equals("si") || input.equals("sí") || input.equals("yes")) {
                return true;
            }
            if (input.equals("no")) {
                return false;
            }
            System.out.println("Error: Dato introducido invalido");
        }
    }

    /**
     * Lee un tipo de habitación validado.
     */
    private static Habitacion.TipoHabitacion leerTipoHabitacion() {
        while (true) {
            try {
                System.out.println("Tipos disponibles:");
                for (Habitacion.TipoHabitacion tipo : Habitacion.TipoHabitacion.values()) {
                    System.out.printf("- %s (Capacidad: %d)%n", tipo.name(), tipo.getCapacidadMaxima());
                }
                System.out.print("Seleccione tipo: ");
                return Habitacion.TipoHabitacion.valueOf(SCANNER.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Dato introducido invalido");
            }
        }
    }
}