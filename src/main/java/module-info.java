module org.herzig {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.herzig to javafx.fxml;
    exports org.herzig;
}