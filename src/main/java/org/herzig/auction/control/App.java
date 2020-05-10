package org.herzig.auction.control;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.herzig.auction.control.helper.View;
import org.herzig.auction.control.helper.ViewHelper;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ViewHelper.showView(View.ADMINISTRATION_VIEW);
    }

    public static void main(String[] args) {
        launch(args);
    }

}