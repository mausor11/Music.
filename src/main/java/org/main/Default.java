package org.main;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Default {
/** ArrayList with tiles **/
    public static ArrayList<AlbumTile> favourites;
    public static ArrayList<AlbumTile> albums;
    public static ArrayList<AlbumTile> playlists;
    public final static String dot = " • ";
/** Size **/
    public final static int MIN_WIDTH = 1320;
    public enum StatusPlay {PLAY, PAUSE}
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
    public static BooleanProperty tileFocused = new SimpleBooleanProperty(true);
    public static BooleanProperty libraryFocused = new SimpleBooleanProperty(false);
    public static StackPane homeView;
    static {
        try {
            homeView = new FXMLLoader(Main.class.getResource("fxml/HomeSection.fxml")).load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static StackPane playlistView;
    static {
        try {
            playlistView = new FXMLLoader(Main.class.getResource("fxml/PlaylistSection.fxml")).load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static StackPane albumsView;
    static {
        try {
            albumsView = new FXMLLoader(Main.class.getResource("fxml/AlbumSection.fxml")).load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
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
    public static void blurEffect(ImageView button, ImageView buttonEffect) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(buttonEffect.opacityProperty(), buttonEffect.getOpacity())),
                new KeyFrame(Duration.millis(100), new KeyValue(buttonEffect.opacityProperty(), 1))
        );
        timeline.play();

        button.setOnMouseExited(event -> {
            Timeline timeline1 = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(buttonEffect.opacityProperty(), buttonEffect.getOpacity())),
                    new KeyFrame(Duration.millis(100), new KeyValue(buttonEffect.opacityProperty(), 0))
            );
            timeline1.play();
        });
    }
    public static Rectangle clipShape(double width, double height, double arcWidth, double arcHeight) {
        Rectangle clipShape = new Rectangle();
        clipShape.setWidth(width);
        clipShape.setHeight(height);
        clipShape.setArcWidth(arcWidth);
        clipShape.setArcHeight(arcHeight);
        return clipShape;
    }
    public static Rectangle2D setViewportSquare(Image backgroundArt, ImageView background) {
        double VIEWPORT_WIDTH = backgroundArt.getWidth();
        double VIEWPORT_HEIGHT = (VIEWPORT_WIDTH / background.getFitWidth()) * background.getFitHeight();
        double VIEWPORT_X = 0;
        double VIEWPORT_Y = (backgroundArt.getHeight() / 2) - (VIEWPORT_HEIGHT / 2);

        return new Rectangle2D(VIEWPORT_X, VIEWPORT_Y, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
    }
    public static Rectangle2D setViewportRectangle(Image backgroundArt, ImageView background) {
        double VIEWPORT_HEIGHT = backgroundArt.getHeight();
        double VIEWPORT_WIDTH = (VIEWPORT_HEIGHT / background.getFitHeight()) * background.getFitWidth();
        double VIEWPORT_Y = 0;
        double VIEWPORT_X = (backgroundArt.getWidth() / 2) - (VIEWPORT_WIDTH / 2);

        return new Rectangle2D(VIEWPORT_X, VIEWPORT_Y, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
    }
}
