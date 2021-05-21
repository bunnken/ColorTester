module com.bunnken.colortester {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.bunnken.colortester to javafx.fxml;
    exports com.bunnken.colortester;
}
