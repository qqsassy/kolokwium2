package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) { //public static void main(String[] args): Główna metoda uruchamiająca serwer.
        try {
            Server server = new Server(8000); //Server server = new Server(8000);: Tworzy instancję serwera na porcie 8000.
            server.listen(); //server.listen();: Serwer nasłuchuje połączeń od klientów.
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

//Podsumowanie:
//ClientThread.java obsługuje komunikację z poszczególnymi klientami, odbiera wiadomości i odpowiednio je przetwarza.
//Main.java uruchamia serwer.
//Message.java definiuje strukturę wiadomości.
//MessageType.java określa typy wiadomości.
//Server.java zarządza klientami i obsługuje logikę serwera, w tym nadawanie wiadomości, usuwanie klientów i obsługę prywatnych wiadomości.