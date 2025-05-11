package org.ed06.app;
import org.ed06.model.*;

/** Main limpio **/
public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel(
                "El Mirador",
                "Calle Entornos de Desarrollo 6",
                "123456789"
        );
        new HotelCLI(hotel).inicio();
    }
}