package org.example.k2_client;

public class ClientReceiver {
    public static ClientController controller; // Statyczne pole do przechowywania instancji kontrolera
    public static ConnectionThread thread; // Statyczne pole do przechowywania instancji wątku połączenia

    // Metoda do odbierania słowa i przekazywania go do kontrolera
    public static void receiveWord(String word) {
        controller.onWordReceived(word); // Wywołuje metodę onWordReceived w kontrolerze
    }
}

//Podsumowanie:
//Deklaracja klasy:
//
//public class ClientReceiver: Klasa ClientReceiver zawiera metody statyczne do odbierania słów i zarządzania instancjami kontrolera oraz wątku połączenia.
//Pola:
//
//public static ClientController controller: Statyczne pole do przechowywania instancji kontrolera.
//public static ConnectionThread thread: Statyczne pole do przechowywania instancji wątku połączenia.
//Metoda receiveWord:
//
//public static void receiveWord(String word): Statyczna metoda do odbierania słowa i przekazywania go do kontrolera.
//controller.onWordReceived(word): Wywołuje metodę onWordReceived w kontrolerze, przekazując odebrane słowo.

//Opis działania:
//ClientReceiver: Klasa ta pełni rolę pośrednika, odbierając słowa i przekazując je do kontrolera (ClientController).
//Statyczne pola: Pozwalają na łatwe zarządzanie instancjami kontrolera i wątku połączenia z dowolnego miejsca w aplikacji.
//Statyczna metoda receiveWord: Umożliwia odbieranie słów i przekazywanie ich do kontrolera w celu dalszego przetwarzania.