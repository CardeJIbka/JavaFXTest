package org.example.javafxtest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.PauseTransition;

import java.io.IOException;
import java.util.Objects;

public class OptionsController {

    @FXML
    private Button saveButton;
    @FXML
    private Button backButton;
    private final AudioClip buttonSelected = new AudioClip(this.getClass().getResource("/sounds/button_sounds/buttonSelected.mp3").toExternalForm());
    private final AudioClip buttonClicked = new AudioClip(this.getClass().getResource("/sounds/button_sounds/buttonClicked.mp3").toExternalForm());
    private boolean isSaveButtonClicked = false;

    @FXML
    public void initialize() {
        buttonSelected.setVolume(0.1);
        buttonClicked.setVolume(0.2);
    }

    @FXML
    void onMouseEnteredOptions(javafx.scene.input.MouseEvent mouseEvent) {
        Image newImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/buttons/backButtonSelected.png")));
        ImageView imageView = new ImageView(newImage);
        imageView.setFitWidth(535); // Устанавливаем ширину и высоту
        imageView.setFitHeight(156);
        backButton.setGraphic(imageView);
        buttonSelected.play();
    }

    @FXML
    void onMouseExitedOptions(javafx.scene.input.MouseEvent mouseEvent) {
        Image newImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/buttons/backButton.png")));
        ImageView imageView = new ImageView(newImage);
        imageView.setFitWidth(535); // Устанавливаем ширину и высоту
        imageView.setFitHeight(156);
        backButton.setGraphic(imageView);
    }

    @FXML
    void onMouseEnteredSave(javafx.scene.input.MouseEvent mouseEvent) {
        Image newImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/buttons/saveButtonSelected.png")));
        ImageView imageView = new ImageView(newImage);
        imageView.setFitWidth(535); // Устанавливаем ширину и высоту
        imageView.setFitHeight(156);
        saveButton.setGraphic(imageView);
        buttonSelected.play();
    }

    @FXML
    void onMouseExitedSave(javafx.scene.input.MouseEvent mouseEvent) {
        Image newImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/buttons/saveButton.png")));
        ImageView imageView = new ImageView(newImage);
        imageView.setFitWidth(535); // Устанавливаем ширину и высоту
        imageView.setFitHeight(156);
        saveButton.setGraphic(imageView);
    }

    @FXML
    void onSaveButtonClick(ActionEvent event) {
        if (!isSaveButtonClicked) {
            isSaveButtonClicked = true;

            Image clickedImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/buttons/saveButtonClick.png")));
            ImageView clickedImageView = new ImageView(clickedImage);
            clickedImageView.setFitWidth(535);
            clickedImageView.setFitHeight(156);
            saveButton.setGraphic(clickedImageView);
            buttonClicked.play();

            PauseTransition delay = new PauseTransition(Duration.seconds(3)); // кд 3 секунды перед тем, как фотка станет нормальной
            delay.setOnFinished(e -> {
                isSaveButtonClicked = false;

                Image normalImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/buttons/saveButton.png")));
                ImageView normalImageView = new ImageView(normalImage);
                normalImageView.setFitWidth(535);
                normalImageView.setFitHeight(156);
                saveButton.setGraphic(normalImageView);
                saveButton.setText("");
            });
            delay.play();
        }
    }

    @FXML
    private void onBackButtonClick() {
        buttonClicked.play();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main_menu.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) backButton.getScene().getWindow();

            // Сохраняем текущие размеры окна
            double width = currentStage.getWidth();
            double height = currentStage.getHeight();

            // Создаём сцену с сохранёнными размерами
            Scene mainScene = new Scene(root, width, height);
            currentStage.setScene(mainScene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}