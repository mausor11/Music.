package org.main;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MusicPlayer {
    private MediaPlayer mediaPlayer;
    private static MusicPlayer instance;
    private MusicPlayer() {
    }
    public void setMediaPlayer(String url) {
        if(url != null) {
            Media media = new Media(new File(url).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        }
    }
    public static MusicPlayer getMusicPlayer() {
        if(instance == null) {
            instance = new MusicPlayer();
        }
        return instance;
    }
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
