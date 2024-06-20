package org.example.k2_client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Tworzy loader FXML do załadowania pliku view.fxml, który zawiera definicję interfejsu użytkownika
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("view.fxml"));

        // Tworzy nową scenę o rozmiarach 320x240 pikseli, ładując definicję interfejsu użytkownika z pliku view.fxml
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        // Ustawia tytuł okna aplikacji
        stage.setTitle("Hello!");

        // Ustawia scenę dla okna aplikacji
        stage.setScene(scene);

        // Wyświetla okno aplikacji
        stage.show();

        // Tworzy nowy wątek połączenia z serwerem na localhost i porcie 5000, przypisując go do ClientReceiver.thread
        ClientReceiver.thread = new ConnectionThread("localhost", 5000);

        // Uruchamia wątek połączenia
        ClientReceiver.thread.start();
    }

    public static void main(String[] args) {
        // Wywołuje metodę launch z klasy Application, która uruchamia aplikację JavaFX
        launch();
    }
}
