module org.herzig.auction.control {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.herzig.auction.control to javafx.fxml;
    exports org.herzig.auction.control;

    opens org.herzig.auction.model to javafx.fxml;
    exports org.herzig.auction.model;
}