package org.main.controllers;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;

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
/**<p>0 - Home Tile</p>
 * <p>1 - Playlists Tile</p>
 * <p>2 - Albums Tile</p>
 * <p>3 - Favourite Tile</p>
 * <p>4 - Settings Tile</p> **/
    private int actualTile = 0;
    private int prevTile = 0;
    private Rectangle searchCover = new Rectangle();
    private final ArrayList<Rectangle> tileCover = new ArrayList<>();
    private boolean isSearchOn = false;

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

}
