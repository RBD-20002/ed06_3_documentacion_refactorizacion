package org.ed06.model;

/** Clase para mostrar Menus **/
public class InfoMenu implements Menu {

    @Override
    public void MenuPrincipal() {
        System.out.println("""
                |------GESTION HOTEL------| 
                |1. Menú de habitaciones  |
                |2. Menú de reservas      |
                |3. Menú de clientes      |
                |4. Salir                 |
                """);
    }

    @Override
    public void MenuHabitaciones() {
        System.out.println("""
                |---MENU HABITACIONES---|
                |1. Registrar habitación|
                |2. Listar disponibles  |
                |3. Volver atras        |
                """);
    }

    @Override
    public void MenuReservas() {
        System.out.println("""
                |---MENU DE RESERVAS---|
                |1. Hacer reserva      |
                |2. Listar reservas    |
                |3. Volver atras       |
                """);
    }

    @Override
    public void MenuClientes() {
        System.out.println("""
                |----MENU CLIENTES----|
                1. Registrar cliente  |
                2. Listar clientes    |
                3. Volver atras       |
                """);
    }
}