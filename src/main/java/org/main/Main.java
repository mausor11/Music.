package org.main;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage stage) {
        StageHolder.setPrimaryStage(stage);
        StageHolder.getPrimaryStage().setScene(Default.mainViewScene);
        StageHolder.getPrimaryStage().show();
    }
}