package org.main.controllers;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ListView;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.main.Default;
import org.main.Main;

public class LibraryController {
    @FXML
    ImageView plusEffect;
    @FXML
    ImageView plusButton;
    @FXML
    ListView<StackPane> listView;
    private double VIEWPORT_WIDTH;
    private double VIEWPORT_HEIGHT;
    private double VIEWPORT_X;
    private double VIEWPORT_Y;
    private double PREF_WIDTH = 250;
    private double PREF_HEIGHT = 78;
    public void initialize() {
        listView.getItems().add(listCell(new Image(Main.class.getResourceAsStream("cover-images/albums/ASTROWORLD.jpg"))));
        listView.getItems().add(listCell(new Image(Main.class.getResourceAsStream("cover-images/albums/Lost Souls.jpg"))));
        listView.getItems().add(listCell(new Image(Main.class.getResourceAsStream("cover-images/albums/SMTHREENS.jpg"))));
        listView.getItems().add(listCell(new Image(Main.class.getResourceAsStream("cover-images/albums/Starboy.jpg"))));
        listView.getItems().add(listCell(new Image(Main.class.getResourceAsStream("cover-images/albums/The Off-Season.jpg"))));
        listView.getItems().add(listCell(new Image(Main.class.getResourceAsStream("cover-images/albums/utopia.jpg"))));
        listView.getItems().add(listCell(new Image(Main.class.getResourceAsStream("cover-images/albums/WholeLottaRed.jpg"))));
        listView.getItems().add(listCell(new Image(Main.class.getResourceAsStream("cover-images/albums/ye.jpg"))));
        listView.getItems().add(listCell(new Image(Main.class.getResourceAsStream("cover-images/albums/NOT ALL HEROES WEAR CAPES.jpg"))));
    }
    public void plusEffect() {
        Default.blurEffect(plusButton,plusEffect);
    }
    public void setPlus() {
        plusButton.setRotate(0);
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(150), plusButton);
        rotateTransition.setInterpolator(Interpolator.EASE_OUT);
        rotateTransition.setByAngle(180);
        rotateTransition.setAutoReverse(false);
        rotateTransition.play();
        GaussianBlur gaussianBlur = new GaussianBlur(0);
        plusButton.setEffect(gaussianBlur);
        Timeline timeline1 = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(gaussianBlur.radiusProperty(), 0)),
                new KeyFrame(Duration.millis(75), new KeyValue(gaussianBlur.radiusProperty(), 7.7)),
                new KeyFrame(Duration.millis(150), new KeyValue(gaussianBlur.radiusProperty(), 0))
        );
        timeline1.play();
    }
    private StackPane listCell(Image backgroundArt) {
        StackPane cell = new StackPane();
        cell.setPrefWidth(PREF_WIDTH);
        cell.setPrefHeight(PREF_HEIGHT);

        cell.getChildren().add(setBackground(backgroundArt));

        listView.widthProperty().addListener((observable, oldValue, newValue) -> {
            cell.setPrefWidth(newValue.doubleValue() - 20.0);
        });

        return cell;

    }
    private Rectangle2D newViewport(Image backgroundArt, ImageView background) {
        VIEWPORT_WIDTH = backgroundArt.getWidth();
        VIEWPORT_HEIGHT = (VIEWPORT_WIDTH / background.getFitWidth()) * background.getFitHeight();
        VIEWPORT_X = 0;
        VIEWPORT_Y = (backgroundArt.getHeight() / 2) - (VIEWPORT_HEIGHT / 2);

        return new Rectangle2D(VIEWPORT_X, VIEWPORT_Y, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
    }
    private StackPane setBackground(Image backgroundArt) {
        ImageView background = new ImageView(backgroundArt);
        background.setFitHeight(PREF_HEIGHT);
        background.setFitWidth(PREF_WIDTH);
        background.setEffect(new GaussianBlur(3.0));
        background.setOpacity(0);
        background.setViewport(newViewport(backgroundArt, background));

        Rectangle clipShape = new Rectangle();
        clipShape.setWidth(PREF_WIDTH);
        clipShape.setHeight(PREF_HEIGHT);
        clipShape.setArcHeight(16);
        clipShape.setArcWidth(16);
        background.setClip(clipShape);

        Rectangle cover = new Rectangle();
        cover.setFill(Default.BROWN_4);
        cover.setWidth(PREF_WIDTH);
        cover.setHeight(PREF_HEIGHT);
        cover.setOpacity(0);
        cover.setArcHeight(16);
        cover.setArcWidth(16);

        StackPane bg = new StackPane(background);
        bg.getChildren().add(cover);


        listView.widthProperty().addListener((observable, oldValue, newValue) -> {
            background.setFitWidth(newValue.doubleValue() - 20.0);
            clipShape.setWidth(newValue.doubleValue() - 20.0);
            cover.setWidth(newValue.doubleValue() - 20.0);
            background.setViewport(newViewport(backgroundArt, background));
        });


        cover.setOnMouseEntered(event -> {
            GaussianBlur gaussianBlur = new GaussianBlur(0.0);
            background.setEffect(gaussianBlur);
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(gaussianBlur.radiusProperty(), gaussianBlur.getRadius())),
                    new KeyFrame(Duration.ZERO, new KeyValue(background.opacityProperty(), background.getOpacity())),
                    new KeyFrame(Duration.ZERO, new KeyValue(cover.opacityProperty(), cover.getOpacity())),

                    new KeyFrame(Duration.millis(200), new KeyValue(gaussianBlur.radiusProperty(), 7.7)),
                    new KeyFrame(Duration.millis(200), new KeyValue(background.opacityProperty(), 0.3)),
                    new KeyFrame(Duration.millis(200), new KeyValue(cover.opacityProperty(), 0.2))
            );
            timeline.play();
            background.setOpacity(0.7);
            cover.setOnMouseExited(event1 -> {
                Timeline timeline1 = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(gaussianBlur.radiusProperty(), gaussianBlur.getRadius())),
                        new KeyFrame(Duration.ZERO, new KeyValue(background.opacityProperty(), background.getOpacity())),
                        new KeyFrame(Duration.ZERO, new KeyValue(cover.opacityProperty(), cover.getOpacity())),

                        new KeyFrame(Duration.millis(200), new KeyValue(gaussianBlur.radiusProperty(), 0.0)),
                        new KeyFrame(Duration.millis(200), new KeyValue(background.opacityProperty(), 0.0)),
                        new KeyFrame(Duration.millis(200), new KeyValue(cover.opacityProperty(), 0))

                );
                timeline1.play();
            });
        });

        return bg;
    }
}
