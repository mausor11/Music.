package org.main.controllers;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.main.Default;
import org.main.ListCell;
import org.main.Main;

import java.util.ArrayList;
import java.util.Objects;

public class LibraryController {
    @FXML
    ImageView plusEffect;
    @FXML
    ImageView plusButton;
    @FXML
    ListView<StackPane> listView;
    ArrayList<ListCell> listCells = new ArrayList<>();
    private int prevCell = -1;
    public void initialize() {
        for(StackPane album : templateAlbums()) {
            listView.getItems().add(album);
        }
        setListViewOnMouseClicked();

    }
    private void setListViewOnMouseClicked() {
        listView.setOnMouseClicked(event -> {
            if(prevCell != -1) {
                listCells.get(prevCell).setIsFocused(false);
            }
            listCells.get(listView.getSelectionModel().getSelectedIndex()).setIsFocused(true);
            prevCell = listView.getSelectionModel().getSelectedIndex();
        });
    }
    public void plusEffect() {
        Default.blurEffect(plusButton,plusEffect);
    }
    private ArrayList<StackPane> templateAlbums() {
        ArrayList<String> features = new ArrayList<>();
        features.add("21 Savage");
        ArrayList<StackPane> albums = new ArrayList<>();
        ListCell listCell1 = new ListCell(listView, new Image(Objects.requireNonNull(Main.class.getResourceAsStream("cover-images/albums/ASTROWORLD.jpg"))), "ASTROWORLD", "Travis Scott", null);
        ListCell listCell2 = new ListCell(listView, new Image(Objects.requireNonNull(Main.class.getResourceAsStream("cover-images/albums/Lost Souls.jpg"))), "Lost Souls", "Vory", null);
        ListCell listCell3 = new ListCell(listView, new Image(Objects.requireNonNull(Main.class.getResourceAsStream("cover-images/albums/SMTHREENS.jpg"))), "SMTHREENS", "Joji", null);
        ListCell listCell4 = new ListCell(listView, new Image(Objects.requireNonNull(Main.class.getResourceAsStream("cover-images/albums/Starboy.jpg"))), "Starboy", "The Weeknd", null);
        ListCell listCell5 = new ListCell(listView, new Image(Objects.requireNonNull(Main.class.getResourceAsStream("cover-images/albums/The Off-Season.jpg"))), "The Off-Season", "J.Cole", null);
        ListCell listCell6 = new ListCell(listView, new Image(Objects.requireNonNull(Main.class.getResourceAsStream("cover-images/albums/utopia.jpg"))), "utopia", "Travis Scott", null);
        ListCell listCell7 = new ListCell(listView, new Image(Objects.requireNonNull(Main.class.getResourceAsStream("cover-images/albums/WholeLottaRed.jpg"))), "WholeLottaRed", "Playboi Carti", null);
        ListCell listCell8 = new ListCell(listView, new Image(Objects.requireNonNull(Main.class.getResourceAsStream("cover-images/albums/ye.jpg"))), "ye", "Travis Scott", null);
        ListCell listCell9 = new ListCell(listView, new Image(Objects.requireNonNull(Main.class.getResourceAsStream("cover-images/albums/NOT ALL HEROES WEAR CAPES.jpg"))), "NOT ALL HEROES WEAR CAPES", "Metro Boomin", features);
        albums.add(listCell1.getCell());
        albums.add(listCell2.getCell());
        albums.add(listCell3.getCell());
        albums.add(listCell4.getCell());
        albums.add(listCell5.getCell());
        albums.add(listCell6.getCell());
        albums.add(listCell7.getCell());
        albums.add(listCell8.getCell());
        albums.add(listCell9.getCell());

        listCells.add(listCell1);
        listCells.add(listCell2);
        listCells.add(listCell3);
        listCells.add(listCell4);
        listCells.add(listCell5);
        listCells.add(listCell6);
        listCells.add(listCell7);
        listCells.add(listCell8);
        listCells.add(listCell9);

        return albums;
    }
    public void setPlus() {
        plusButton.setRotate(0);
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(150), plusButton);
        rotateTransition.setInterpolator(Interpolator.EASE_OUT);
        rotateTransition.setByAngle(180);
        rotateTransition.setAutoReverse(false);
        rotateTransition.play();
        GaussianBlur gaussianBlur = new GaussianBlur(0);
        plusButton.setEffect(gaussianBlur);
        Timeline timeline1 = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(gaussianBlur.radiusProperty(), 0)),
                new KeyFrame(Duration.millis(75), new KeyValue(gaussianBlur.radiusProperty(), 7.7)),
                new KeyFrame(Duration.millis(150), new KeyValue(gaussianBlur.radiusProperty(), 0))
        );
        timeline1.play();
    }
}
