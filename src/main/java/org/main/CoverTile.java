package org.main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class CoverTile {
    private final Image coverImage;
    private ImageView coverArt;
    private double imgWidth = 181;
    private double imgHeight = 211;
    public CoverTile(String coverURL) {
        coverImage = new Image(Objects.requireNonNull(coverURL));
        makeTile();
    }
    private void makeTile() {
        coverArt = new ImageView(coverImage);
        coverArt.setFitHeight(imgHeight);
        coverArt.setFitWidth(imgWidth);

        Rectangle clipShape = Default.clipShape(imgWidth, imgHeight, 16, 16);
        coverArt.setViewport(Default.setViewportRectangle(coverImage, coverArt));
        coverArt.setClip(clipShape);
    }
    public ImageView getCoverArt() {
        return coverArt;
    }
    public void resizeCoverArt(double scaleX, double scaleY) {
        coverArt.setFitHeight(imgHeight*scaleY);
        coverArt.setFitWidth(imgWidth*scaleX);

        Rectangle clipShape = Default.clipShape(imgWidth*scaleX, imgHeight*scaleY, 16, 16);
        coverArt.setViewport(Default.setViewportRectangle(coverImage, coverArt));
        coverArt.setClip(clipShape);
    }
}
