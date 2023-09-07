package org.main.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.main.Main;
import org.main.PlaylistTile;

import java.util.ArrayList;
import java.util.Objects;


public class PlaylistSectionController {
    @FXML
    Label showAllText1;
    @FXML
    Label showAllText1a;
    @FXML
    Label showAllText1Effect;
    @FXML
    StackPane showAllPane1;
    @FXML
    StackPane playlistSection;
    @FXML
    GridPane gridFirst;
    @FXML
    GridPane gridSecond;
    @FXML
    GridPane gridThird;
    @FXML
    GridPane gridFourth;
    @FXML
    GridPane mainGrid;
    private ArrayList<PlaylistTile> covers;
    private double scaleX;
    private double scaleY;
    private BooleanProperty isShown = new SimpleBooleanProperty(false);
    private double lastValue = 1002;
    public void initialize() {
        makeTemplate(10);
        makeDefault();

        playlistSection.widthProperty().addListener((observableValue1, number, t2) -> {
            hiddenLookPlaylists(t2.doubleValue() - 50, gridFirst);
            lastValue = t2.doubleValue();
        });
        isShown.addListener(((observableValue, aBoolean, t1) -> {
            if(t1) {
                if(!gridFirst.getChildren().isEmpty()) {
                    gridFirst.getChildren().clear();
                    gridFirst.getColumnConstraints().clear();
                    gridFirst.getRowConstraints().clear();
                }
                mainGrid.getRowConstraints().get(2).setMinHeight(520);
                playlistSection.widthProperty().addListener((observableValue1, number, t2) -> {
                    shownLookPlaylists(t2.doubleValue() - 50, gridFirst);
                    lastValue = t2.doubleValue();
                });
            } else {
                if(!gridFirst.getChildren().isEmpty()) {
                    gridFirst.getChildren().clear();
                    gridFirst.getColumnConstraints().clear();
                    gridFirst.getRowConstraints().clear();
                }
                mainGrid.getRowConstraints().get(2).setMinHeight(250);
                playlistSection.widthProperty().addListener((observableValue1, number, t2) -> {
                    hiddenLookPlaylists(t2.doubleValue() - 50, gridFirst);
                    lastValue = t2.doubleValue();
                });
            }
        }));

        makeEffect(showAllPane1, showAllText1, showAllText1a, showAllText1Effect, isShown);

    }
    private void makeTemplate(int many) {
        ArrayList<String> artists = new ArrayList<>();
        artists.add("Kanye West");
        artists.add("Vory");
        artists.add("Travis Scott");
        artists.add("Joji");
        artists.add("Tory Lanez");
        ArrayList<String> artists1 = new ArrayList<>();
        artists1.add("Kanye West");
        artists1.add("Vory");

        covers = new ArrayList<>();
            PlaylistTile playlistTile1 = new PlaylistTile(180, 250,"Playlist 1", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/ASTROWORLD.jpg")).toString());
            PlaylistTile playlistTile2 = new PlaylistTile(180, 250,"Playlist 2", artists, Objects.requireNonNull(Main.class.getResource("cover-images/albums/ASTROWORLD.jpg")).toString());
            PlaylistTile playlistTile3 = new PlaylistTile(180, 250,"Playlist 3", artists1, Objects.requireNonNull(Main.class.getResource("cover-images/albums/ASTROWORLD.jpg")).toString());
            PlaylistTile playlistTile4 = new PlaylistTile(180, 250,"Playlist 4", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/ASTROWORLD.jpg")).toString());
            PlaylistTile playlistTile5 = new PlaylistTile(180, 250,"Playlist 5", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/ASTROWORLD.jpg")).toString());
            PlaylistTile playlistTile6 = new PlaylistTile(180, 250,"Playlist 5", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/ASTROWORLD.jpg")).toString());
            covers.add(playlistTile1);
            covers.add(playlistTile2);
            covers.add(playlistTile3);
            covers.add(playlistTile4);
            covers.add(playlistTile5);
            covers.add(playlistTile6);
    }
    private void makeDefault() {
        for(int i=0;i<5 && i < covers.size();i++) {
            gridFirst.addColumn(i, covers.get(i).getCoverArt());
        }
    }
    private void hiddenLookPlaylists(double W, GridPane gridPane) {
        if(!gridPane.getChildren().isEmpty()) {
            gridPane.getChildren().clear();
            gridPane.getColumnConstraints().clear();
            gridPane.getRowConstraints().clear();
        }
        double recW = 180;
        double b = 10;
        int columns = (int) ((W + b) / (recW + b));
        double freePx = (W - (columns * recW + (columns -1)* b));
        double k = freePx /columns;
        if(columns < covers.size()) {
            this.scaleX = (recW + k) / recW;
            this.scaleY = (recW + k) / recW;
        }
        if(columns == covers.size()) {
            this.scaleX = 1;
            this.scaleY = 1;
        }

        for(int i=0;i<columns;i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints());
        }
        gridPane.getRowConstraints().add(new RowConstraints());
        mainGrid.getRowConstraints().get(2).setMinHeight(250 * scaleY);
        for(int i = 0; i < columns && i < covers.size(); i++) {
            covers.get(i).resizeCoverArt(scaleX, scaleY);
            gridPane.add(covers.get(i).getCoverArt(), i, 0);
        }
    }
    private void shownLookPlaylists(double W, GridPane gridPane) {
        if(!gridPane.getChildren().isEmpty()) {
            gridPane.getChildren().clear();
            gridPane.getColumnConstraints().clear();
            gridPane.getRowConstraints().clear();
        }
        double recW = 180;
        double b = 10;
        int rows = 0;
        int columns = (int) ((W + b) / (recW + b));
        double freePx = (W - (columns * recW + (columns -1)* b));
        double k = freePx /columns;
        if(columns < covers.size()) {
            this.scaleX = (recW + k) / recW;
            this.scaleY = (recW + k) / recW;
        }
        if(columns == covers.size()) {
            this.scaleX = 1;
            this.scaleY = 1;
        }
        if(covers.size() % columns == 0) {
            rows = covers.size() / columns;
        } else {
            rows = covers.size() / columns + 1;
        }

        if(rows == 1) {
            mainGrid.getRowConstraints().get(2).setMinHeight(250 * scaleY);
        } else {
            mainGrid.getRowConstraints().get(2).setMinHeight(520 * scaleY);
        }
        for(int i=0;i<columns;i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints());
        }
        for(int y=0;y<rows;y++) {
            gridPane.getRowConstraints().add(new RowConstraints());
        }
        int columnCount = gridPane.getColumnCount();
        int r = 0;
        int c = 0;
        for(PlaylistTile cover : covers) {
            if(c == columnCount) {
                r++;
                c = 0;
            }
            cover.resizeCoverArt(scaleX, scaleY);
            gridPane.add(cover.getCoverArt(), c, r);
            c++;
        }

    }
    private void makeEffect(StackPane pane, Label text, Label textA, Label textEffect, BooleanProperty isShown) {
        pane.setOnMouseEntered(event1 -> {
            Timeline enterTimeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(text.opacityProperty(), text.getOpacity())),
                    new KeyFrame(Duration.ZERO, new KeyValue(textA.opacityProperty(), textA.getOpacity())),
                    new KeyFrame(Duration.ZERO, new KeyValue(textEffect.opacityProperty(), textEffect.getOpacity())),

                    new KeyFrame(Duration.millis(100), new KeyValue(text.opacityProperty(), 0)),
                    new KeyFrame(Duration.millis(100), new KeyValue(textA.opacityProperty(), 1)),
                    new KeyFrame(Duration.millis(100), new KeyValue(textEffect.opacityProperty(), 1))
            );
            enterTimeline.play();
            pane.setOnMouseExited(event2 -> {
                Timeline exitTimeline = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(text.opacityProperty(), text.getOpacity())),
                        new KeyFrame(Duration.ZERO, new KeyValue(textA.opacityProperty(), textA.getOpacity())),
                        new KeyFrame(Duration.ZERO, new KeyValue(textEffect.opacityProperty(), textEffect.getOpacity())),

                        new KeyFrame(Duration.millis(100), new KeyValue(text.opacityProperty(), 1)),
                        new KeyFrame(Duration.millis(100), new KeyValue(textA.opacityProperty(), 0)),
                        new KeyFrame(Duration.millis(100), new KeyValue(textEffect.opacityProperty(), 0))
                );
                exitTimeline.play();
            });
        });
        pane.setOnMouseClicked(event3 -> {
            Timeline clickedTimeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(text.opacityProperty(), text.getOpacity())),
                    new KeyFrame(Duration.ZERO, new KeyValue(textA.opacityProperty(), textA.getOpacity())),
                    new KeyFrame(Duration.ZERO, new KeyValue(textEffect.opacityProperty(), textEffect.getOpacity())),

                    new KeyFrame(Duration.millis(20), new KeyValue(text.opacityProperty(), 1)),
                    new KeyFrame(Duration.millis(20), new KeyValue(textA.opacityProperty(), 0)),
                    new KeyFrame(Duration.millis(20), new KeyValue(textEffect.opacityProperty(), 0)),

                    new KeyFrame(Duration.millis(200), new KeyValue(text.opacityProperty(), 0)),
                    new KeyFrame(Duration.millis(200), new KeyValue(textA.opacityProperty(), 1)),
                    new KeyFrame(Duration.millis(200), new KeyValue(textEffect.opacityProperty(), 1))
            );
            clickedTimeline.play();
            switchMode(isShown);
        });
    }
    private void switchMode(BooleanProperty isShown) {
        if(isShown.get()) {
            this.isShown.set(false);
            hiddenLookPlaylists(lastValue - 50, gridFirst);

        } else {
            this.isShown.set(true);
            shownLookPlaylists(lastValue - 50, gridFirst);
        }
    }
}
