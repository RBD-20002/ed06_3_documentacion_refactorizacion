package org.ed06.model;
import java.time.LocalDate;
import java.util.*;

/**
 * Clase que representa un hotel con sus habitaciones, clientes y reservas
 * Gestiona toda operación relacionada con el hotel
 */
public class Hotel {
    private final String nombre;
    private final String direccion;
    private final String telefono;

    private final Map<Integer,Cliente> clientes = new HashMap<>();
    private final List<Habitacion> habitaciones = new ArrayList<>();
    private final Map<Integer,List<Reserva>> reservasPorHabitacion = new HashMap<>();

    /**
     * Constructor para crear un objeto hotel
     * @param nombre Nombre del hotel
     * @param direccion Dirección del hotel
     * @param telefono Telefono del hotel
     */
    public Hotel(String nombre, String direccion, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    //|---- GESTIÓN DE HABITACIONES ----|

    /**
     * Registra una nueva habitación en el hotel
     * @param tipo Tipo de habitación (SIMPLE, DOBLE, SUITE, LITERA)
     * @param precioBase Precio base por noche de la habitación
     * @return La habitación creada
     */
    public Habitacion registrarHabitacion(TipoHabitacion tipo, double precioBase) {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de habitación no puede ser nulo");
        }
        if (precioBase <= 0) {
            throw new IllegalArgumentException("El precio base debe ser mayor que cero");
        }

        Habitacion habitacion = new Habitacion(habitaciones.size() + 1, tipo, precioBase, true);
        habitaciones.add(habitacion);
        reservasPorHabitacion.put(habitacion.getNumero(), new ArrayList<>());
        return habitacion;
    }

    /**
     * Registra múltiples habitaciones en el hotel
     * @param tipos Lista de los tipos de habitaciones
     * @param preciosBase Lista de precios correspondientes
     */
    public void registrarHabitaciones(List<TipoHabitacion> tipos, List<Double> preciosBase) {
        if (tipos.size() != preciosBase.size()) {
            throw new IllegalArgumentException("Las listas de tipos y precios deben tener el mismo tamaño");
        }
        for (int i = 0; i < tipos.size(); i++) {
            registrarHabitacion(tipos.get(i), preciosBase.get(i));
        }
    }

    /**
     * Lista todas las habitaciones del hotel
     *
     * @return
     */
    public List<Habitacion> listarHabitacionesDisponibles() {
        for(Habitacion habitacion : habitaciones) {
            if(habitacion.isDisponible()) {
                System.out.println("Habitación: " + habitacion.getNumero() +
                        " - Tipo: " + habitacion.getTipo() + " - Precio base: " + habitacion.getPrecioBase());
            }
        }
        return null;
    }

    /**
     * Obtiene una habitación por su número
     * @param numero Número de la habitación a buscar
     * @return La habitación o null si no existe
     */
    public Habitacion obtenerHabitacion(int numero) {
        for(Habitacion habitacion : habitaciones) {
            if(habitacion.getNumero() == numero) {
                return habitacion;
            }
        }
        return null;
    }

    //|---- GESTIÓN DE RESERVAS ----|

    /**
     * Realiza una reserva de habitación para un cliente
     * @param clienteId "ID" del cliente que realiza la reserva
     * @param tipo Tipo de habitación solicitada
     * @param fechaEntrada Fecha de inicio de la estadía
     * @param fechaSalida Fecha de fin de la estadía
     * @return Número de habitación asignada o código de error negativo
     */
    public int reservarHabitacion(int clienteId, TipoHabitacion tipo,
                                  LocalDate fechaEntrada, LocalDate fechaSalida) {
        if (habitaciones.isEmpty()) {
            System.out.println("No hay habitaciones en el hotel");
            return -1; // Código de error: no hay habitaciones
        }

        Cliente cliente = clientes.get(clienteId);
        if (cliente == null) {
            System.out.println("No existe el cliente con id " + clienteId);
            return -2; // Código de error: cliente no existe
        }

        if (fechaEntrada.isAfter(fechaSalida)) {
            System.out.println("La fecha de entrada es posterior a la fecha de salida");
            return -3; // Código de error: fechas inválidas
        }

        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getTipo() == tipo && habitacion.isDisponible()) {
                actualizarEstadoVipCliente(cliente);
                crearReserva(habitacion, cliente, fechaEntrada, fechaSalida);
                return habitacion.getNumero();
            }
        }

        System.out.println("No hay habitaciones disponibles del tipo " + tipo);
        return -4; // Código de error: no hay habitaciones disponibles
    }

    /**
     * Actualiza el estado VIP de un cliente que ha tenido más de 3 reservas
     * @param cliente Cliente a verificar
     */
    private void actualizarEstadoVipCliente(Cliente cliente) {
        if (!cliente.isEsVip() && contarReservasUltimoAnio(cliente) > 3) {
            cliente.setEsVip(true);
            System.out.println("Cliente " + cliente.getNombre() + " actualizado a VIP");
        }
    }

    /**
     * Cuenta las reservas hechas por un cliente en específico
     * @param cliente Cliente a verificar
     * @return Número de reservas recientes
     */
    private int contarReservasUltimoAnio(Cliente cliente) {
        int contador = 0;
        LocalDate haceUnAnio = LocalDate.now().minusYears(1);

        for (List<Reserva> reservas : reservasPorHabitacion.values()) {
            for (Reserva reserva : reservas) {
                if (Objects.equals(reserva.getCliente(), cliente)) {
                    if (reserva.getFechaInicio().isAfter(haceUnAnio)) {
                        contador++;
                    }
                }
            }
        }
        return contador;
    }

    /**
     * Crea una reserva y actualiza la habitación
     * @param habitacion Habitación a reservar
     * @param cliente Cliente que reserva
     * @param entrada  Fecha de entrada
     * @param salida Fecha de salida
     */
    private void crearReserva(Habitacion habitacion, Cliente cliente, LocalDate entrada, LocalDate salida) {
        int nuevoId = reservasPorHabitacion.values().stream().mapToInt(List::size).sum() + 1;
        Reserva reserva = new Reserva(nuevoId, habitacion, cliente, entrada, salida);
        reservasPorHabitacion.get(habitacion.getNumero()).add(reserva);
        habitacion.reservar();
    }

    /**
     * Lista todas las reservas del hotel
     *
     * @return
     */
    public Map<Integer, List<Reserva>> listarReservas() {
        reservasPorHabitacion.forEach((key, value) -> {
            System.out.println("Habitación #" + key);
            value.forEach(reserva -> System.out.println(
                "Reserva #" + reserva.getId() + " - Cliente: " + reserva.getCliente().nombre
                    + " - Fecha de entrada: " + reserva.getFechaInicio()
                    + " - Fecha de salida: " + reserva.getFechaFin()));
        });
        return null;
    }

    //|---- GESTIÓN DE CLIENTES ----|

    /**
     * Lista todos los clientes registrados en el sistema
     *
     * @return
     */
    public List<Cliente> listarClientes() {
        for(Cliente cliente : clientes.values()) {
            System.out.println("Cliente #" + cliente.id + " - Nombre: " +
                    cliente.nombre + " - DNI: " + cliente.dni + " - VIP: " + cliente.esVip);
        }
        return null;
    }

    /**
     * Registra un nuevo cliente en el sistema del hotel
     *
     * @param nombre Nombre del nuevo cliente
     * @param email  Email válido del cliente
     * @param dni    DNI válido con ek formato español
     * @param esVip  Indica si el cliente es VIP o si no es VIP
     * @return
     */
    public Cliente registrarCliente(String nombre, String email, String dni, boolean esVip) {
        Cliente cliente = new Cliente(clientes.size() + 1, nombre, dni, email, esVip);
        clientes.put(cliente.id, cliente);
        return cliente;
    }
}
