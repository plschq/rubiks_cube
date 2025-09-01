module rubikscube {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens rubikscube to javafx.fxml;
    exports rubikscube;

}
