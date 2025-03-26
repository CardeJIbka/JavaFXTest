package org.example.javafxtest;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game extends Application { // главный класс как я понимаю
    @Override
    public void start(Stage stage) throws IOException { // метод, вызываемый при старте игры
        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("main_menu.fxml")); // хз что, похоже на запуск первого окна при запуске игры
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080); //размер окна
        stage.setTitle("Tales of Elteria"); //название окна
        stage.setScene(scene); // установка сцены (fxml файл с дизайном и объектами)
        stage.show(); // отображение сцены
    }

    public static void main(String[] args) { // начальный метод
        launch(); // хз как работает, запуск приложения
    }
}
