module Music {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;
    requires java.sql;

    opens org.main.controllers to javafx.fxml, javafx.graphics, javafx.controls, javafx.media, java.sql;
    opens org.main to javafx.fxml;

    exports org.main to javafx.controls, javafx.fxml, javafx.graphics, javafx.media;
}