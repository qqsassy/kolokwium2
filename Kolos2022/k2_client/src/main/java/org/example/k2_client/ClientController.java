package org.example.k2_client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClientController {

    @FXML
    public Label wordCountLabel; // Etykieta do wyświetlania liczby słów
    @FXML
    public ListView wordList; // Lista do wyświetlania słów
    @FXML
    public TextField filterField; // Pole tekstowe do filtrowania słów

    // Konstruktor kontrolera
    public ClientController() {
        ClientReceiver.controller = this; // Ustawienie instancji kontrolera w ClientReceiver
    }

    List<Word> listOfWords = new ArrayList<>(); // Lista przechowująca słowa

    // Metoda wywoływana po otrzymaniu słowa
    public void onWordReceived(String word) {
        listOfWords.add(new Word(LocalTime.now(), word)); // Dodanie nowego słowa do listy
        Platform.runLater(() -> {
            wordCountLabel.setText(Integer.toString(listOfWords.size())); // Aktualizacja etykiety z liczbą słów
            update(); // Aktualizacja listy słów
        });
    }

    // Metoda do aktualizacji listy słów
    public void update() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss ");
        wordList.setItems(FXCollections.observableArrayList(listOfWords.stream()
                .filter((word) -> word.getWord().startsWith(filterField.getText())) // Filtrowanie słów na podstawie pola tekstowego
                .sorted(Comparator.comparing(word -> word.getWord())) // Sortowanie słów alfabetycznie
                .map((item) -> item.getTime().format(formatter) + item.getWord()) // Mapowanie słów do formatu czasowego
                .toList()
        ));
    }

    // Metoda wywoływana po wciśnięciu klawisza Enter
    public void onEnter() {
        Platform.runLater(this::update); // Aktualizacja listy słów w wątku JavaFX
    }
}

//Podsumowanie:
//Imports: Importuje klasy JavaFX i inne potrzebne klasy.
//Pola:
//wordCountLabel: Etykieta do wyświetlania liczby słów.
//wordList: Lista do wyświetlania słów.
//filterField: Pole tekstowe do filtrowania słów.
//Konstruktor: Ustawia instancję kontrolera w ClientReceiver.
//Metody:
//onWordReceived(String word): Dodaje nowe słowo do listy i aktualizuje interfejs użytkownika.
//update(): Filtrowanie, sortowanie i aktualizacja listy słów.
//onEnter(): Aktualizuje listę słów po wciśnięciu klawisza Enter.