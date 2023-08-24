package org.main;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class StageHolder {
    private static Stage primaryStage;
    public static void setPrimaryStage(Stage stage) {
        stage.setMinHeight(Default.MIN_HEIGHT);
        stage.setMinWidth(Default.MIN_WIDTH);
        stage.setWidth(Default.MIN_WIDTH);
        stage.setHeight(Default.MIN_HEIGHT);
        stage.getIcons().add(new Image(Objects.requireNonNull(StageHolder.class.getResourceAsStream("icons/IconDark.png"))));
        primaryStage = stage;
    }
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
