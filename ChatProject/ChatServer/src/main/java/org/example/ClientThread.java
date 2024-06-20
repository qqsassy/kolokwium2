package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//com.fasterxml.jackson.core.JsonProcessingException, com.fasterxml.jackson.databind.ObjectMapper: Klasy do serializacji i deserializacji JSON.

import java.io.*;
import java.net.Socket;
//java.io.*, java.net.Socket: Klasy do obsługi wejścia/wyjścia i połączeń sieciowych.

public class ClientThread extends Thread {
    private Socket client; //private Socket client;: Gniazdo sieciowe.
    private Server server; //private Server server;: Odniesienie do serwera.
    private PrintWriter writer; //private PrintWriter writer;: Obiekt do wysyłania danych.
    String username; //String username;: Nazwa użytkownika.

    //Konstruktor
    public ClientThread(Socket socket, Server server) { //public ClientThread(Socket socket, Server server): Inicjalizuje połączenie z klientem i serwerem.
        this.client = socket;
        this.server = server;
    }

    @Override
    public void run() { //@Override public void run(): Główna metoda wątku, która odbiera wiadomości od klienta.
        try (InputStream input = client.getInputStream();
             OutputStream output = client.getOutputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {

            writer = new PrintWriter(output, true);

            String rawMessage;

            while ((rawMessage = reader.readLine()) != null) { //while ((rawMessage = reader.readLine()) != null): Odczytuje wiadomości od klienta.
                Message message = new ObjectMapper().readValue(rawMessage, Message.class); //Message message = new ObjectMapper().readValue(rawMessage, Message.class);: Deserializuje wiadomość z formatu JSON.

                switch (message.type) { //switch (message.type): Obsługuje różne typy wiadomości:
                    case Login -> { //Login: Loguje użytkownika i informuje innych.
                        username = message.content;
                        server.broadcast(new Message(MessageType.Broadcast, username + " has joined the chat"));
                    }
                    case Broadcast -> { //Broadcast: Przesyła wiadomość do wszystkich.
                        String formattedMessage = "[" + username + "] " + message.content;
                        server.broadcast(new Message(MessageType.Broadcast, formattedMessage));
                    }
                    case Logout -> { //Logout: Wylogowuje użytkownika i informuje innych.
                        server.broadcast(new Message(MessageType.Broadcast, username + " has left the chat"));
                        server.removeClient(this);
                        client.close();
                        return; // Exit the thread
                    }
                    case Request -> server.online(this); //Request: Odpowiada na żądanie listy online.
                    case Whisper -> { //Whisper: Wysyła prywatną wiadomość do innego użytkownika.
                        String destUsername = rawMessage.split(" ")[1];
                        String actualMessage = rawMessage.split(" ", 3)[2];
                        String formattedMessage = "[" + username + "] " + actualMessage;
                        server.whisper(new Message(MessageType.Whisper, formattedMessage), destUsername);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            server.removeClient(this);
            if (username != null) {
                try {
                    server.broadcast(new Message(MessageType.Broadcast, username + " has left the chat"));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(Message message) throws JsonProcessingException { //public void send(Message message) throws JsonProcessingException: Wysyła wiadomość do klienta.
        String rawMessage = new ObjectMapper().writeValueAsString(message); //String rawMessage = new ObjectMapper().writeValueAsString(message);: Serializuje wiadomość do formatu JSON.
        writer.println(rawMessage); //writer.println(rawMessage);: Wysyła wiadomość.
    }
}
