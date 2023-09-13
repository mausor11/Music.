package org.main.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.main.*;

import java.io.IOException;
import java.util.ArrayList;

public class TracklistSectionController {
    @FXML
    ImageView tracklistBackground;
    @FXML
    ImageView coverImg;
    @FXML
    Label titleLabel;
    @FXML
    Label infoLabel;
    @FXML
    StackPane tracklistSection;
    @FXML
    ScrollPane sectionPane;
    @FXML
    ListView<StackPane> trackView;
    @FXML
    GridPane mainGrid;
    private ScrollPane section;
    private ImageView background;
    private Image cover;
    private ArrayList<TrackCell> trackCells = new ArrayList<>();
    public void initialize() {
        Default.albumID.addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() != 0) {
                //todo: add database album type
                try {
                    initializeSection("Album", DataBase.getDataBase().getAlbumName(newValue.intValue()), DataBase.getDataBase().getAlbumArtistName(newValue.intValue()), DataBase.getDataBase().getAlbumFeaturesName(newValue.intValue()), DataBase.getDataBase().getAlbumCover(newValue.intValue()), DataBase.getDataBase().getAlbumTracklist(newValue.intValue()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        StageHolder.getPrimaryStage().widthProperty().addListener(((observableValue, number, t1) -> {
            resizeBackground(t1.doubleValue() - 317, StageHolder.getPrimaryStage().getHeight() - 240);
        }));
        StageHolder.getPrimaryStage().heightProperty().addListener(((observableValue1, number1, t11) -> {
            resizeBackground(StageHolder.getPrimaryStage().getWidth() - 317, t11.doubleValue() - 240);
        }));

    }
    private void initializeSection(String type, String title, String artist, ArrayList<String> features, String imgURL, ArrayList<Track> tracklist) throws IOException {
        titleLabel.setText(title);
        infoLabel.setText(type + Default.dot + artist);
        if(features != null) {
            for(String feat : features) {
                infoLabel.setText(infoLabel.getText() + Default.dot + feat);
            }
        }
        prepareCover(imgURL);
        section = sectionPane;
        prepareBackground(imgURL);
        prepareTracklist(tracklist);
    }
    private void prepareCover(String imgURL) {
        cover = new Image(imgURL);
        coverImg.setImage(cover);
        coverImg.setClip(Default.clipShape(200, 200, 16, 16));
        coverImg.setViewport(Default.setViewportSquare(cover, coverImg));
    }
    private void prepareBackground(String imgURL) {
        cover = new Image(imgURL);
        background = new ImageView(cover);
        background.setFitHeight(StageHolder.getPrimaryStage().getHeight() - 240);
        background.setFitWidth(StageHolder.getPrimaryStage().getWidth() - 317);
        background.setEffect(new GaussianBlur(25));
        background.setClip(Default.clipShape(StageHolder.getPrimaryStage().getWidth() - 317, StageHolder.getPrimaryStage().getHeight() - 240, 16, 16));
        background.setViewport(Default.setViewportSquare(cover, background));
        background.setOpacity(0.5);
        tracklistSection.getChildren().clear();
        tracklistSection.getChildren().add(background);
        tracklistSection.getChildren().add(section);
    }
    private void resizeBackground(double width, double height) {
        tracklistBackground.setFitWidth(width);
        tracklistBackground.setFitHeight(height);
        background.setFitWidth(width);
        background.setFitHeight(height);
        background.setClip(Default.clipShape(width, height, 16, 16));
        background.setViewport(Default.setViewportSquare(cover, background));
    }
    private void prepareTracklist(ArrayList<Track> tracklist) throws IOException {
        if(!trackView.getItems().isEmpty()) {
            trackView.getItems().clear();
            trackCells.clear();
        }
        for(Track track : tracklist) {
            TrackCell trackCell = new TrackCell(track);
            trackView.getItems().add(trackCell.getCell());
            trackCells.add(trackCell);
        }
        mainGrid.getRowConstraints().get(1).setMinHeight(30*tracklist.size() + 3*(tracklist.size()+1));
    }

}
