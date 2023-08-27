package org.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Objects;

public class Default {
/** Size **/
    public final static int MIN_WIDTH = 1320;
    public enum StatusPlay {PLAY, PAUSE };
    public enum StatusRepeat {NONE, REPEAT, REPEAT_HEAVY}
    public final static int MIN_HEIGHT = 815;
/** Dark Theme Colors **/
    public final static Color BACKGROUND_COLOR = Color.web("#030303");
    public final static Color TILES_COLOR = Color.web("#1D1919");
    public final static Color LIGHT_TILES_COLOR = Color.web("#302C2C");
    public final static Color BROWN_4 = Color.web("#553939");
    public final static Color BROWN_3 = Color.web("#704F4F");
    public final static Color BROWN_2 = Color.web("#937272");
    public final static Color BROWN_1 = Color.web("#A77979");
    public final static Color FONT_COLOR = Color.web("#E4CFCF");
    public static Scene mainViewScene;
    static {
        try {
            mainViewScene = new Scene(new FXMLLoader(Default.class.getResource("fxml/MainView.fxml")).load());
            mainViewScene.getStylesheets().add(Objects.requireNonNull(Objects.requireNonNull(Default.class.getResource("css/darkTheme.css")).toExternalForm()));
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
