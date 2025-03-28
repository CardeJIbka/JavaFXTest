package org.example.javafxtest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class OptionsController {

    @FXML
    private Button backButton;  // Должен соответствовать fx:id в options.fxml

    @FXML
    private void onBackButtonClick() {
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