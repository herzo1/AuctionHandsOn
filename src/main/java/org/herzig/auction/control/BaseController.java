package org.herzig.auction.control;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import org.herzig.auction.model.Auction;
import org.herzig.auction.model.AuctionObserver;

public abstract class BaseController implements AuctionObserver {

    @Override
    public void update(Auction auction) {
        Platform.runLater(() -> handleUpdate(auction));
    }

    abstract void handleUpdate(Auction auction);
}
