package org.main.controllers;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class MainViewController {
    @FXML
    StackPane homeTile;
    @FXML
    Rectangle homeCover;
    private boolean isAnimated = false;
/**<p>0 - Home Tile</p>
 * <p>1 - Playlists Tile</p>
 * <p>2 - Albums Tile</p>
 * <p>3 - Favourite Tile</p> **/
    private int actualTile = 0;
    private int prevTile = 0;

    @FXML
    public void initialize() {
    }

    public void setHomeTile() {
        if(actualTile != 0) {
            actualTile = 0;
        }
    }

    public void setPlaylistsTile() {
        if(actualTile != 1) {
            actualTile = 1;
        }

    }

    public void setAlbumsTile() {
        if(actualTile != 2) {
            actualTile = 2;
        }
    }

    public void setFavouriteTile() {
        if(actualTile != 3) {
            actualTile = 3;
        }
    }
}
