package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//BufferedReader, IOException, InputStreamReader: Te klasy są używane do odczytywania danych wejściowych od użytkownika.

public class Client {
    private ConnectionThread connectionThread; //private ConnectionThread connectionThread;: Pole, które przechowuje instancję ConnectionThread.

    public void start(String address, int port) { //Rozpoczyna działanie klienta, łącząc się z podanym adresem i portem.
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) { // BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)): Używane do odczytu danych wejściowych z konsoli.
            System.out.print("Enter your username: ");
            String username = reader.readLine(); //String username = reader.readLine();: Odczytuje nazwę użytkownika.

            connectionThread = new ConnectionThread(address, port); //connectionThread = new ConnectionThread(address, port);: Tworzy nową instancję ConnectionThread i uruchamia ją.
            connectionThread.start();
            connectionThread.login(username); //connectionThread.login(username);: Loguje użytkownika.

            String rawMessage;
            while ((rawMessage = reader.readLine()) != null) { //while ((rawMessage = reader.readLine()) != null): Odczytuje wiadomości od użytkownika.
                Message message = new Message();
                if(rawMessage.equals("/online")) { //if(rawMessage.equals("/online")): Jeśli wiadomość to "/online", tworzy wiadomość typu Request.
                    message = new Message(MessageType.Request, rawMessage);
                } else if(rawMessage.split(" ")[0].equals("/w")) { //else if(rawMessage.split(" ")[0].equals("/w")): Jeśli wiadomość zaczyna się od "/w", tworzy wiadomość typu Whisper.
                    message = new Message(MessageType.Whisper, rawMessage);
                } else { //else: W przeciwnym razie tworzy wiadomość typu Broadcast.
                    message = new Message(MessageType.Broadcast, rawMessage);
                }
                connectionThread.send(message); //connectionThread.send(message);: Wysyła wiadomość przez ConnectionThread.
            }
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        } finally {
            stop();
        }
    }
    public void stop() { //public void stop(): Zatrzymuje klienta, zamykając połączenie.
        if (connectionThread != null && connectionThread.isAlive()) { //if (connectionThread != null && connectionThread.isAlive()): Sprawdza, czy ConnectionThread jest aktywne.
            try {
                connectionThread.client.close(); //connectionThread.client.close();: Zamyka połączenie.
            } catch (IOException e) {
                System.err.println("Error closing connection thread: " + e.getMessage());
            }
        }
    }
}
