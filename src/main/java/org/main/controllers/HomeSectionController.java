package org.main.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.main.AlbumTile;
import org.main.DataBase;

import java.util.ArrayList;

public class HomeSectionController {
    @FXML
    GridPane gridFirst;
    @FXML
    GridPane gridSecond;
    @FXML
    GridPane gridThird;
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
    GridPane gridPane;
    @FXML
    StackPane homeSection;
    @FXML
    ScrollPane homeScrollPane;
    private double scaleX = 1;
    private double scaleY = 1;
    private final ArrayList<AlbumTile> favourites = new ArrayList<>();
    private final ArrayList<AlbumTile> albums = new ArrayList<>();
    private final ArrayList<AlbumTile> playlists = new ArrayList<>();
    public static BooleanProperty isBack = new SimpleBooleanProperty(false);
    private BooleanProperty isFavourites = new SimpleBooleanProperty(false);
    private BooleanProperty isAlbums = new SimpleBooleanProperty(false);
    private BooleanProperty isPlaylists = new SimpleBooleanProperty(false);
    private double lastValue = 1002;
    public void initialize() {
        isBack.addListener(((observableValue, aBoolean, t1) -> {
            if(isBack.get()) {
                homeScrollPane.setVvalue(0);
                isBack.set(false);
            }
        }));
        makeTemplate(favourites);
        makeTemplate(albums);
        makeTemplate(playlists);
        makeDefault(gridFirst, isFavourites, favourites);
        makeDefault(gridSecond, isAlbums, albums);
        makeDefault(gridThird, isPlaylists, playlists);

        makeEffect(showAllPane1, showAllText1, showAllText1a, showAllText1Effect, isFavourites, gridFirst, favourites);
        makeEffect(showAllPane2, showAllText2, showAllText2a, showAllText2Effect, isAlbums, gridSecond, albums);
        makeEffect(showAllPane3, showAllText3, showAllText3a, showAllText3Effect, isPlaylists, gridThird, playlists);

    }

    private void makeTemplate(ArrayList<AlbumTile> covers) {
        if(covers.equals(favourites)) {
            ArrayList<Integer> ids = DataBase.getDataBase().getAllAlbumsFavourites();
            for(Integer id : ids) {
                AlbumTile albumTile = new AlbumTile(181, 211, DataBase.getDataBase().getAlbumName(id), DataBase.getDataBase().getAlbumArtistName(id), DataBase.getDataBase().getAlbumFeaturesName(id), DataBase.getDataBase().getAlbumCover(id));
                covers.add(albumTile);
            }
        } else if(covers.equals(albums)){
            ArrayList<Integer> ids = DataBase.getDataBase().getAllAlbumsID();
            for(Integer id : ids) {
                AlbumTile albumTile = new AlbumTile(181, 211, DataBase.getDataBase().getAlbumName(id), DataBase.getDataBase().getAlbumArtistName(id), DataBase.getDataBase().getAlbumFeaturesName(id), DataBase.getDataBase().getAlbumCover(id));
                covers.add(albumTile);
            }
        } else {
            ArrayList<Integer> ids = DataBase.getDataBase().getAllPlaylistsID();
            for(Integer id : ids) {
                AlbumTile albumTile = new AlbumTile(181, 211, DataBase.getDataBase().getPlaylistName(id), null, null, DataBase.getDataBase().getPlaylistCoverURL(id));
                covers.add(albumTile);
            }
        }




    }
    private void makeDefault(GridPane gridPane, BooleanProperty isShown, ArrayList<AlbumTile> covers) {
        for(int i=0;i<5 && i < covers.size();i++) {
            gridPane.addColumn(i, covers.get(i).getCoverArt());
        }

        homeSection.widthProperty().addListener((observableValue1, number, t2) -> {
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
                this.gridPane.getRowConstraints().get(takeIndex(gridPane)).setMinHeight(520);
                homeSection.widthProperty().addListener((observableValue1, number, t2) -> {
                    shownLookPlaylists(t2.doubleValue() - 50, gridPane, covers);
                    lastValue = t2.doubleValue();
                });
            } else {
                if(!gridPane.getChildren().isEmpty()) {
                    gridPane.getChildren().clear();
                    gridPane.getColumnConstraints().clear();
                    gridPane.getRowConstraints().clear();
                }
                this.gridPane.getRowConstraints().get(takeIndex(gridPane)).setMinHeight(250);
                homeSection.widthProperty().addListener((observableValue1, number, t2) -> {
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
            return 5;
        } else if(gridPane.equals(gridThird)) {
            return 8;
        } else {
            return -1;
        }
    }
    private void hiddenLookPlaylists(double W, GridPane gridPane, ArrayList<AlbumTile> covers) {
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
        this.gridPane.getRowConstraints().get(takeIndex(gridPane)).setMinHeight(211 * scaleY);
        for(int i = 0; i < columns && i < covers.size(); i++) {
            covers.get(i).resizeCoverArt(scaleX, scaleY);
            gridPane.add(covers.get(i).getCoverArt(), i, 0);
        }
    }
    private void shownLookPlaylists(double W, GridPane gridPane, ArrayList<AlbumTile> covers) {
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

        this.gridPane.getRowConstraints().get(takeIndex(gridPane)).setMinHeight(((rows * 211) * scaleY ) + (rows+1) * 10);

        for(int i=0;i<columns;i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints());
        }
        for(int y=0;y<rows;y++) {
            gridPane.getRowConstraints().add(new RowConstraints());
        }
        int columnCount = gridPane.getColumnCount();
        int r = 0;
        int c = 0;
        for(AlbumTile cover : covers) {
            if(c == columnCount) {
                r++;
                c = 0;
            }
            cover.resizeCoverArt(scaleX, scaleY);
            gridPane.add(cover.getCoverArt(), c, r);
            c++;
        }

    }
    private void makeEffect(StackPane pane, Label text, Label textA, Label textEffect, BooleanProperty isShown, GridPane gridPane, ArrayList<AlbumTile> covers) {
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

                    new KeyFrame(Duration.millis(100), new KeyValue(text.opacityProperty(), 1)),
                    new KeyFrame(Duration.millis(100), new KeyValue(textA.opacityProperty(), 0)),
                    new KeyFrame(Duration.millis(100), new KeyValue(textEffect.opacityProperty(), 0))
            );
            clickedTimeline.play();
            switchMode(gridPane, isShown, covers);
        });
    }
    private void switchMode(GridPane gridPane, BooleanProperty isShown, ArrayList<AlbumTile> covers) {
        if(isShown.get()) {
            isShown.set(false);
            hiddenLookPlaylists(lastValue - 50, gridPane, covers);

        } else {
            if(covers.size() > 5) {
                isShown.set(true);
                shownLookPlaylists(lastValue - 50, gridPane, covers);
            }

        }
    }


}
