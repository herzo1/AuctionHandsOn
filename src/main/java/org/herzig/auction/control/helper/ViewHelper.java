package org.herzig.auction.control.helper;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewHelper{
    private View view;
    private String windowTitle;
    private Parent root;
    private Stage stage;
    private Object controller;

    public static void showView(View view) throws IOException {
        ViewHelper viewHelper = new ViewHelper(view);
        viewHelper.showView();
    }

    public ViewHelper(View view, String windowTitle) throws IOException {
        this.view = view;
        this.windowTitle = windowTitle;
        setupFXMLLoader();

    }

    public ViewHelper(View view) throws IOException {
        this(view, view.getViewTitle());
    }

    public void showView() {
        setupStage();
        this.stage.show();
    }

    public Object getController() {
        return this.controller;
    }

    private void setupFXMLLoader() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(this.view.getViewPath()));
        this.root = loader.load();
        this.controller = loader.getController();
    }

    private void setupStage() {
        this.stage = new Stage();
        this.stage.setScene(new Scene(this.root));
        this.stage.setTitle(this.windowTitle);
    }

}
