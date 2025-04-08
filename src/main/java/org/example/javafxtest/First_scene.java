package org.example.javafxtest;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class First_scene {


    @FXML
    private MediaView cutScene1;
    @FXML
    private Text startText;
    @FXML
    private Text hint;
    private MediaPlayer mediaPlayer;
    private PauseTransition delay = new PauseTransition(Duration.seconds(4));

    public void initialize() {
        mediaPlayer = cutScene1.getMediaPlayer();
        startText.setVisible(false);
        hint.setVisible(false);
        mediaPlayer.setVolume(0.1);
        mediaPlayer.play();

        mediaPlayer.setOnEndOfMedia(this::endOfCutScene); // вызов метода endOfCutScene через лямбду когда конец
        mediaPlayer.setOnStopped(this::endOfCutScene); // вызов метода endOfCutScene через лямбду когда стоп
        delay.play();
        delay.setOnFinished(event -> fadeInForHint(hint));
    }


    public void endOfCutScene() {
        startText.setVisible(true);
        startText.setOpacity(0);// Показываем

        // Анимация появления текста (fade in)
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), startText);
        fadeTransition.setFromValue(0); // Начальная непрозрачность
        fadeTransition.setToValue(1);   // Конечная непрозрачность
        fadeTransition.play();
        hint.setVisible(false);
    }

    public void skipCutScene() { // (ЛКМ)
        mediaPlayer.stop();
    }


    public void fadeInForHint(Text text) {
        if (!startText.isVisible()) {
            text.setVisible(true);
            text.setOpacity(0);// Показываем

            // Анимация появления текста (fade in)
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), text);
            fadeTransition.setFromValue(0); // Начальная непрозрачность
            fadeTransition.setToValue(1);   // Конечная непрозрачность
            fadeTransition.play();
        }
    }
}