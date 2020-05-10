package org.herzig.auction.control;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.herzig.auction.control.helper.View;
import org.herzig.auction.control.helper.ViewHelper;
import org.herzig.auction.model.Auction;
import org.herzig.auction.model.NoBidAvailableException;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class AuctionController extends BaseController {
    private Auction auction;

     @FXML
     private Label itemLbl;

     @FXML
     private Label statusLbl;

     @FXML
     private Label endTimeLbl;

     @FXML
     private Label remainingTimeLbl;

     @FXML
     private Label currentBidLbl;

     @FXML
     public void addBidder() throws IOException {
         ViewHelper view = new ViewHelper(View.LOGIN_VIEW);
         ((LoginController)view.getController()).setAuction(auction);
         view.showView();
     }

     @FXML
     public void closeAuction() {
         this.auction.stop();
         this.auction.removeObserver(this);
         ((Stage)this.itemLbl.getScene().getWindow()).close();
     }

     public void setAuction(Auction auction) {
         this.auction = auction;
         this.auction.addObserver(this);
         setItemLabel(auction);
     }

    @Override
    void handleUpdate(Auction auction) {
         setEndTimeLabel(auction);
         setRemainingTimeLabel(auction);
         setCurrentBidLabel(auction);
    }

    private void setItemLabel(Auction auction) {
        this.itemLbl.setText(auction.getItem().toString());
    }

    private void setEndTimeLabel(Auction auction) {
        this.endTimeLbl.setText(auction.getEndTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
    }

    private void setRemainingTimeLabel(Auction auction) {
        this.remainingTimeLbl.setText(getStringForRemainingTime(auction));
    }

    private void setCurrentBidLabel(Auction auction) {
        try{
            this.currentBidLbl.setText(auction.getCurrentBid().toString());
        } catch (NoBidAvailableException e) {
            this.currentBidLbl.setText("---");
        }
    }

    private String getStringForRemainingTime(Auction auction) {
         return String.format("%d days, %d hours, %d minutes, %d seconds",
                 auction.getRemainingTime().toDays(), auction.getRemainingTime().toHoursPart(),
                 auction.getRemainingTime().toMinutesPart(), auction.getRemainingTime().toSecondsPart());
    }
}
