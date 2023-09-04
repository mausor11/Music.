package org.main.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.main.CoverTile;
import org.main.Main;

import java.util.ArrayList;
import java.util.Objects;

public class HomeSectionController {
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
    }
    private void makeTemplate() {
        CoverTile coverTile1 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/ASTROWORLD.jpg")).toString());
        CoverTile coverTile2 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/Lost Souls.jpg")).toString());
        CoverTile coverTile3 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/Starboy.jpg")).toString());
        CoverTile coverTile4 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/ye.jpg")).toString());
        CoverTile coverTile5 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile6 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile7 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile8 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile9 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile10 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile11 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
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
        CoverTile coverTile1 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/ASTROWORLD.jpg")).toString());
        CoverTile coverTile2 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/Lost Souls.jpg")).toString());
        CoverTile coverTile3 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/Starboy.jpg")).toString());
        CoverTile coverTile4 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/ye.jpg")).toString());
        CoverTile coverTile5 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile6 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile7 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile8 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile9 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile10 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile11 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
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
        CoverTile coverTile1 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/ASTROWORLD.jpg")).toString());
        CoverTile coverTile2 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/Lost Souls.jpg")).toString());
        CoverTile coverTile3 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/Starboy.jpg")).toString());
        CoverTile coverTile4 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/ye.jpg")).toString());
        CoverTile coverTile5 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile6 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile7 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile8 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile9 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile10 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        CoverTile coverTile11 = new CoverTile(Objects.requireNonNull(Main.class.getResource("cover-images/albums/utopia.jpg")).toString());
        tiles3.add(coverTile1);
        tiles3.add(coverTile2);
        tiles3.add(coverTile3);
        tiles3.add(coverTile4);
        tiles3.add(coverTile5);
        tiles3.add(coverTile6);
        tiles3.add(coverTile7);
        tiles3.add(coverTile8);
        tiles3.add(coverTile9);
        tiles3.add(coverTile10);
        tiles3.add(coverTile11);
    }
    private void math(double sectionWidth) {
        double tileWidth = 182;
        int x = (int) ((sectionWidth + (double) 10) / (tileWidth + (double) 10));
        double y = (sectionWidth + (double) 10) / (tileWidth + (double) 10);
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

}
