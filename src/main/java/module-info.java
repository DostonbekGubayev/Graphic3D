module com.example.graphic3d {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.graphic3d to javafx.fxml;
    exports com.example.graphic3d;
}