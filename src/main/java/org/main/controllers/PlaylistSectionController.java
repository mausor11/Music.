package org.main.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.main.DataBase;
import org.main.PlaylistTile;

import java.util.ArrayList;


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
    Label showAllText2;
    @FXML
    Label showAllText2a;
    @FXML
    Label showAllText2Effect;
    @FXML
    StackPane showAllPane2;
    @FXML
    Label showAllText3;
    @FXML
    Label showAllText3a;
    @FXML
    Label showAllText3Effect;
    @FXML
    StackPane showAllPane3;
    @FXML
    Label showAllText4;
    @FXML
    Label showAllText4a;
    @FXML
    Label showAllText4Effect;
    @FXML
    StackPane showAllPane4;
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
    @FXML
    ScrollPane playlistScrollPane;
    private double scaleX;
    private double scaleY;
    private final BooleanProperty isShownFirst = new SimpleBooleanProperty(false);
    private final BooleanProperty isShownSecond = new SimpleBooleanProperty(false);
    private final BooleanProperty isShownThird = new SimpleBooleanProperty(false);
    private final BooleanProperty isShownFourth = new SimpleBooleanProperty(false);
    public static BooleanProperty isBack = new SimpleBooleanProperty(false);
    private double lastValue = 1002;
    public void initialize() {
        isBack.addListener(((observableValue, aBoolean, t1) -> {
            if(isBack.get()) {
                playlistScrollPane.setVvalue(0);
                isBack.set(false);
            }
        }));
        ArrayList<PlaylistTile> covers1 = makeTemplate(20);
        makeDefault(gridFirst, isShownFirst, covers1);
        ArrayList<PlaylistTile> covers2 = makeTemplate(4);
        makeDefault(gridSecond, isShownSecond, covers2);
        ArrayList<PlaylistTile> covers3 = makeTemplate(5);
        makeDefault(gridThird, isShownThird, covers3);
        ArrayList<PlaylistTile> covers4 = makeTemplate(1);
        makeDefault(gridFourth, isShownFourth, covers4);

        makeEffect(showAllPane1, showAllText1, showAllText1a, showAllText1Effect, isShownFirst, gridFirst, covers1);
        makeEffect(showAllPane2, showAllText2, showAllText2a, showAllText2Effect, isShownSecond, gridSecond, covers2);
        makeEffect(showAllPane3, showAllText3, showAllText3a, showAllText3Effect, isShownThird, gridThird, covers3);
        makeEffect(showAllPane4, showAllText4, showAllText4a, showAllText4Effect, isShownFourth, gridFourth, covers4);

    }
    private ArrayList<PlaylistTile> makeTemplate(int many) {
        ArrayList<String> artists = new ArrayList<>();
        artists.add("Kanye West");
        artists.add("Vory");
        artists.add("Travis Scott");
        artists.add("Joji");
        artists.add("Tory Lanez");
        ArrayList<String> artists1 = new ArrayList<>();
        artists1.add("Kanye West");
        artists1.add("Vory");
        ArrayList<PlaylistTile> c = new ArrayList<>();

        ArrayList<Integer> IDs = DataBase.getDataBase().getAllPlaylistsID();

        for(Integer ID : IDs) {
            PlaylistTile playlistTile = new PlaylistTile(180, 250, DataBase.getDataBase().getPlaylistName(ID), DataBase.getDataBase().getAllPlaylistArtists(ID), DataBase.getDataBase().getPlaylistCoverURL(ID), ID);
                    c.add(playlistTile);
        }
        return c;
    }
    private void makeDefault(GridPane gridPane, BooleanProperty isShown, ArrayList<PlaylistTile> covers) {
        for(int i=0;i<5 && i < covers.size();i++) {
            gridPane.addColumn(i, covers.get(i).getCoverArt());
        }

        playlistSection.widthProperty().addListener((observableValue1, number, t2) -> {
            hiddenLookPlaylists(t2.doubleValue() - 50, gridPane, covers);
            lastValue = t2.doubleValue();
        });

        isShown.addListener(((observableValue, aBoolean, t1) -> {
            if(t1) {
                if(!gridPane.getChildren().isEmpty()) {
                    gridPane.getChildren().clear();
                    gridPane.getColumnConstraints().clear();
                    gridPane.getRowConstraints().clear();
                }
                mainGrid.getRowConstraints().get(takeIndex(gridPane)).setMinHeight(520);
                playlistSection.widthProperty().addListener((observableValue1, number, t2) -> {
                    shownLookPlaylists(t2.doubleValue() - 50, gridPane, covers);
                    lastValue = t2.doubleValue();
                });
            } else {
                if(!gridPane.getChildren().isEmpty()) {
                    gridPane.getChildren().clear();
                    gridPane.getColumnConstraints().clear();
                    gridPane.getRowConstraints().clear();
                }
                mainGrid.getRowConstraints().get(takeIndex(gridPane)).setMinHeight(250);
                playlistSection.widthProperty().addListener((observableValue1, number, t2) -> {
                    hiddenLookPlaylists(t2.doubleValue() - 50, gridPane, covers);
                    lastValue = t2.doubleValue();
                });
            }
        }));
    }
    private int takeIndex(GridPane gridPane) {
        if(gridPane.equals(gridFirst)) {
            return 2;
        } else if(gridPane.equals(gridSecond)) {
            return 4;
        } else if(gridPane.equals(gridThird)) {
            return 6;
        } else if(gridPane.equals(gridFourth)) {
            return 8;
        } else {
            return -1;
        }
    }
    private void hiddenLookPlaylists(double W, GridPane gridPane, ArrayList<PlaylistTile> covers) {
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
        if(columns >= covers.size()) {
            this.scaleX = 1;
            this.scaleY = 1;
        }

        for(int i=0;i<columns;i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints());
        }
        gridPane.getRowConstraints().add(new RowConstraints());
        mainGrid.getRowConstraints().get(takeIndex(gridPane)).setMinHeight(250 * scaleY);
        for(int i = 0; i < columns && i < covers.size(); i++) {
            covers.get(i).resizeCoverArt(scaleX, scaleY);
            gridPane.add(covers.get(i).getCoverArt(), i, 0);
        }
    }
    private void shownLookPlaylists(double W, GridPane gridPane, ArrayList<PlaylistTile> covers) {
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
        if(columns >= covers.size()) {
            this.scaleX = 1;
            this.scaleY = 1;
        }
        if(covers.size() % columns == 0) {
            rows = covers.size() / columns;
        } else {
            rows = covers.size() / columns + 1;
        }

            mainGrid.getRowConstraints().get(takeIndex(gridPane)).setMinHeight(((rows * 250) * scaleY ) + (rows+1) * 10);

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
    private void makeEffect(StackPane pane, Label text, Label textA, Label textEffect, BooleanProperty isShown, GridPane gridPane, ArrayList<PlaylistTile> covers) {
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
            switchMode(gridPane, isShown, covers);
        });
    }
    private void switchMode(GridPane gridPane, BooleanProperty isShown, ArrayList<PlaylistTile> covers) {
        if(isShown.get()) {
            isShown.set(false);
            hiddenLookPlaylists(lastValue - 50, gridPane, covers);

        } else {
            isShown.set(true);
            shownLookPlaylists(lastValue - 50, gridPane, covers);
        }
    }
}
