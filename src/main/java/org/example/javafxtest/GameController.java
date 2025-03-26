package org.example.javafxtest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GameController { // этот класс управляет свойствами объектов сцены

    @FXML
    private Button options; // говорим что есть кнопка с айди options

    @FXML
    private Button play; // говорим что есть кнопка с айди play

    @FXML
    void onPlayBtnClick(ActionEvent event) { // метод, который выполняется при нажатии кнопки "играть"
        play.setText("Играем..."); // изменить текст кнопки играть
        options.setText("Настройки"); // изменить текст кнопки настройки
    }

    @FXML
    void onOptionsBtnClick(ActionEvent event) { // метод, который выполняется при нажатии кнопки "настройки"
        options.setText("Настраиваем..."); // изменить текст кнопки настройки
        play.setText("Играть"); // изменить текст кнопки играть
    }

}
