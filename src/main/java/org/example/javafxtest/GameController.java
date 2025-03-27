package org.example.javafxtest;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class GameController {
    public ImageView logoImageView;

    @FXML
    private Button options;
    @FXML
    private Button play;
    @FXML
    private Button exit;

    @FXML
    void onPlayBtnClick(ActionEvent event) { //кнопка Играть
        play.setText("Играем...");
        options.setText("");
        exit.setText("");
    }

    @FXML
    void onOptionsBtnClick(ActionEvent event) { // кнопка Настройки
        try {
            Stage currentStage = (Stage) options.getScene().getWindow(); // считываем параметры окна
            WindowState.saveWindowState(currentStage); // записываем в класс

            FXMLLoader loader = new FXMLLoader(getClass().getResource("options.fxml")); // включаем и загружаем другую папку кода
            Parent root = loader.load();

            Scene optionsScene = new Scene(root, WindowState.getWidth(), WindowState.getHeight()); // для новой сцены передаём параметры из класса WindowState
            currentStage.setScene(optionsScene);

            WindowState.restoreWindowState(currentStage);

        } catch (IOException e) {
            e.printStackTrace();
            options.setText("Ошибка загрузки!"); // если нихуя не сработает чтоб код не вылетел и было видно, что чёта произошло
        }
    }

    @FXML
    void onExitBtnClick(ActionEvent event) { //кнопка Выход
        Platform.exit();
    }
}

// СОХРАНЕНИЕ ПАРАМЕТРОВ ОКНА
class WindowState {
    private static double width;
    private static double height;
    private static double x;
    private static double y;
    private static boolean maximized;

    // метод, чтоб записать данные
    public static void saveWindowState(Window window) {
        if (window instanceof Stage) {
            Stage stage = (Stage) window;
            width = stage.getWidth();
            height = stage.getHeight();
            x = stage.getX();
            y = stage.getY();
            maximized = stage.isMaximized();
        }
    }
    // метод, чтоб вернуть данные окна
    public static void restoreWindowState(Window window) {
        if (window instanceof Stage) {
            Stage stage = (Stage) window;
            stage.setWidth(width);
            stage.setHeight(height);
            stage.setX(x);
            stage.setY(y);
            stage.setMaximized(maximized);
        }
    }

    public static double getWidth() { return width; }
    public static double getHeight() { return height; }
    public static double getX() { return x; }
    public static double getY() { return y; }
    public static boolean isMaximized() { return maximized; }
}