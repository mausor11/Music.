package org.main;

import javafx.scene.chart.PieChart;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class DataBase {
    private Connection connection;
    private final String databaseURL = "jdbc:ucanaccess://src/main/resources/org/main/MusicDataBase.accdb";
    private static DataBase instance;

    /* DATABASE DEFAULT METHODS */
    private DataBase() {
        try {
            this.connection = DriverManager.getConnection(databaseURL);
            System.out.println("Connected.");
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
    public static DataBase getDataBase() {
        if(instance == null) {
            instance = new DataBase();
        }
        return instance;
    }
    public Connection getConnection() {
        return this.connection;
    }
    public String getArtistName(int ID) {
        String artistName = null;
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT artist_name FROM Artists WHERE artist_id = " + ID;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                artistName = resultSet.getString("artist_name");
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return artistName;
    }
    public long getArtistID(String name) {
        long ID = 0;
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT artist_id FROM Artists WHERE artist_name = '" + name +"'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                ID = resultSet.getLong("artist_id");
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(ID != 0 ) {
            return ID;
        } else {
            return addArtist(name);
        }
    }
    public long getGenreID(String name) {
        long ID = 0;
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT genre_id FROM Genres WHERE genre_name = '" + name +"'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                ID = resultSet.getLong("genre_id");
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(ID != 0 ) {
            return ID;
        } else {
            return addGenre(name);
        }
    }
    public long addArtist(String name) {
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }


                String sql = "INSERT INTO Artists(artist_name) VALUES (?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, name);
                int row = preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return getArtistID(name);
    }
    public long addGenre(String name) {
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
                String sql = "INSERT INTO Genres(genre_name) VALUES (?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, name);
                int row = preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return getGenreID(name);
    }
    public void getAllString(String table, String column) {
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT * FROM [" + table + "]";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println(table + " -> " + column);
            while(resultSet.next()) {
                System.out.println(resultSet.getString(column));
            }
            System.out.println("------------------");
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void getAllInteger(String table, String column) {
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT * FROM [" + table + "]";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println(table + " -> " + column);
            while(resultSet.next()) {
                System.out.println(resultSet.getInt(column));
            }
            System.out.println("------------------");
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /* DATABASE Albums METHODS */
    public ArrayList<Integer> getAllAlbumsID() {
        ArrayList<Integer> allIDs = new ArrayList<>();

        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT album_id FROM Albums";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                allIDs.add(resultSet.getInt("album_id"));
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allIDs;
    }
    public ArrayList<Integer> getAllAlbumsFavourites() {
        ArrayList<Integer> IDs = new ArrayList<>();
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT album_id FROM Albums WHERE album_favourite = true";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                IDs.add(resultSet.getInt("album_id"));
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return IDs;
    }

    public int getAlbumID(String album_name) {
        int ID = 0;
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT album_id FROM Albums WHERE album_name = '" + album_name + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                ID = resultSet.getInt("album_id");
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ID;
    }
    public int getAlbumCell(int ID) {
        int cellID = 0;
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT cell_id FROM Albums WHERE album_id = " + ID;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                cellID = resultSet.getInt("cell_id");
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cellID;
    }
    public int getPlaylistCell(int ID) {
        int cellID = 0;
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT cell_id FROM Playlists WHERE playlist_id = " + ID;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                cellID = resultSet.getInt("cell_id");
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cellID;
    }
    public int getAlbumID(String album_name, long artist_id) {
        int ID = 0;
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT album_id FROM Albums WHERE (album_name = '" + album_name + "'" + " AND " + "album_artist = " + artist_id + ")";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                ID = resultSet.getInt("album_id");
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ID;
    }

    public String getAlbumName(int ID) {
        String albumName = null;
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT album_name FROM Albums WHERE album_id = " + ID;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                albumName = resultSet.getString("album_name");
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return albumName;
    }
    public int getAlbumArtistID(int albumID) {
        int ID = 0;
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT album_artist FROM Albums WHERE album_id = " + albumID;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                ID = resultSet.getInt("album_artist");
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ID;
    }
    public int getAlbumArtistID(String album_name) {
        int ID = 0;
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT album_artist FROM Albums WHERE album_name = '" + album_name + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                ID = resultSet.getInt("album_artist");
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ID;
    }
    public String getAlbumArtistName(int albumID) {
        String artistName = null;
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT artist_name FROM Artists JOIN Albums am ON (album_artist = artist_id) WHERE album_id = " + albumID;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                artistName = resultSet.getString("artist_name");
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return artistName;
    }
    public String getAlbumArtistName(String album_name) {
        String artistName = null;
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT artist_name FROM Artists JOIN Albums am ON (album_artist = artist_id) WHERE album_name = '" + album_name + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                artistName = resultSet.getString("artist_name");
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return artistName;
    }
    public String getAlbumCover(int ID) {
        String albumCover = null;
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT album_cover FROM Albums WHERE album_id = " + ID;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                albumCover = resultSet.getString("album_cover");
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return albumCover;
    }
    public String getAlbumCover(String album_name) {
        String albumCover = null;
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT album_cover FROM Albums WHERE album_name = '" + album_name + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                albumCover = resultSet.getString("album_cover");
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(albumCover == null) {
            System.out.println("No album cover with album name: " + album_name);
        }
        return albumCover;
    }
    public ArrayList<String> getAlbumFeaturesName(int ID) {
        ArrayList<String> features = new ArrayList<>();
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT a.artist_name FROM Artists a JOIN Features f ON (f.artist_id = a.artist_id) WHERE f.album_id = " + ID;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                features.add(resultSet.getString("artist_name"));
            }
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return features;
    }
    public ArrayList<Integer> getAlbumFeaturesID(int ID) {
        ArrayList<Integer> features = new ArrayList<>();
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT artist_id FROM Features WHERE album_id = " + ID;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                features.add(resultSet.getInt("artist_id"));
            }
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return features;
    }
    public boolean isAlbumFavourite(int ID) {
        boolean isFavourite = false;
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT album_favourite FROM Albums WHERE album_id = " + ID;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                isFavourite = resultSet.getBoolean("album_favourite");
            }
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  isFavourite;
    }
    public boolean isAlbumFavourite(String album_name) {
        boolean isFavourite = false;
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT album_favourite FROM Albums WHERE album_name = '" + album_name + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                isFavourite = resultSet.getBoolean("album_favourite");
            }
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  isFavourite;
    }
    public int getTrackAlbumIndex(int ID) {
        int index = 0;
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT songAlbum_index FROM Songs WHERE song_id = " + ID;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                index = resultSet.getInt("songAlbum_index");
            }
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return index;
    }
    public ArrayList<String> getAlbumTracklistName(int ID) {
        ArrayList<String> tracklist = new ArrayList<>();
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT song_name FROM Songs WHERE album_id = " + ID;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                tracklist.add(resultSet.getString("song_name"));
            }


            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tracklist;
    }
    public ArrayList<String> getAlbumTracklistName(String album_name) {
        ArrayList<String> tracklist = new ArrayList<>();
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT song_name FROM Songs WHERE album_name = '" + album_name + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                tracklist.add(resultSet.getString("song_name"));
            }


            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tracklist;
    }
    public ArrayList<Integer> getAlbumTracklistID(int ID) {
        ArrayList<Integer> tracklist = new ArrayList<>();
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT song_id FROM Songs WHERE album_id = " + ID;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                tracklist.add(resultSet.getInt("song_id"));
            }


            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tracklist;
    }
    public String getAlbumType(int ID) {
        String type = null;
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT type FROM Albums WHERE album_id = " + ID;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                type = resultSet.getString("type");
            }
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return type;
    }
    public ArrayList<Track> getAlbumTracklist(int ID) {
        ArrayList<Track> tracklist = new ArrayList<>();
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT song_id FROM Songs WHERE album_id = " + ID;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                tracklist.add(getTrack(resultSet.getInt("song_id")));
            }


            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Collections.sort(tracklist);
        return tracklist;
    }
    public ArrayList<Track> getPlaylistTracklist(int ID) {
        ArrayList<Track> tracklist = new ArrayList<>();
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT song_id FROM Songs WHERE playlist_id = " + ID;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                tracklist.add(getTrack(resultSet.getInt("song_id")));
            }


            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Collections.sort(tracklist);
        return tracklist;
    }
    public ArrayList<Integer> getAlbumTracklistID(String album_name) {
        ArrayList<Integer> tracklist = new ArrayList<>();
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT song_id FROM Songs WHERE album_name = '" + album_name + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                tracklist.add(resultSet.getInt("song_id"));
            }


            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tracklist;
    }
    public int getAlbumUserID(int albumID) {
        int ID = 0;
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT u.user_id FROM Users u JOIN Songs s ON (s.user_id = u.user_id) WHERE s.album_id = " + albumID;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                ID = resultSet.getInt("user_id");
            }
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ID;
    }
    public int getAlbumUserID(String album_name) {
        int ID = 0;
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT u.user_id FROM Users u JOIN Songs s ON (s.user_id = u.user_id) JOIN Albums a ON (s.album_id = a.album_id) WHERE a.album_name = '" + album_name + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                ID = resultSet.getInt("user_id");
            }
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ID;
    }
    public String getAlbumUserName(int albumID) {
        String user = null;
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT u.user_name FROM Users u JOIN Songs s ON (s.user_id = u.user_id) WHERE s.album_id = " + albumID;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                user = resultSet.getString("user_name");
            }
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
    public void getAllInfoAlbum(int ID) {
        System.out.println("Title: " + getAlbumName(ID));
        System.out.println("Artist: " + getAlbumArtistName(ID));
        System.out.println("Feat: " + getAlbumFeaturesName(ID));
        System.out.println("Tracklist: \n" + getAlbumTracklist(ID));
        System.out.println("Cover: " + getAlbumCover(ID));
        System.out.println("Favourite: " + isAlbumFavourite(ID));
    }
    public boolean addAlbum(String albumName, String albumCover, boolean isFavourite, long albumArtist, ArrayList<Integer> features, ArrayList<Track> tracklist) {
        try {
            if(isArtistExists((int)albumArtist)) {
                this.connection = DriverManager.getConnection(databaseURL);
                String sql = "INSERT INTO Albums(album_name, album_cover, album_favourite, album_artist) VALUES (?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, albumName);
                preparedStatement.setString(2, albumCover);
                preparedStatement.setBoolean(3, isFavourite);
                preparedStatement.setLong(4, albumArtist);
                int row = preparedStatement.executeUpdate();
                if(row == 1) {
                    String sql2 = "INSERT INTO Features(artist_id, album_id) VALUES (?,?)";
                    long albumID = getAlbumID(albumName, albumArtist);
                    this.connection = DriverManager.getConnection(databaseURL);
                    PreparedStatement preparedStatement1 = connection.prepareStatement(sql2);
                    for(Integer feat : features) {
                        preparedStatement1.setInt(1, feat);
                        preparedStatement1.setLong(2, albumID);
                        preparedStatement1.executeUpdate();
                    }
                    for(Track track : tracklist) {
                        addTrack(track.getTrackName(), track.getTrackDuration(), track.getArtistID(), track.getCoverLink(), track.getPlaylistID(), track.isFavourite(), track.getGenreID(), track.getAlbumID(), track.getUserID(), track.getFeatures());
                    }
                }
                connection.close();
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /* DATABASE Songs METHODS */
    public <T> T getTrackInfo (int ID, String column) {
        T getter = null;
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT [" + column + "] FROM Songs WHERE song_id = " + ID;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                getter = (T) resultSet.getObject(column);
            }
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getter;
    }
    public ArrayList<Integer> getTrackFeat(int ID) {
        ArrayList<Integer> features = new ArrayList<>();
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT artist_id FROM Features WHERE song_id = " + ID;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                features.add(resultSet.getInt("artist_id"));
            }
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return features;
    }
    public ArrayList<String> getTrackFeatNames(int ID) {
        ArrayList<Integer> features = getTrackFeat(ID);
        ArrayList<String> featNames = new ArrayList<>();
        try {
            for(Integer feat : features) {
                featNames.add(getArtistName(feat));
            }
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return featNames;
    }
    public Track getTrack(int ID) {
        return new Track(getTrackAlbumIndex(ID), ID,(Long) getTrackInfo(ID, "song_duration"), (Long) getTrackInfo(ID, "user_id"), (Long)getTrackInfo(ID, "artist_id"), (Long)getTrackInfo(ID, "genre_id"), (String)getTrackInfo(ID, "cover_link"), (String)getTrackInfo(ID, "song_name"), (Long)getTrackInfo(ID, "album_id"), (boolean)getTrackInfo(ID, "song_favourite"), (Long)getTrackInfo(ID, "playlist_id"), getTrackFeat(ID));
    }
    public long getTrackID(String trackName, long trackArtist) {
        long trackID = 0;
        try {
            if (connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT song_id FROM Songs WHERE (song_name = '" + trackName + "'" + " AND " + "artist_id = " + trackArtist + ")";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                trackID = resultSet.getLong("song_id");
            }
            connection.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return trackID;
    }
    public void addTrack(String trackName, long trackDuration, long trackArtist, String trackCover, long trackPlaylist, boolean isFavourite, long trackGenre, long trackAlbum, long trackUser, ArrayList<Integer> features) {
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "INSERT INTO Songs(song_name, song_duration, artist_id, cover_link, playlist_id, song_favourite, genre_id, album_id, user_id) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, trackName);
            preparedStatement.setLong(2, trackDuration);
            preparedStatement.setLong(3, trackArtist);
            preparedStatement.setString(4, trackCover);
            preparedStatement.setLong(5, trackPlaylist);
            preparedStatement.setBoolean(6, isFavourite);
            preparedStatement.setLong(7, trackGenre);
            preparedStatement.setLong(8, trackAlbum);
            preparedStatement.setLong(9, trackUser);
            int row = preparedStatement.executeUpdate();
            if(row == 1) {
                System.out.println("Track added.");
                if(features != null) {
                    String sql2 = "INSERT INTO Features(artist_id, song_id) VALUES (?,?)";
                    long trackID = getTrackID(trackName, trackArtist);
                    this.connection = DriverManager.getConnection(databaseURL);
                    PreparedStatement preparedStatement1 = connection.prepareStatement(sql2);
                    for(Integer feat : features) {
                        preparedStatement1.setInt(1, feat);
                        preparedStatement1.setLong(2, trackID);
                            preparedStatement1.executeUpdate();
                    }
                }
            }
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /* DATABASE Artists METHODS */
    public boolean isArtistExists(int ID) {
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT * FROM Artists WHERE artist_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) {
                return false;
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    public ArrayList<String> getAllPlaylistArtists(int ID) {
        HashSet<String> a = new LinkedHashSet<>();
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT a.artist_name FROM Artists a JOIN  Songs s ON (a.artist_id = s.artist_id) WHERE s.playlist_id = " + ID;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                a.add(resultSet.getString("artist_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> artists = new ArrayList<>();
        for(String artist : a) {
            artists.add(artist);
        }
        return artists;
    }
    public ArrayList<Integer> getAllPlaylistsID() {
        ArrayList<Integer> IDs = new ArrayList<>();
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT playlist_id FROM Playlists";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                IDs.add(resultSet.getInt("playlist_id"));
            }
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return IDs;
    }
    public ArrayList<Integer> getFavouriteAlbumsID() {
        ArrayList<Integer> IDs = new ArrayList<>();
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT album_id FROM Albums WHERE album_favourite = true";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                IDs.add(resultSet.getInt("album_id"));
            }
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return IDs;
    }
    public String getPlaylistName(int ID) {
        String playlistName = null;
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT playlist_name FROM Playlists WHERE playlist_id = " + ID;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                playlistName = resultSet.getString("playlist_name");
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return playlistName;
    }
    public String getPlaylistCoverURL(int ID) {
        String playlistCover = null;
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT playlist_cover FROM Playlists WHERE playlist_id = " + ID;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                playlistCover = resultSet.getString("playlist_cover");
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return playlistCover;
    }
    public ArrayList<Track> getFavouriteTracklist() {
        ArrayList<Track> tracklist = new ArrayList<>();
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT song_id FROM Songs WHERE song_favourite = true";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                tracklist.add(getTrack(resultSet.getInt("song_id")));
            }
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tracklist;
    }
    public int getFavouriteCount() {
        int i = 0;
        try {
            if(connection.isClosed()) {
                this.connection = DriverManager.getConnection(databaseURL);
            }
            String sql = "SELECT song_id FROM Songs WHERE song_favourite = true";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                i++;
            }
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i;
    }


    public static void main(String[] args) {
        System.out.println(DataBase.getDataBase().getArtistID("Asap Rocky"));
    }

}
