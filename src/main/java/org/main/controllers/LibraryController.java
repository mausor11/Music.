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

import java.net.URISyntaxException;
import java.util.ArrayList;

public class LibraryController {
    @FXML
    ImageView plusEffect;
    @FXML
    ImageView plusButton;
    @FXML
    ListView<StackPane> listView;
    public static ArrayList<ListCell> listCells = new ArrayList<>();

    public static int prevCell = -1;
    public void initialize() throws URISyntaxException {
        for(StackPane album : templateAlbums()) {
            listView.getItems().add(album);
        }
        for(StackPane playlist : templatePlaylists()) {
            listView.getItems().add(playlist);
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
            setListView(listView.getSelectionModel().getSelectedIndex());
        });
    }
    public static void setListView(int cellID) {
        Default.tileFocused.set(false);
        Default.libraryFocused.set(true);
        if(prevCell != cellID) {
            if(prevCell != -1) {
                listCells.get(prevCell).setIsFocused(false);
            }
            listCells.get(cellID).setIsFocused(true);
            prevCell = cellID;
            Default.Type = listCells.get(cellID).type();
            Default.albumID.set(listCells.get(cellID).getDataBaseID());
            TracklistSectionController.isChange.set(true);

        }
    }
    public void plusEffect() {
        Default.blurEffect(plusButton,plusEffect);
    }
    private ArrayList<StackPane> templateAlbums() {
        ArrayList<Integer> ids = DataBase.getDataBase().getAllAlbumsID();
        ArrayList<StackPane> albums = new ArrayList<>();
        for(Integer id : ids) {
            ListCell listCell = new ListCell(0, listView, id, DataBase.getDataBase().getAlbumCover(id), DataBase.getDataBase().getAlbumName(id), DataBase.getDataBase().getAlbumArtistName(id), DataBase.getDataBase().getAlbumFeaturesName(id));
            albums.add(listCell.getCell());
            listCells.add(listCell);
        }

        return albums;
    }
    private ArrayList<StackPane> templatePlaylists() {
        ArrayList<Integer> ids = DataBase.getDataBase().getAllPlaylistsID();
        ArrayList<StackPane> playlists = new ArrayList<>();
        for(Integer id : ids) {
            ListCell listCell = new ListCell(1, listView, id, DataBase.getDataBase().getPlaylistCoverURL(id), DataBase.getDataBase().getPlaylistName(id), DataBase.getDataBase().getAlbumUserName(id), null);
            playlists.add(listCell.getCell());
            listCells.add(listCell);
        }
        return playlists;
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
