package org.herzig.auction.control;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AdministrationView.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Auction Administration");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}