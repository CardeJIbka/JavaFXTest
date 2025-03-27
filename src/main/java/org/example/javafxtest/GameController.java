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
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


//Главный контроллер игрового меню. Управляет отображением слайд-шоу фоновых изображений и обработкой действий пользователя.

public class GameController {
    // Элементы интерфейса из FXML
    public ImageView logoImageView;          // Логотип игры
    @FXML private ImageView backgroundImageView;     // Основное фоновое изображение
    @FXML private ImageView nextBackgroundImageView; // Временное фоновое изображение для анимации перехода
    @FXML private Button options;           // Кнопка перехода в настройки
    @FXML private Button play;              // Кнопка начала игры
    @FXML private Button exit;              // Кнопка выхода из игры

    // Переменные для управления слайд-шоу
    private Timeline slideShowTimeline;      // Таймер для автоматической смены фонов
    private List<Image> backgroundImages = new ArrayList<>(); // Список загруженных фоновых изображений
    private int currentBackgroundIndex = 0;  // Индекс текущего фонового изображения


    @FXML
    public void initialize() {
        // Настройка временного ImageView для плавных переходов
        nextBackgroundImageView.setVisible(false); // Сначала скрываем
        // Привязываем размеры к основному фоновому изображению
        nextBackgroundImageView.fitWidthProperty().bind(backgroundImageView.fitWidthProperty());
        nextBackgroundImageView.fitHeightProperty().bind(backgroundImageView.fitHeightProperty());

        loadBackgroundImages();  // Загрузка фоновых изображений
        startSlideShow();        // Запуск слайд-шоу
    }

    // ЗАГРУЗКА ФОНА ДЛЯ СЛАЙДШОУ
    private void loadBackgroundImages() {
        // Пути к фоновым изображениям в ресурсах
        String[] imagePaths = {
                "/images/background/MainMenuBackground1.png",
                "/images/background/MainMenuBackground2.png",
                "/images/background/MainMenuBackground3.png"
        };

        // Загрузка каждого изображения по указанным путям
        for (String path : imagePaths) {
            try (InputStream is = getClass().getResourceAsStream(path)) {
                if (is != null) {
                    backgroundImages.add(new Image(is)); // Добавляем успешно загруженное изображение
                }
            } catch (IOException e) {
                e.printStackTrace(); // Логируем ошибки загрузки
            }
        }

        // Устанавливаем первое изображение как начальный фон
        if (!backgroundImages.isEmpty()) {
            backgroundImageView.setImage(backgroundImages.get(0));
        }
    }

    // ГОЛОВА СЛАЙДШОУ
    private void startSlideShow() {
        // Для слайд-шоу нужно минимум 2 изображения
        if (backgroundImages.size() < 2) return;

        // Создаем таймер с интервалом 5 секунд между сменами фона
        slideShowTimeline = new Timeline(
                new KeyFrame(Duration.seconds(5), event -> changeBackgroundWithAnimation())
        );
        slideShowTimeline.setCycleCount(Timeline.INDEFINITE); // Бесконечное повторение
        slideShowTimeline.play(); // Запуск таймера
    }

    // ОСНОВНОЙ ПРИНЦИП СЛАЙДШОУ
    private void changeBackgroundWithAnimation() {
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

    // кнопка Играть
    @FXML
    void onPlayBtnClick(ActionEvent event) {
        play.setText("Играем..."); // Изменяем текст кнопки
    }

    // Кнопка Настройки
    @FXML
    void onOptionsBtnClick(ActionEvent event) {
        try {
            // Останавливаем слайд-шоу перед переходом
            if (slideShowTimeline != null) {
                slideShowTimeline.stop();
            }

            Stage currentStage = (Stage) options.getScene().getWindow();
            WindowState.saveWindowState(currentStage); // Сохраняем параметры окна

            // Загружаем новую сцену
            Parent root = FXMLLoader.load(getClass().getResource("options.fxml"));
            currentStage.setScene(new Scene(root, WindowState.getWidth(), WindowState.getHeight()));

            // Восстанавливаем параметры окна
            WindowState.restoreWindowState(currentStage);
        } catch (IOException e) {
            e.printStackTrace(); // Логируем ошибки загрузки
        }
    }

    // Кнопка Выход
    @FXML
    void onExitBtnClick(ActionEvent event) {
        Platform.exit(); // Корректный выход из приложения
    }
}

// СОХРАНЕНИЕ РАЗРЕШЕНИЕ ИЗОБРАЖЕНИЯ
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