package org.main;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

import static org.main.Default.mainSpace;
import static org.main.controllers.LibraryController.setListView;

public class PlaylistTile {
    private final Image coverImage;
    private final ArrayList<String> artists;
    private final String title;
    private ImageView coverArt;
    private ImageView coverBackground;
    private StackPane cover;
    private Rectangle coverRectangle;
    private VBox infoBox;
    private double imgWidth;
    private double imgHeight;
    private int playlistID;
    public PlaylistTile(double width, double height, String title, ArrayList<String> artists, String coverURL, int ID) {
        coverImage = new Image(Objects.requireNonNull(coverURL));
        this.title = title;
        this.artists = artists;
        this.imgWidth = width;
        this.imgHeight = height;
        this.playlistID = ID;
        makeCover();
    }
    private void makeTile() {
        coverArt = new ImageView(coverImage);
        coverArt.setFitHeight(imgHeight);
        coverArt.setFitWidth(imgWidth);

        Rectangle clipShape = Default.clipShape(imgWidth, imgHeight, 16, 16);
        coverArt.setViewport(Default.setViewportRectangle(coverImage, coverArt));
        coverArt.setClip(clipShape);
        makeHoverEffect();
    }
    private void makeBackground() {
        coverBackground = new ImageView(coverImage);
        coverBackground.setFitHeight(imgHeight);
        coverBackground.setFitWidth(imgWidth);
        coverBackground.setViewport(Default.setViewportRectangle(coverImage, coverBackground));
        coverBackground.setEffect(new GaussianBlur(20));
        coverBackground.setOpacity(0);
    }
    private void makeCover() {
        cover = new StackPane();
        makeTile();
        makeBackground();

        coverRectangle = new Rectangle();
        coverRectangle.setHeight(imgHeight);
        coverRectangle.setWidth(imgWidth);
        coverRectangle.setArcWidth(16);
        coverRectangle.setArcHeight(16);
        coverRectangle.setFill(Color.web("#1a1616"));

        cover.getChildren().addAll(coverBackground,coverRectangle,coverArt);

        Label titleCover = new Label(title);
        titleCover.getStyleClass().add("tileText");
        VBox artistsBox = null;
        if(artists != null) {
            artistsBox = new VBox();
            for(int i=0; i<artists.size() && i < 3; i++) {
                Label artistsCover = new Label(artists.get(i));
                artistsCover.getStyleClass().add("playlistInfoText");
                artistsBox.getChildren().add(artistsCover);
            }
            int more = artists.size() - 3;
            if(more >= 0) {
                Label moreCover = new Label(more + " more");
                moreCover.getStyleClass().add("moreInfoText");
                artistsBox.getChildren().add(moreCover);
            }

            artistsBox.setAlignment(Pos.CENTER_LEFT);
            artistsBox.setPadding(new Insets(0, 0,0,33));
        }

        infoBox = new VBox();
        if(artistsBox != null) {
            infoBox.getChildren().addAll(titleCover, artistsBox);
        } else {
            infoBox.getChildren().add(titleCover);
        }
        infoBox.setMouseTransparent(true);
        infoBox.setOpacity(0);
        infoBox.setAlignment(Pos.TOP_CENTER);
        infoBox.setPadding(new Insets(30, 0, 0, 0));
        infoBox.setSpacing(54);
        cover.getChildren().add(infoBox);

    }

    public StackPane getCoverArt() {
        return cover;
    }
    public void resizeCoverArt(double scaleX, double scaleY) {
        coverArt.setFitHeight(imgHeight*scaleY);
        coverArt.setFitWidth(imgWidth*scaleX);

        coverBackground.setFitHeight(imgHeight*scaleY);
        coverBackground.setFitWidth(imgWidth*scaleX);

        coverRectangle.setHeight(imgHeight*scaleY);
        coverRectangle.setWidth(imgWidth*scaleX);

        Rectangle clipShape = Default.clipShape(imgWidth*scaleX, imgHeight*scaleY, 16, 16);
        coverArt.setViewport(Default.setViewportRectangle(coverImage, coverArt));
        coverArt.setClip(clipShape);
    }
    private void makeHoverEffect() {
        coverArt.setOnMouseEntered(event -> {
            GaussianBlur gaussianBlur = new GaussianBlur(0);
            coverArt.setEffect(gaussianBlur);
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(coverArt.opacityProperty(), coverArt.getOpacity())),
                    new KeyFrame(Duration.ZERO, new KeyValue(coverBackground.opacityProperty(), coverBackground.getOpacity())),
                    new KeyFrame(Duration.ZERO, new KeyValue(infoBox.opacityProperty(), infoBox.getOpacity())),
                    new KeyFrame(Duration.ZERO, new KeyValue(gaussianBlur.radiusProperty(), gaussianBlur.getRadius())),

                    new KeyFrame(Duration.millis(200), new KeyValue(coverArt.opacityProperty(), 0.7)),
                    new KeyFrame(Duration.millis(200), new KeyValue(coverBackground.opacityProperty(), 1)),
                    new KeyFrame(Duration.millis(200), new KeyValue(infoBox.opacityProperty(), 1)),
                    new KeyFrame(Duration.millis(200), new KeyValue(gaussianBlur.radiusProperty(), 12))
            );
            timeline.play();
            coverArt.setOnMouseExited(event2 -> {
                Timeline timeline2 = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(coverArt.opacityProperty(), coverArt.getOpacity())),
                        new KeyFrame(Duration.ZERO, new KeyValue(coverBackground.opacityProperty(), coverBackground.getOpacity())),
                        new KeyFrame(Duration.ZERO, new KeyValue(infoBox.opacityProperty(), infoBox.getOpacity())),
                        new KeyFrame(Duration.ZERO, new KeyValue(gaussianBlur.radiusProperty(), gaussianBlur.getRadius())),

                        new KeyFrame(Duration.millis(200), new KeyValue(coverArt.opacityProperty(), 1)),
                        new KeyFrame(Duration.millis(200), new KeyValue(coverBackground.opacityProperty(), 0)),
                        new KeyFrame(Duration.millis(200), new KeyValue(infoBox.opacityProperty(), 0)),
                        new KeyFrame(Duration.millis(200), new KeyValue(gaussianBlur.radiusProperty(), 0))
                );
                timeline2.play();
            });
            coverArt.setOnMouseClicked(event3 -> {
                Default.Type = 1;

                Default.albumID.set(playlistID);
                if(!mainSpace.getChildren().isEmpty()) {
                    mainSpace.getChildren().clear();
                }
                mainSpace.getChildren().add(Default.tracklistView);
                setListView(DataBase.getDataBase().getPlaylistCell(playlistID));
            });

        });
    }
}
