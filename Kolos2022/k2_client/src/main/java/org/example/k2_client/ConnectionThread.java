package org.example.k2_client;

import java.io.*;
import java.net.Socket;

public class ConnectionThread extends Thread {
    Socket socket; // Gniazdo sieciowe do komunikacji z serwerem
    PrintWriter writer; // Obiekt do wysyłania danych do serwera

    // Konstruktor inicjujący połączenie z serwerem
    public ConnectionThread(String address, int port) throws IOException {
        socket = new Socket(address, port); // Tworzy nowe połączenie z serwerem na podanym adresie i porcie
    }

    @Override
    public void run() {
        try (InputStream input = socket.getInputStream();
             OutputStream output = socket.getOutputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {

            writer = new PrintWriter(output, true); // Inicjalizuje PrintWriter do wysyłania danych

            String rawMessage;

            // Pętla do odczytywania wiadomości z serwera
            while ((rawMessage = reader.readLine()) != null) {
                ClientReceiver.receiveWord(rawMessage); // Przekazuje odebraną wiadomość do ClientReceiver
            }

        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage()); // Obsługuje błędy połączenia
        }
    }
}

//Podsumowanie:
//Deklaracja klasy:
//
//public class ConnectionThread extends Thread: Klasa ConnectionThread dziedziczy po klasie Thread, co pozwala na uruchomienie jej w oddzielnym wątku.
//Pola:
//
//Socket socket: Gniazdo sieciowe do komunikacji z serwerem.
//PrintWriter writer: Obiekt do wysyłania danych do serwera.
//Konstruktor:
//
//public ConnectionThread(String address, int port) throws IOException: Konstruktor inicjujący połączenie z serwerem na podanym adresie i porcie.
//socket = new Socket(address, port): Tworzy nowe połączenie z serwerem.
//Metoda run:
//
//@Override public void run(): Główna metoda wątku, która odbiera wiadomości od serwera i przekazuje je do ClientReceiver.
//try (InputStream input = socket.getInputStream(); OutputStream output = socket.getOutputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(input))): Tworzy strumienie wejścia i wyjścia oraz BufferedReader do odczytu danych.
//writer = new PrintWriter(output, true): Inicjalizuje PrintWriter do wysyłania danych do serwera.
//while ((rawMessage = reader.readLine()) != null): Pętla do odczytywania wiadomości z serwera.
//ClientReceiver.receiveWord(rawMessage): Przekazuje odebraną wiadomość do ClientReceiver.
//catch (IOException e): Obsługuje błędy połączenia.
//System.err.println("Connection error: " + e.getMessage()): Wypisuje komunikat błędu na standardowe wyjście błędów.

//Opis działania:
//ConnectionThread: Klasa ta reprezentuje wątek połączenia z serwerem. Odbiera wiadomości od serwera i przekazuje je do ClientReceiver.
//Strumienie wejścia i wyjścia: Używane do komunikacji z serwerem.
//Pętla odbioru wiadomości: Odczytuje wiadomości z serwera i przekazuje je do ClientReceiver do dalszego przetwarzania.