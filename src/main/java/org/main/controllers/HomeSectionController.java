package org.main.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.main.CoverTile;
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
    private int manyTiles = 5;
    private final ArrayList<CoverTile> tiles = new ArrayList<>();
    private final ArrayList<CoverTile> tiles2 = new ArrayList<>();
    private final ArrayList<CoverTile> tiles3 = new ArrayList<>();

    public void initialize() {
        makeTemplate();
        makeTemplate2();
        makeTemplate3();
        makeListener();
        makeEffect(showAllPane1, showAllText1, showAllText1a, showAllText1Effect);
        makeEffect(showAllPane2, showAllText2, showAllText2a, showAllText2Effect);
        makeEffect(showAllPane3, showAllText3, showAllText3a, showAllText3Effect);
    }
    private void makeTemplate() {
        ArrayList<String> features = new ArrayList<>();
        features.add("21 Savage");
        CoverTile coverTile1 = new CoverTile("ASTROWORLD", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/ASTROWORLD.jpg")).toString());
        CoverTile coverTile2 = new CoverTile("Lost Souls", "Vory", features, Objects.requireNonNull(Main.class.getResource("cover-images/albums/Lost Souls.jpg")).toString());
        CoverTile coverTile3 = new CoverTile("Starboy", "The Weeknd", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/Starboy.jpg")).toString());
        CoverTile coverTile4 = new CoverTile("ye", "Kanye West", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/ye.jpg")).toString());
        CoverTile coverTile5 = new CoverTile("utopia", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile6 = new CoverTile("utopia", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile7 = new CoverTile("utopia", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile8 = new CoverTile("utopia", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile9 = new CoverTile("utopia", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile10 = new CoverTile("utopia", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile11 = new CoverTile("utopia", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        tiles.add(coverTile1);
        tiles.add(coverTile2);
        tiles.add(coverTile3);
        tiles.add(coverTile4);
        tiles.add(coverTile5);
        tiles.add(coverTile6);
        tiles.add(coverTile7);
        tiles.add(coverTile8);
        tiles.add(coverTile9);
        tiles.add(coverTile10);
        tiles.add(coverTile11);
    }
    private void makeTemplate2() {
        CoverTile coverTile1 = new CoverTile("ASTROWORLD", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/ASTROWORLD.jpg")).toString());
        CoverTile coverTile2 = new CoverTile("Lost Souls", "Vory", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/Lost Souls.jpg")).toString());
        CoverTile coverTile3 = new CoverTile("Starboy", "The Weeknd", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/Starboy.jpg")).toString());
        CoverTile coverTile4 = new CoverTile("ye", "Kanye West", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/ye.jpg")).toString());
        CoverTile coverTile5 = new CoverTile("utopia", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile6 = new CoverTile("utopia", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile7 = new CoverTile("utopia", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile8 = new CoverTile("utopia", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile9 = new CoverTile("utopia", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile10 = new CoverTile("utopia", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile11 = new CoverTile("utopia", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        tiles2.add(coverTile1);
        tiles2.add(coverTile2);
        tiles2.add(coverTile3);
        tiles2.add(coverTile4);
        tiles2.add(coverTile5);
        tiles2.add(coverTile6);
        tiles2.add(coverTile7);
        tiles2.add(coverTile8);
        tiles2.add(coverTile9);
        tiles2.add(coverTile10);
        tiles2.add(coverTile11);
    }
    private void makeTemplate3() {
        CoverTile coverTile1 = new CoverTile("ASTROWORLD", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/ASTROWORLD.jpg")).toString());
        CoverTile coverTile2 = new CoverTile("Lost Souls", "Vory", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/Lost Souls.jpg")).toString());
        CoverTile coverTile3 = new CoverTile("Starboy", "The Weeknd", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/Starboy.jpg")).toString());
        CoverTile coverTile4 = new CoverTile("ye", "Kanye West", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/ye.jpg")).toString());
        CoverTile coverTile5 = new CoverTile("utopia", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile6 = new CoverTile("utopia", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile7 = new CoverTile("utopia", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile8 = new CoverTile("utopia", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile9 = new CoverTile("utopia", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile10 = new CoverTile("utopia", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile11 = new CoverTile("utopia", "Travis Scott", null, Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        tiles3.add(coverTile11);
        tiles3.add(coverTile10);
        tiles3.add(coverTile9);
        tiles3.add(coverTile8);
        tiles3.add(coverTile7);
        tiles3.add(coverTile6);
        tiles3.add(coverTile5);
        tiles3.add(coverTile4);
        tiles3.add(coverTile3);
        tiles3.add(coverTile2);
        tiles3.add(coverTile1);
    }
    private void math(double sectionWidth) {
        double tileWidth = 182;
        int x = (int) ((sectionWidth + (double) 10) / (tileWidth + (double) 10));
        double freePx = (sectionWidth - (x* tileWidth + (x-1)* (double) 10));
        double k = freePx / x;
        double scaleX = (tileWidth + k) / tileWidth;
        double scaleY = (tileWidth + k) / tileWidth;
        if(x <= tiles.size()) {
            this.scaleX = scaleX;
            this.scaleY = scaleY;
        }
        if(x == tiles.size()) {
            this.scaleX = 1;
            this.scaleY = 1;
        }
        this.manyTiles = x;

    }
    private void makeListener() {
        homeSection.widthProperty().addListener(((observableValue, number, t1) -> {
            math(t1.doubleValue() - 50);
            gridPane.getRowConstraints().get(2).setMinHeight(211 * scaleY);
            gridPane.getRowConstraints().get(5).setMinHeight(211 * scaleY);
            gridPane.getRowConstraints().get(8).setMinHeight(211 * scaleY);

            if(!favouriteTiles.getChildren().isEmpty()) {
                favouriteTiles.getChildren().clear();
            }
            if(!albumTiles.getChildren().isEmpty()) {
                albumTiles.getChildren().clear();
            }
            if(!playlistTiles.getChildren().isEmpty()) {
                playlistTiles.getChildren().clear();
            }

            for(int i=0;i< manyTiles && i < tiles.size(); i++)  {
                favouriteTiles.getChildren().add(tiles.get(i).getCoverArt());
                albumTiles.getChildren().add(tiles2.get(i).getCoverArt());
                playlistTiles.getChildren().add(tiles3.get(i).getCoverArt());
                tiles.get(i).resizeCoverArt(scaleX, scaleY);
                tiles2.get(i).resizeCoverArt(scaleX, scaleY);
                tiles3.get(i).resizeCoverArt(scaleX, scaleY);
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
