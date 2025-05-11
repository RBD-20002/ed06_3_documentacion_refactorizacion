package org.ed06.app;
import org.ed06.model.*;

/** Main limpio **/
public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel("Sharenton", "Calle Palmeras 159", "88888888"
        );
        new HotelCLI(hotel).inicio();
    }
}