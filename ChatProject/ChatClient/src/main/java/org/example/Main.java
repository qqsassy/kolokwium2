package org.example;

import java.io.IOException;
//java.io.IOException: Klasa do obsługi wyjątków wejścia/wyjścia.

public class Main {
    public static void main(String[] args) { //public static void main(String[] args): Główna metoda uruchamiająca aplikację.
        Client client = new Client(); //Client client = new Client();: Tworzy instancję Client.
        client.start("localhost", 8000); //client.start("localhost", 8000);: Uruchamia klienta, łącząc się z serwerem na localhost i porcie 8000.
    }
}

//Podsumowanie:
//Main.java jest punktem startowym aplikacji.
//Client.java zarządza interakcją użytkownika i połączeniem sieciowym.
//ConnectionThread.java obsługuje komunikację z serwerem.
//Message.java definiuje strukturę wiadomości.
//MessageType.java określa typy wiadomości.