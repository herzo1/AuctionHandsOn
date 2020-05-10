package org.herzig.auction.control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.herzig.auction.control.helper.View;
import org.herzig.auction.control.helper.ViewHelper;
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

        ViewHelper view = new ViewHelper(View.AUCTION_VIEW);
        ((AuctionController)view.getController()).setAuction(auction);
        view.showView();

        auction.start();
    }

    @FXML
    public void handleShowBids() {
        // todo
    }

    @Override
    public void update(Auction auction) {
        if(auction.isTerminated()) {
            this.startAuctionBtn.setDisable(false);
            auction.removeObserver(this);
        }

    }
}
