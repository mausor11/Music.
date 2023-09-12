package org.main.controllers;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.main.DataBase;
import org.main.Default;
import org.main.ListCell;

import java.util.ArrayList;

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
        setFocusedListener();
    }
    private void setFocusedListener() {
        Default.libraryFocused.addListener(((observableValue, aBoolean, t1) -> {
            if(!t1) {
                if(prevCell != -1) {
                    listCells.get(prevCell).setIsFocused(false);
                }
                prevCell = -1;
            }
        }));
    }
    private void setListViewOnMouseClicked() {
        listView.setOnMouseClicked(event -> {
            Default.tileFocused.set(false);
            Default.libraryFocused.set(true);
            if(prevCell != listView.getSelectionModel().getSelectedIndex()) {
            if(prevCell != -1) {
                listCells.get(prevCell).setIsFocused(false);
            }
                listCells.get(listView.getSelectionModel().getSelectedIndex()).setIsFocused(true);
                prevCell = listView.getSelectionModel().getSelectedIndex();
                Default.albumID.set(listCells.get(listView.getSelectionModel().getSelectedIndex()).getDataBaseID());

            }

        });
    }
    public void plusEffect() {
        Default.blurEffect(plusButton,plusEffect);
    }
    private ArrayList<StackPane> templateAlbums() {
        ArrayList<Integer> ids = DataBase.getDataBase().getAllAlbumsID();
        ArrayList<StackPane> albums = new ArrayList<>();
        for(Integer id : ids) {
            ListCell listCell = new ListCell(listView, id, DataBase.getDataBase().getAlbumCover(id), DataBase.getDataBase().getAlbumName(id), DataBase.getDataBase().getAlbumArtistName(id), DataBase.getDataBase().getAlbumFeaturesName(id));
            albums.add(listCell.getCell());
            listCells.add(listCell);
        }

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
