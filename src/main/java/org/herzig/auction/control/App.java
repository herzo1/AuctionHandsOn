package org.herzig.auction.control;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.herzig.auction.control.helper.View;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(View.ADMINISTRATION_VIEW));
        stage.setScene(new Scene(root));
        stage.setTitle("Auction Administration");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}