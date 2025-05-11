package org.ed06.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Clase que maneja la interfaz de línea de comandos (CLI) para la gestión integral del hotel.
 * Proporciona menús interactivos para gestionar habitaciones, reservas y clientes.
 */
public class HotelCLI {
    private final Hotel hotel;
    private final InfoMenu muestra = new InfoMenu();

    // Componentes para entrada de datos
    private final DatoEntero inMenuPrincipal = new DatoEntero("opción principal", 1, 4);
    private final DatoEntero inMenuHabitaciones = new DatoEntero("opción habitaciones", 1, 3);
    private final DatoEntero inMenuReservas = new DatoEntero("opción reservas", 1, 3);
    private final DatoEntero inMenuClientes = new DatoEntero("opción clientes", 1, 3);
    private final DatoEnum<TipoHabitacion> inTipo = new DatoEnum<>(TipoHabitacion.class);
    private final DatoDouble inPrecio = new DatoDouble("precio base", 0.0, Double.MAX_VALUE);
    private final DatoFecha inFecha = new DatoFecha("fecha", "yyyy-MM-dd");
    private final DatoTexto inTexto = new DatoTexto("texto", false);
    private final DatoEntero inIdCliente = new DatoEntero("ID de cliente", 1, Integer.MAX_VALUE);

    /**
     * Constructor que inicializa la CLI con una instancia del hotel.
     * @param hotel Instancia del hotel a gestionar
     * @throws IllegalArgumentException si el hotel es null
     */
    public HotelCLI(Hotel hotel) {
        if (hotel == null) {
            throw new IllegalArgumentException("El hotel no puede ser nulo");
        }
        this.hotel = hotel;
    }

    /**
     * Inicia la interfaz de línea de comandos mostrando el menú principal.
     * El método se ejecuta en bucle hasta que el usuario seleccione la opción de salir.
     */
    public void inicio() {
        int opcion;
        do {
            muestra.MenuPrincipal();
            opcion = inMenuPrincipal.leer("Seleccione una opción (1-4):");
            switchMenuPrincipal(opcion);
        } while (opcion != 4);
    }

    /**
     * Gestiona el menú principal y redirige a las diferentes funcionalidades.
     * @param opcion Opción seleccionada por el usuario
     */
    private void switchMenuPrincipal(int opcion) {
        switch (opcion) {
            case 1 -> gestionarHabitaciones();
            case 2 -> gestionarReservas();
            case 3 -> gestionarClientes();
            case 4 -> System.out.println("Saliendo del programa.");
            default -> System.out.println("Opción no válida.");
        }
    }

    /**
     * Muestra y gestiona el menú de habitaciones.
     */
    private void gestionarHabitaciones() {
        int opcion;
        do {
            muestra.MenuHabitaciones();
            opcion = inMenuHabitaciones.leer("Seleccione una opción (1-3):");
            switchMenuHabitaciones(opcion);
        } while (opcion != 3);
    }

    /**
     * Procesa las opciones del menú de habitaciones.
     * @param opcion Opción seleccionada por el usuario
     */
    private void switchMenuHabitaciones(int opcion) {
        switch (opcion) {
            case 1 -> registrarNuevaHabitacion();
            case 2 -> listarHabitacionesDisponibles();
            case 3 -> System.out.println("Volviendo al menú principal...");
            default -> System.out.println("Opción no válida.");
        }
    }

    /**
     * Registra una nueva habitación en el sistema.
     */
    private void registrarNuevaHabitacion() {
        TipoHabitacion tipo = inTipo.leer("Tipo (SIMPLE, DOBLE, SUITE, LITERAS):");
        double precio = inPrecio.leer("Precio base:");
        Habitacion nuevaHabitacion = hotel.registrarHabitacion(tipo, precio);
        System.out.printf("Habitación %d registrada exitosamente.%n", nuevaHabitacion.getNumero());
    }

    /**
     * Lista todas las habitaciones disponibles.
     */
    private void listarHabitacionesDisponibles() {
        List<Habitacion> disponibles = hotel.listarHabitacionesDisponibles();
        if (disponibles.isEmpty()) {
            System.out.println("No hay habitaciones disponibles.");
        } else {
            disponibles.forEach(hab -> System.out.printf(
                    "Habitación: %d | Tipo: %s | Precio: %.2f%n",
                    hab.getNumero(), hab.getTipo(), hab.getPrecioBase()));
        }
    }

    /**
     * Muestra y gestiona el menú de reservas.
     */
    private void gestionarReservas() {
        int opcion;
        do {
            muestra.MenuReservas();
            opcion = inMenuReservas.leer("Seleccione una opción (1-3):");
            switchMenuReservas(opcion);
        } while (opcion != 3);
    }

    /**
     * Procesa las opciones del menú de reservas.
     * @param opcion Opción seleccionada por el usuario
     */
    private void switchMenuReservas(int opcion) {
        switch (opcion) {
            case 1 -> crearNuevaReserva();
            case 2 -> listarTodasLasReservas();
            case 3 -> System.out.println("Volviendo al menú principal.");
            default -> System.out.println("Opción no válida.");
        }
    }

    /**
     * Crea una nueva reserva en el sistema.
     */
    private void crearNuevaReserva() {
        try {
            int clienteId = inIdCliente.leer("ID del cliente:");
            TipoHabitacion tipo = inTipo.leer("Tipo de habitación (SIMPLE, DOBLE, SUITE, LITERAS):");
            LocalDate entrada = inFecha.leer("Fecha de entrada (yyyy-MM-dd):");
            LocalDate salida = inFecha.leer("Fecha de salida (yyyy-MM-dd):");

            int numHabitacion = hotel.reservarHabitacion(clienteId, tipo, entrada, salida);
            System.out.printf("Reserva realizada. Habitación asignada: %d%n", numHabitacion);
        } catch (Exception e) {
            System.out.println("Error al crear reserva: " + e.getMessage());
        }
    }

    /**
     * Lista todas las reservas existentes.
     */
    private void listarTodasLasReservas() {
        Map<Integer, List<Reserva>> reservas = hotel.listarReservas();
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
        } else {
            reservas.forEach((numHab, listaReservas) -> {
                System.out.printf("%nHabitación %d:%n", numHab);
                listaReservas.forEach(res -> System.out.printf(
                        "  Reserva #%d: %s a %s | Cliente: %s (ID: %d)%n",
                        res.getId(), res.getFechaInicio(), res.getFechaFin(),
                        res.getCliente().getNombre(), res.getCliente().getId()));
            });
        }
    }

    /**
     * Muestra y gestiona el menú de clientes.
     */
    private void gestionarClientes() {
        int opcion;
        do {
            muestra.MenuClientes();
            opcion = inMenuClientes.leer("Seleccione una opción (1-3):");
            switchMenuClientes(opcion);
        } while (opcion != 3);
    }

    /**
     * Procesa las opciones del menú de clientes.
     * @param opcion Opción seleccionada por el usuario
     */
    private void switchMenuClientes(int opcion) {
        switch (opcion) {
            case 1 -> registrarNuevoCliente();
            case 2 -> listarTodosLosClientes();
            case 3 -> System.out.println("Volviendo al menú principal...");
            default -> System.out.println("Opción no válida.");
        }
    }

    /**
     * Registra un nuevo cliente en el sistema.
     */
    private void registrarNuevoCliente() {
        try {
            String nombre = inTexto.leer("Nombre del cliente:");
            String email = inTexto.leer("Email del cliente:");
            String dni = inTexto.leer("DNI:");
            boolean vip = inTexto.leer("¿Es VIP? (si/no):").equalsIgnoreCase("si");

            Cliente cliente = hotel.registrarCliente(nombre, email, dni, vip);
            System.out.printf("Cliente registrado con ID: %d%n", cliente.getId());
        } catch (Exception e) {
            System.out.println("Error al registrar cliente: " + e.getMessage());
        }
    }

    /**
     * Lista todos los clientes registrados.
     */
    private void listarTodosLosClientes() {
        List<Cliente> clientes = hotel.listarClientes();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            clientes.forEach(cli -> System.out.printf(
                    "ID: %d | Nombre: %s | DNI: %s | %s%n",
                    cli.getId(), cli.getNombre(), cli.getDni(),
                    cli.isEsVip() ? "VIP" : "No VIP"));
        }
    }
}