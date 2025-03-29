package org.example.javafxtest;

import java.io.IOException;
import java.util.Objects;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game extends Application { // главный класс как я понимаю

    private final AudioClip ambientMenu = new AudioClip(Objects.requireNonNull(this.getClass().getResource("/sounds/background_ambient/ambientMenu.mp3")).toExternalForm());

    @Override
    public void start(Stage stage) throws IOException { // метод, вызываемый при старте игры
        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("main_menu.fxml")); // хз что, похоже на запуск первого окна при запуске игры
        Scene scene = new Scene(fxmlLoader.load(), 1440, 840); //размер окна
        stage.setTitle("Tales of Elteria"); //название окна
        stage.setScene(scene); // установка сцены (fxml файл с дизайном и объектами)
        stage.setMaximized(true);
        stage.show(); // отображение сцены
        ambientMenu.setVolume(0.1);
        ambientMenu.play();
        Timeline backgroundTimeline = new Timeline( // счётчик чтобы музыка начиналась заного
                new KeyFrame(Duration.seconds(1), actionEvent -> checkAmbientIsPlaying(ambientMenu))
        );
        backgroundTimeline.setCycleCount(Timeline.INDEFINITE);
        backgroundTimeline.play();
    }

    public void checkAmbientIsPlaying(AudioClip clip) {
        if (!clip.isPlaying()) {
            clip.play();
        }
    }

    public static void main(String[] args) { // начальный метод
        launch(); // хз как работает, запуск приложения
    }
}
