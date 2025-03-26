package org.example.javafxtest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HelloController {

    @FXML
    private Button options;

    @FXML
    private Button play;

    @FXML
    void onPlayBtnClick(ActionEvent event) {
        play.setText("Играем...");
        options.setText("Настройки");
    }

    @FXML
    void onOptionsBtnClick(ActionEvent event) {
        options.setText("Настраиваем...");
        play.setText("Играть");
    }

}
