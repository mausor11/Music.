package org.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class Default {
/** Size **/
    public final static int MIN_WIDTH = 1320;
    public final static int MIN_HEIGHT = 815;


    public static Scene mainViewScene; static {
        try {
            mainViewScene = new Scene(new FXMLLoader(Default.class.getResource("fxml/MainView.fxml")).load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void resetMainViewScene() {
        try {
            mainViewScene = new Scene(new FXMLLoader(Default.class.getResource("fxml/MainView.fxml")).load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
