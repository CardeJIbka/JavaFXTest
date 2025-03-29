package org.example.javafxtest;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import javax.print.attribute.standard.Media;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class GameController {

    @FXML
    private ImageView logoImageView, backgroundImageView, nextBackgroundImageView;
    @FXML
    private Button optionsButton, playButton, exitButton;
    private Timeline slideShowTimeline;      // Таймер для автоматической смены фонов
    private Timeline backgroundTimeline;
    private List<Image> backgroundImages = new ArrayList<>(); // Список загруженных фоновых изображений
    private int currentBackgroundIndex = 0;  // Индекс текущего фонового изображения
    private final AudioClip buttonSelected = new AudioClip(Objects.requireNonNull(this.getClass().getResource("/sounds/button_sounds/buttonSelected.mp3")).toExternalForm());
    private final AudioClip buttonClicked = new AudioClip(Objects.requireNonNull(this.getClass().getResource("/sounds/button_sounds/buttonClicked.mp3")).toExternalForm());

    @FXML
    public void initialize() {
        nextBackgroundImageView.setVisible(false);
        nextBackgroundImageView.fitWidthProperty().bind(backgroundImageView.fitWidthProperty());
        nextBackgroundImageView.fitHeightProperty().bind(backgroundImageView.fitHeightProperty());

        loadBackgroundImages();  // Загрузка фоновых изображений
        startSlideShow();        // Запуск слайд-шоу
        buttonSelected.setVolume(0.1);
        buttonClicked.setVolume(0.2);
    }

    private void loadBackgroundImages() {   // Загрузка фоновых изображений
        String[] imagePaths = {
                "/images/background/MainMenuBackground1.png",
                "/images/background/MainMenuBackground2.png",
                "/images/background/MainMenuBackground3.png",
                "/images/background/MainMenuBackground4.png",
        };
        for (String path : imagePaths) {
            InputStream is = getClass().getResourceAsStream(path);
            if (is != null) {
                backgroundImages.add(new Image(is));
            }
        }
            backgroundImageView.setImage(backgroundImages.getFirst()); // первое изображение
    }

    private void startSlideShow() { // Запуск слайд-шоу
        if (backgroundImages.size() < 2) return;

        slideShowTimeline = new Timeline(
                new KeyFrame(Duration.seconds(5), event ->
                        changeBackgroundWithAnimation())
        );
        slideShowTimeline.setCycleCount(Timeline.INDEFINITE); // Бесконечное повторение
        slideShowTimeline.play(); // Запуск таймера

    }

    private void changeBackgroundWithAnimation() { // метод, делающий смену картинок
        // Вычисляем индекс следующего изображения (с зацикливанием)
        int nextIndex = (currentBackgroundIndex + 1) % backgroundImages.size();

        // Настраиваем временное изображение для перехода
        nextBackgroundImageView.setImage(backgroundImages.get(nextIndex));
        nextBackgroundImageView.setOpacity(0); // Начинаем полностью прозрачным
        nextBackgroundImageView.setVisible(true); // Показываем

        // Анимация исчезновения текущего фона (fade out)
        FadeTransition fadeOut = new FadeTransition(Duration.millis(1000), backgroundImageView);
        fadeOut.setFromValue(1.0); // Начальная прозрачность (полностью видимый)
        fadeOut.setToValue(0.0);   // Конечная прозрачность (полностью прозрачный)

        // Анимация появления нового фона (fade in)
        FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), nextBackgroundImageView);
        fadeIn.setFromValue(0.0); // Начальная прозрачность
        fadeIn.setToValue(1.0);   // Конечная прозрачность

        // Параллельное выполнение обеих анимаций
        ParallelTransition parallelTransition = new ParallelTransition(fadeOut, fadeIn);

        // Действия после завершения анимации
        parallelTransition.setOnFinished(event -> {
            // Обновляем основное изображение
            backgroundImageView.setImage(backgroundImages.get(nextIndex));
            backgroundImageView.setOpacity(1.0); // Восстанавливаем непрозрачность
            nextBackgroundImageView.setVisible(false); // Скрываем временное изображение
            currentBackgroundIndex = nextIndex; // Обновляем текущий индекс
        });
        parallelTransition.play(); // Запуск анимации
    }

    @FXML
    void onPlayButtonClick(ActionEvent event) { // Нажатие на кнопку играть
        playButton.setText("Играем..."); // Изменяем текст кнопки
        buttonClicked.play();
    }

    @FXML
    void onOptionsButtonClick(ActionEvent event) {
        buttonClicked.play();
        try {
            // Останавливаем слайд-шоу и музыку перед переходом
            if (slideShowTimeline != null) {
                slideShowTimeline.stop();
            }
            Stage currentStage = (Stage) optionsButton.getScene().getWindow();
            WindowState.saveWindowState(currentStage); // Сохраняем параметры окна

            // Загружаем новую сцену
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("options.fxml")));
            currentStage.setScene(new Scene(root, WindowState.getWidth(), WindowState.getHeight()));

            // Восстанавливаем параметры окна
            WindowState.restoreWindowState(currentStage);
        } catch (IOException e) {
            e.printStackTrace(); // Логируем ошибки загрузки
        }
    }

    @FXML
    void onExitButtonClick(ActionEvent event) {
        buttonClicked.play();
        Platform.exit();
    }

    @FXML
    void onMouseEnteredPlay(javafx.scene.input.MouseEvent mouseEvent) {
        Image newImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/buttons/playButtonSelected.png")));
        ImageView imageView = new ImageView(newImage);
        imageView.setFitWidth(535); // Устанавливаем ширину и высоту
        imageView.setFitHeight(156);
        playButton.setGraphic(imageView);
        buttonSelected.play();
    }

    @FXML
    void onMouseExitedPlay(javafx.scene.input.MouseEvent mouseEvent) {
        Image newImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/buttons/playButton.png")));
        ImageView imageView = new ImageView(newImage);
        imageView.setFitWidth(535); // Устанавливаем ширину и высоту
        imageView.setFitHeight(156);
        playButton.setGraphic(imageView);
    }

    @FXML
    void onMouseEnteredOptions(javafx.scene.input.MouseEvent mouseEvent) {
        Image newImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/buttons/optionsButtonSelected.png")));
        ImageView imageView = new ImageView(newImage);
        imageView.setFitWidth(535); // Устанавливаем ширину и высоту
        imageView.setFitHeight(156);
        optionsButton.setGraphic(imageView);
        buttonSelected.play();
    }

    @FXML
    void onMouseExitedOptions(javafx.scene.input.MouseEvent mouseEvent) {
        Image newImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/buttons/optionsButton.png")));
        ImageView imageView = new ImageView(newImage);
        imageView.setFitWidth(535); // Устанавливаем ширину и высоту
        imageView.setFitHeight(156);
        optionsButton.setGraphic(imageView);
    }

    @FXML
    void onMouseEnteredExit(javafx.scene.input.MouseEvent mouseEvent) {
        Image newImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/buttons/exitButtonSelected.png")));
        ImageView imageView = new ImageView(newImage);
        imageView.setFitWidth(535); // Устанавливаем ширину и высоту
        imageView.setFitHeight(156);
        exitButton.setGraphic(imageView);
        buttonSelected.play();
    }

    @FXML
    void onMouseExitedExit(javafx.scene.input.MouseEvent mouseEvent) {
        Image newImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/buttons/exitButton.png")));
        ImageView imageView = new ImageView(newImage);
        imageView.setFitWidth(535); // Устанавливаем ширину и высоту
        imageView.setFitHeight(156);
        exitButton.setGraphic(imageView);
    }
}



class WindowState {
    private static double width, height, x, y; // Параметры окна
    private static boolean maximized;          // Состояние максимизации

    // сохраняем
    public static void saveWindowState(Window window) {
        if (window instanceof Stage stage) {
            width = stage.getWidth();    // Сохраняем ширину
            height = stage.getHeight();  // Сохраняем высоту
            x = stage.getX();           // Позиция по X
            y = stage.getY();           // Позиция по Y
            maximized = stage.isMaximized(); // Состояние максимизации
        }
    }

    // передаём
    public static void restoreWindowState(Window window) {
        if (window instanceof Stage stage) {
            stage.setWidth(width);      // Восстанавливаем ширину
            stage.setHeight(height);    // Восстанавливаем высоту
            stage.setX(x);             // Позиция по X
            stage.setY(y);             // Позиция по Y
            stage.setMaximized(maximized); // Состояние максимизации
        }
    }

    // Методы доступа к сохраненным параметрам
    public static double getWidth() { return width; }
    public static double getHeight() { return height; }
    public static double getX() { return x; }
    public static double getY() { return y; }
    public static boolean isMaximized() { return maximized; }
}