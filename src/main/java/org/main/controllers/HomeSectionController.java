package org.main.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.main.AlbumTile;
import org.main.Main;

import java.util.ArrayList;
import java.util.Objects;

public class HomeSectionController {
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
    HBox favouriteTiles;
    @FXML
    HBox albumTiles;
    @FXML
    HBox playlistTiles;
    @FXML
    GridPane gridPane;
    @FXML
    StackPane homeSection;
    private double scaleX = 1;
    private double scaleY = 1;
    private final ArrayList<AlbumTile> favourites = new ArrayList<>();
    private final ArrayList<AlbumTile> albums = new ArrayList<>();
    private final ArrayList<AlbumTile> playlists = new ArrayList<>();

    public void initialize() {
        makeTemplate(favourites);
        makeTemplate(albums);
        makeTemplate(playlists);

        makeListener(favourites, favouriteTiles);
        makeListener(albums, albumTiles);
        makeListener(playlists, playlistTiles);

        makeEffect(showAllPane1, showAllText1, showAllText1a, showAllText1Effect);
        makeEffect(showAllPane2, showAllText2, showAllText2a, showAllText2Effect);
        makeEffect(showAllPane3, showAllText3, showAllText3a, showAllText3Effect);
    }
    private void makeTemplate(ArrayList<AlbumTile> covers) {
        ArrayList<String> features = new ArrayList<>();
        features.add("21 Savage");
        AlbumTile albumTile1 = new AlbumTile(181, 211,"ASTROWORLD", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/ASTROWORLD.jpg")).toString());
        AlbumTile albumTile2 = new AlbumTile(181, 211,"Lost Souls", "Vory", features, Objects.requireNonNull(Main.class.getResource("cover-images/albums/Lost Souls.jpg")).toString());
        AlbumTile albumTile3 = new AlbumTile(181, 211,"Starboy", "The Weeknd", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/Starboy.jpg")).toString());
        AlbumTile albumTile4 = new AlbumTile(181, 211,"ye", "Kanye West", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/ye.jpg")).toString());
        AlbumTile albumTile5 = new AlbumTile(181, 211,"utopia", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        AlbumTile albumTile6 = new AlbumTile(181, 211,"utopia", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        AlbumTile albumTile7 = new AlbumTile(181, 211,"utopia", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        AlbumTile albumTile8 = new AlbumTile(181, 211,"utopia", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        AlbumTile albumTile9 = new AlbumTile(181, 211,"utopia", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        AlbumTile albumTile10 = new AlbumTile(181, 211,"utopia", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        AlbumTile albumTile11 = new AlbumTile(181, 211,"utopia", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        covers.add(albumTile1);
        covers.add(albumTile2);
        covers.add(albumTile3);
        covers.add(albumTile4);
        covers.add(albumTile5);
        covers.add(albumTile6);
        covers.add(albumTile7);
        covers.add(albumTile8);
        covers.add(albumTile9);
        covers.add(albumTile10);
        covers.add(albumTile11);
    }
    private int math(ArrayList<AlbumTile> covers, double sectionWidth) {
        double tileWidth = 182;
        int x = (int) ((sectionWidth + (double) 10) / (tileWidth + (double) 10));
        double freePx = (sectionWidth - (x* tileWidth + (x-1)* (double) 10));
        double k = freePx / x;
        double scaleX = (tileWidth + k) / tileWidth;
        double scaleY = (tileWidth + k) / tileWidth;
        if(x <= covers.size()) {
            this.scaleX = scaleX;
            this.scaleY = scaleY;
        }
        if(x == covers.size()) {
            this.scaleX = 1;
            this.scaleY = 1;
        }
        return x;

    }
    private void makeListener(ArrayList<AlbumTile> covers, HBox box) {
        homeSection.widthProperty().addListener(((observableValue, number, t1) -> {
            int k = math(covers, t1.doubleValue() - 50);
            gridPane.getRowConstraints().get(2).setMinHeight(211 * scaleY);
            gridPane.getRowConstraints().get(5).setMinHeight(211 * scaleY);
            gridPane.getRowConstraints().get(8).setMinHeight(211 * scaleY);

            if(!box.getChildren().isEmpty()) {
                box.getChildren().clear();
            }

            for(int i=0;i < k && i < covers.size(); i++)  {
                box.getChildren().add(covers.get(i).getCoverArt());
                covers.get(i).resizeCoverArt(scaleX, scaleY);
            }
        }));
    }
    private void makeEffect(StackPane pane, Label text, Label textA, Label textEffect) {
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
    }


}
