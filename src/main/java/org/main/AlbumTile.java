package org.main;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import java.util.Objects;

public class AlbumTile {
    private final Image coverImage;
    private final String artist;
    private final String title;
    private final ArrayList<String> features;
    private ImageView coverArt;
    private ImageView coverBackground;
    private StackPane cover;
    private Rectangle coverRectangle;
    private VBox infoBox;
    private double imgWidth;
    private double imgHeight;
    public AlbumTile(double width, double height, String title, String artist, ArrayList<String> features, String coverURL) {
        coverImage = new Image(Objects.requireNonNull(coverURL));
        this.title = title;
        this.artist = artist;
        this.features = features;
        this.imgWidth = width;
        this.imgHeight = height;
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
        titleCover.getStyleClass().add("tileHomeText");

        Label artistCover = new Label(artist);
        artistCover.getStyleClass().add("tileHomeTextSmall");
        if(features != null) {
            for(String feat : features) {
                artistCover.setText(artistCover.getText() + Default.dot + feat);
            }
        }


        infoBox = new VBox();
        infoBox.getChildren().addAll(titleCover, artistCover);
        infoBox.setMouseTransparent(true);
        infoBox.setOpacity(0);
        infoBox.setAlignment(Pos.CENTER);

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
        });
    }
}
