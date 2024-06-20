package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
//com.fasterxml.jackson.core.JsonProcessingException: Klasa do serializacji i deserializacji JSON.

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
//java.io.*, java.net.ServerSocket, java.net.Socket: Klasy do obsługi wejścia/wyjścia i połączeń sieciowych.
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
//java.util.List, java.util.concurrent.CopyOnWriteArrayList: Klasy do obsługi listy klientów.

public class Server {
    private ServerSocket socket; //private ServerSocket socket;: Gniazdo serwera.
    private List<ClientThread> clients = new CopyOnWriteArrayList<>(); //private List<ClientThread> clients = new CopyOnWriteArrayList<>();: Lista klientów.

    public Server(int port) throws IOException { //public Server(int port) throws IOException: Inicjalizuje serwer na podanym porcie.
        socket = new ServerSocket(port);
    }

    public void listen() throws IOException { //public void listen() throws IOException: Nasłuchuje połączeń od klientów.
        while (true) { //while (true): Pętla nieskończona, która akceptuje nowe połączenia.
            Socket client = socket.accept(); //Socket client = socket.accept();: Akceptuje nowe połączenie.
            ClientThread thread = new ClientThread(client, this); //ClientThread thread = new ClientThread(client, this);: Tworzy nowy wątek dla klienta.
            clients.add(thread); //clients.add(thread);: Dodaje wątek do listy klientów.
            thread.start(); //thread.start();: Uruchamia wątek.
        }
    }

    public synchronized void broadcast(Message message) throws JsonProcessingException { //public synchronized void broadcast(Message message) throws JsonProcessingException: Wysyła wiadomość do wszystkich klientów.
        for (ClientThread client : clients) { //for (ClientThread client : clients): Iteruje przez wszystkich klientów.
            client.send(message); //client.send(message);: Wysyła wiadomość.
        }
    }

    public void removeClient(ClientThread client) { //public void removeClient(ClientThread client): Usuwa klienta z listy.
        clients.remove(client); //clients.remove(client);: Usuwa klienta z listy.
    }

    public synchronized void online(ClientThread client) throws JsonProcessingException { //public synchronized void online(ClientThread client) throws JsonProcessingException: Wysyła listę online do klienta.
        StringBuilder builder = new StringBuilder(); //StringBuilder builder = new StringBuilder();: Tworzy nowy StringBuilder.
        for(ClientThread c : clients) { //for(ClientThread c : clients): Iteruje przez wszystkich klientów.
            builder.append(c.username).append("\n"); //builder.append(c.username).append("\n");: Dodaje nazwę użytkownika do listy.
        }
        client.send(new Message(MessageType.Request, builder.toString())); //client.send(new Message(MessageType.Request, builder.toString()));: Wysyła listę do klienta.
    }

    public synchronized void whisper(Message message, String destUsername) throws JsonProcessingException { //public synchronized void whisper(Message message, String destUsername) throws JsonProcessingException: Wysyła prywatną wiadomość do określonego użytkownika.
        for(ClientThread c : clients) { //for(ClientThread c : clients): Iteruje przez wszystkich klientów.
            if(c.username.equals(destUsername)) { //if(c.username.equals(destUsername)): Sprawdza, czy nazwa użytkownika się zgadza.
                c.send(message); //c.send(message);: Wysyła wiadomość.
            }
        }
    }
}
