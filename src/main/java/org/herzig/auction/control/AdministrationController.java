package org.herzig.auction.control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.herzig.auction.control.helper.View;
import org.herzig.auction.model.Auction;
import org.herzig.auction.model.AuctionObserver;
import org.herzig.auction.model.AuctionSystem;
import org.herzig.auction.model.Item;

import java.io.IOException;
import java.util.List;

public class AdministrationController implements AuctionObserver {
    @FXML
    private ListView<Item> items;

    @FXML
    private Button startAuctionBtn;

    public void initialize() {
        List<Item> list = AuctionSystem.getInstance().getCatalogue().getItems();
        this.items.getItems().addAll(list);
        this.items.getSelectionModel().clearAndSelect(0);
    }

    @FXML
    public void handleStartAuction() throws IOException {
        this.startAuctionBtn.setDisable(true);
        Item item = this.items.getSelectionModel().getSelectedItem();
        Auction auction = new Auction(item);
        auction.addObserver(this);

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(View.ACTION_VIEW));
        Parent root = loader.load();
        ((AuctionController)loader.getController()).setAuction(auction);

        stage.setScene(new Scene(root));
        stage.setTitle("Auction");
        stage.show();

        auction.start();
    }

    @FXML
    public void handleShowBids() {
        // todo
    }

    @Override
    public void update(Auction auction) {
        if(auction.getStatus() == Auction.Status.TERMINATED) {
            this.startAuctionBtn.setDisable(false);
            auction.removeObserver(this);
        }

    }
}
