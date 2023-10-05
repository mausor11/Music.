package org.main.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.main.Default;

import static org.main.Default.mainSpace;

public class ChooseTileController {
    @FXML
    StackPane track;
    @FXML
    StackPane playlist;
    @FXML
    StackPane album;
    @FXML
    StackPane backPane;
    @FXML
    Label backText;
    @FXML
    Label backTextEffect;
    private BooleanProperty isChange = new SimpleBooleanProperty(false);
    @FXML
    public void initialize() {
        setHoverEffect(track);
        setHoverEffect(playlist);
        setHoverEffect(album);
        makeBackTextHoverEffect();
        setImporter();
        setBack();
    }
    private void setHoverEffect(StackPane stackPane) {
        stackPane.setOnMouseEntered(mouseEnter -> {
            Timeline enterAnimation = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(stackPane.scaleXProperty(), stackPane.getScaleX())),
                    new KeyFrame(Duration.ZERO, new KeyValue(stackPane.scaleYProperty(), stackPane.getScaleY())),
                    new KeyFrame(Duration.ZERO, new KeyValue(stackPane.opacityProperty(), stackPane.getOpacity())),

                    new KeyFrame(Duration.millis(100), new KeyValue(stackPane.scaleXProperty(), 1.04)),
                    new KeyFrame(Duration.millis(100), new KeyValue(stackPane.scaleYProperty(), 1.04)),
                    new KeyFrame(Duration.millis(100), new KeyValue(stackPane.opacityProperty(), 1))
            );
            enterAnimation.play();
        });
        stackPane.setOnMouseExited(mouseExited -> {
            Timeline exitAnimation = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(stackPane.scaleXProperty(), stackPane.getScaleX())),
                    new KeyFrame(Duration.ZERO, new KeyValue(stackPane.scaleYProperty(), stackPane.getScaleY())),
                    new KeyFrame(Duration.ZERO, new KeyValue(stackPane.opacityProperty(), stackPane.getOpacity())),

                    new KeyFrame(Duration.millis(100), new KeyValue(stackPane.scaleXProperty(), 1)),
                    new KeyFrame(Duration.millis(100), new KeyValue(stackPane.scaleYProperty(), 1)),
                    new KeyFrame(Duration.millis(100), new KeyValue(stackPane.opacityProperty(), 0.9))
            );
            exitAnimation.play();
        });

    }
    private void makeBackTextHoverEffect() {
        backPane.setOnMouseEntered(event -> {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(backTextEffect.opacityProperty(), backTextEffect.getOpacity())),

                    new KeyFrame(Duration.millis(100), new KeyValue(backTextEffect.opacityProperty(), 1))
            );
            timeline.play();
        });
        backPane.setOnMouseExited(event2 -> {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(backTextEffect.opacityProperty(), backTextEffect.getOpacity())),

                    new KeyFrame(Duration.millis(100), new KeyValue(backTextEffect.opacityProperty(), 0))
            );
            timeline.play();
        });
        backPane.setOnMouseClicked(event3 -> {
            GaussianBlur gaussianBlur = new GaussianBlur(20);
            Default.containerBox.setEffect(null);
            Default.containerBox.setEffect(gaussianBlur);
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(gaussianBlur.radiusProperty(), 20)),
                    new KeyFrame(Duration.ZERO, new KeyValue(Default.chooseWindow.opacityProperty(), 1)),

                    new KeyFrame(Duration.millis(100), new KeyValue(gaussianBlur.radiusProperty(), 0)),
                    new KeyFrame(Duration.millis(150), new KeyValue(Default.chooseWindow.opacityProperty(), 0))
            );
            timeline.play();
            timeline.setOnFinished(event4 -> {
                Default.containerBox.setDisable(false);
                Default.mainPane.getChildren().remove(Default.chooseWindow);
            });

        });
    }
    private void setBack() {
        isChange.addListener(((observableValue, aBoolean, t1) -> {
            if(t1) {
                GaussianBlur gaussianBlur = new GaussianBlur(20);
                Default.containerBox.setEffect(null);
                Default.containerBox.setEffect(gaussianBlur);
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(gaussianBlur.radiusProperty(), 20)),
                        new KeyFrame(Duration.ZERO, new KeyValue(Default.chooseWindow.opacityProperty(), 1)),

                        new KeyFrame(Duration.millis(100), new KeyValue(gaussianBlur.radiusProperty(), 0)),
                        new KeyFrame(Duration.millis(150), new KeyValue(Default.chooseWindow.opacityProperty(), 0))
                );
                timeline.play();
                timeline.setOnFinished(event4 -> {
                    Default.containerBox.setDisable(false);
                    Default.mainPane.getChildren().remove(Default.chooseWindow);
                    isChange.set(false);

                });
            }
        }));
    }
    private void setImporter() {
        track.setOnMouseClicked(trackEvent -> {
            AddTrackSectionController.actMode.set(0);
            if(!mainSpace.getChildren().isEmpty()) {
                mainSpace.getChildren().clear();
            }
            mainSpace.getChildren().add(Default.trackTrackView);
            isChange.set(true);
            MainViewController.isImporter.set(true);
        });
        playlist.setOnMouseClicked(playlistEvent -> {
            System.out.println("playlist");
            AddTrackSectionController.actMode.set(1);
        });
        album.setOnMouseClicked(albumEvent -> {
            System.out.println("album");
            AddTrackSectionController.actMode.set(2);
        });
    }
}
