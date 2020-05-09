package org.herzig.auction.control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class AdministrationController {
    @FXML
    private ListView<Object> items;

    @FXML
    private Button startAuctionBtn;

    @FXML
    public void handleStartAuction() {
        System.out.println("Start Auction");
    }

    @FXML
    public void handleShowBids() {
        System.out.println("Show Bids");
    }

    public void initialize() {
        // todo
    }

}
