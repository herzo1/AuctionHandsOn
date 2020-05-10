package org.herzig.auction.control.helper;

import org.herzig.auction.control.*;
import org.herzig.auction.model.Bid;

public enum View {
    ADMINISTRATION_VIEW("/AdministrationView.fxml", "Auction Administration"),
    AUCTION_VIEW("/AuctionView.fxml", "Auction"),
    BIDDER_VIEW("/BidderView.fxml", "Bidder"),
    LOGIN_VIEW("/LoginView.fxml", "Login");

    private String viewPath;
    private String viewTitle;


    private View(String viewPath, String viewTitle){
        this.viewPath = viewPath;
        this.viewTitle = viewTitle;
    }

    public String getViewPath() {
        return this.viewPath;
    }

    public String getViewTitle() {
        return this.viewTitle;
    }
}
