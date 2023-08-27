package org.main.controllers;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.main.Default;
import org.main.Main;
import java.util.ArrayList;
import java.util.Objects;

public class MainViewController {
    @FXML
    GridPane menu;
    @FXML
    StackPane homeTile;
    @FXML
    StackPane playlistTile;
    @FXML
    StackPane albumTile;
    @FXML
    StackPane favouriteTile;
    @FXML
    GridPane searchTile;
    @FXML
    Rectangle homeCover;
    @FXML
    Rectangle playlistCover;
    @FXML
    Rectangle albumCover;
    @FXML
    Rectangle favouriteCover;
    @FXML
    ImageView playButton;
    @FXML
    ImageView playButtonEffect;
    @FXML
    ImageView undoButton;
    @FXML
    ImageView undoButtonEffect;
    @FXML
    ImageView nextButton;
    @FXML
    ImageView nextButtonEffect;
/**<p>0 - Home Tile</p>
 * <p>1 - Playlists Tile</p>
 * <p>2 - Albums Tile</p>
 * <p>3 - Favourite Tile</p>
 * <p>4 - Settings Tile</p> **/
    private int actualTile = 0;
    private int prevTile = 0;
    private final Rectangle searchCover = new Rectangle();
    private final ArrayList<Rectangle> tileCover = new ArrayList<>();
    private boolean isSearchOn = false;
    private boolean isAnimated = false;
    Default.Status buttonStatus = Default.Status.PAUSE;

    @FXML
    public void initialize() {
        prepareCovers();

    }
    private void prepareCovers() {
        tileCover.add(homeCover);
        tileCover.add(playlistCover);
        tileCover.add(albumCover);
        tileCover.add(favouriteCover);
        tileCover.add(searchCover);
    }
    public void setHomeTile() {
        if(actualTile != 0) {
            actualTile = 0;
            makeTileAnimation();
            prevTile = actualTile;
        }
    }
    public void setPlaylistsTile() {
        if(actualTile != 1) {
            actualTile = 1;
            makeTileAnimation();
            prevTile = actualTile;
        }

    }
    public void setAlbumsTile() {
        if(actualTile != 2) {
            actualTile = 2;
            makeTileAnimation();
            prevTile = actualTile;
        }
    }
    public void setFavouriteTile() {
        if(actualTile != 3) {
            actualTile = 3;
            makeTileAnimation();
            prevTile = actualTile;
        }
    }
    public void setSearchTile() {
        if(!isSearchOn) {
            double change = menu.getColumnConstraints().get(5).getMaxWidth() - menu.getColumnConstraints().get(5).getPrefWidth();
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(menu.getColumnConstraints().get(5).prefWidthProperty(), menu.getColumnConstraints().get(5).getPrefWidth())),
                    new KeyFrame(Duration.ZERO, new KeyValue(menu.getColumnConstraints().get(4).prefWidthProperty(), menu.getColumnConstraints().get(4).getPrefWidth())),
                    new KeyFrame(Duration.millis(100), new KeyValue(menu.getColumnConstraints().get(5).prefWidthProperty(), 200)),
                    new KeyFrame(Duration.millis(100), new KeyValue(menu.getColumnConstraints().get(4).prefWidthProperty(), menu.getColumnConstraints().get(4).getPrefWidth() - change))
            );
            timeline.play();
            searchTile.getStyleClass().remove("tileMenu");
            searchTile.getStyleClass().add("searchTileActive");
            isSearchOn = true;
            actualTile = 4;
            makeTileAnimation();
            prevTile = actualTile;
        }

    }
    private void makeTileAnimation() {
            if(prevTile < actualTile) {
                StackPane.setAlignment(tileCover.get(prevTile), Pos.CENTER_RIGHT);
                StackPane.setAlignment(tileCover.get(actualTile), Pos.CENTER_LEFT);
            } else {
                StackPane.setAlignment(tileCover.get(prevTile), Pos.CENTER_LEFT);
                StackPane.setAlignment(tileCover.get(actualTile), Pos.CENTER_RIGHT);
            }
            Timeline timeline = new Timeline();
            if(prevTile != 4) {
                timeline.getKeyFrames().addAll(
                        new KeyFrame(Duration.ZERO, new KeyValue(tileCover.get(prevTile).widthProperty(), tileCover.get(prevTile).getWidth(), Interpolator.EASE_IN)),
                        new KeyFrame(Duration.millis(100), new KeyValue(tileCover.get(prevTile).widthProperty(), 0, Interpolator.EASE_IN)));
            } else {
                searchTile.getStyleClass().remove("searchTileActive");
                searchTile.getStyleClass().add("tileMenu");
                isSearchOn = false;
                timeline.getKeyFrames().addAll(
                        new KeyFrame(Duration.ZERO, new KeyValue(menu.getColumnConstraints().get(5).prefWidthProperty(), menu.getColumnConstraints().get(5).getPrefWidth())),
                        new KeyFrame(Duration.ZERO, new KeyValue(menu.getColumnConstraints().get(4).prefWidthProperty(), menu.getColumnConstraints().get(4).getPrefWidth())),
                        new KeyFrame(Duration.millis(100), new KeyValue(menu.getColumnConstraints().get(5).prefWidthProperty(), 100)),
                        new KeyFrame(Duration.millis(100), new KeyValue(menu.getColumnConstraints().get(4).prefWidthProperty(), menu.getColumnConstraints().get(4).getPrefWidth() +100))
                );
            }
            timeline.play();
            timeline.setOnFinished(event ->  {
                Timeline timeline1 = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(tileCover.get(actualTile).widthProperty(), 0, Interpolator.EASE_IN)),
                        new KeyFrame(Duration.millis(100), new KeyValue(tileCover.get(actualTile).widthProperty(), 130, Interpolator.EASE_IN))
                );
                timeline1.play();
                timeline1.setOnFinished(event2 -> {
                });
            });
    }
    public void playEffect() {
        blurEffect(playButton, playButtonEffect);
    }
    public void setPlay() {
            switch(buttonStatus) {
                case PLAY -> {
                    setOnPlay("PlayIcon");
                    buttonStatus = Default.Status.PAUSE;
                }
                case PAUSE -> {
                    setOnPlay("PauseIcon");
                    buttonStatus = Default.Status.PLAY;
                }
            }
    }

    public void nextEffect() {
        blurEffect(nextButton, nextButtonEffect);
    }

    public void undoEffect() {
        blurEffect(undoButton, undoButtonEffect);
    }
    private void blurEffect(ImageView button, ImageView buttonEffect) {
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
    private void setOnPlay(String icon) {
        playButton.setRotate(0);
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(150), playButton);
        rotateTransition.setInterpolator(Interpolator.EASE_OUT);
        rotateTransition.setByAngle(360);
        rotateTransition.setAutoReverse(false);
        rotateTransition.play();
        GaussianBlur gaussianBlur = new GaussianBlur(0);
        playButton.setEffect(gaussianBlur);
        Timeline timeline1 = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(gaussianBlur.radiusProperty(), 0)),
                new KeyFrame(Duration.millis(75), new KeyValue(gaussianBlur.radiusProperty(), 20))
        );
        timeline1.play();
        timeline1.setOnFinished(e -> {
                    playButton.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/" + icon + ".png"))));
                    playButtonEffect.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/" + icon + ".png"))));
                    Timeline timeline2 = new Timeline(
                            new KeyFrame(Duration.ZERO, new KeyValue(gaussianBlur.radiusProperty(), 20)),
                            new KeyFrame(Duration.millis(75), new KeyValue(gaussianBlur.radiusProperty(), 0))
                    );
                    timeline2.play();
                }
        );
    }
}
