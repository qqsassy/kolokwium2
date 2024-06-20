package org.example.k2_client;

import java.time.LocalTime;

public class Word {
    private LocalTime time; // Pole przechowujące czas otrzymania słowa
    private String word; // Pole przechowujące słowo

    // Konstruktor inicjujący pola time i word
    public Word(LocalTime time, String word) {
        this.time = time;
        this.word = word;
    }

    // Metoda ustawiająca czas otrzymania słowa
    public void setTime(LocalTime time) {
        this.time = time;
    }

    // Metoda ustawiająca słowo
    public void setWord(String word) {
        this.word = word;
    }

    // Metoda zwracająca czas otrzymania słowa
    public LocalTime getTime() {
        return time;
    }

    // Metoda zwracająca słowo
    public String getWord() {
        return word;
    }
}

//Podsumowanie:
//Deklaracja klasy:
//
//public class Word: Klasa Word przechowuje informacje o słowie i czasie jego otrzymania.
//Pola:
//
//private LocalTime time: Pole przechowujące czas otrzymania słowa.
//private String word: Pole przechowujące słowo.
//Konstruktor:
//
//public Word(LocalTime time, String word): Konstruktor inicjalizujący pola time i word.
//this.time = time: Inicjalizuje pole time.
//this.word = word: Inicjalizuje pole word.
//Metody ustawiające (settery):
//
//public void setTime(LocalTime time): Ustawia czas otrzymania słowa.
//this.time = time: Przypisuje wartość do pola time.
//public void setWord(String word): Ustawia słowo.
//this.word = word: Przypisuje wartość do pola word.
//Metody zwracające (gettery):
//
//public LocalTime getTime(): Zwraca czas otrzymania słowa.
//return time: Zwraca wartość pola time.
//public String getWord(): Zwraca słowo.
//return word: Zwraca wartość pola word.

//Opis działania:
//Word: Klasa ta służy do przechowywania słów wraz z czasem ich otrzymania. Umożliwia ustawianie i pobieranie tych informacji za pomocą metod ustawiających (setterów) i zwracających (getterów).