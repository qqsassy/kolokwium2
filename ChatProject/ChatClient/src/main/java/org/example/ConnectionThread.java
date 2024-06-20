package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//com.fasterxml.jackson.core.JsonProcessingException, com.fasterxml.jackson.databind.ObjectMapper: Klasy do serializacji i deserializacji JSON.

import java.io.*;
import java.net.Socket;
//java.io.*, java.net.Socket: Klasy do obsługi wejścia/wyjścia i połączeń sieciowych.

public class ConnectionThread extends Thread {
    Socket client; //Socket client;: Gniazdo sieciowe.
    PrintWriter writer; //PrintWriter writer;: Obiekt do wysyłania danych.

    public ConnectionThread(String address, int port) throws IOException { //public ConnectionThread(String address, int port) throws IOException: Tworzy nowe połączenie z serwerem.
        client = new Socket(address, port); //client = new Socket(address, port);: Łączy się z serwerem na podanym adresie i porcie.
    }

    @Override
    public void run() { //@Override public void run(): Główna metoda wątku, która odbiera wiadomości z serwera.
        try (InputStream input = client.getInputStream(); //try (InputStream input = client.getInputStream(); OutputStream output = client.getOutputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(input))): Tworzy strumienie wejścia i wyjścia.
             OutputStream output = client.getOutputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {

            writer = new PrintWriter(output, true); //writer = new PrintWriter(output, true);: Inicjalizuje PrintWriter do wysyłania danych.

            String rawMessage;

            while ((rawMessage = reader.readLine()) != null) { //while ((rawMessage = reader.readLine()) != null): Odczytuje wiadomości od serwera.
                Message message = new ObjectMapper().readValue(rawMessage, Message.class); //Message message = new ObjectMapper().readValue(rawMessage, Message.class);: Deserializuje wiadomość z formatu JSON.
                System.out.println(message.content); //System.out.println(message.content);: Wyświetla treść wiadomości.
            }

        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }

    public void send(Message message) throws JsonProcessingException { //public void send(Message message) throws JsonProcessingException: Wysyła wiadomość do serwera.
        String rawMessage = new ObjectMapper().writeValueAsString(message); //String rawMessage = new ObjectMapper().writeValueAsString(message);: Serializuje wiadomość do formatu JSON.
        writer.println(rawMessage); //writer.println(rawMessage);: Wysyła wiadomość.
    }

    public void login(String login) throws JsonProcessingException { //public void login(String login) throws JsonProcessingException: Loguje użytkownika.
        Message message = new Message(MessageType.Login, login); //Message message = new Message(MessageType.Login, login);: Tworzy wiadomość logowania.
        send(message); //send(message);: Wysyła wiadomość.
    }
}
