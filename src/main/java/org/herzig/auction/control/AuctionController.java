package org.herzig.auction.control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.herzig.auction.model.Auction;
import org.herzig.auction.model.Bid;

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
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginView.fxml"));
         Parent root = loader.load();
         ((LoginController)loader.getController()).setAuction(this.auction);
         Stage stage = new Stage();
         stage.setScene(new Scene(root));
         stage.setTitle("Login");
         stage.show();
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
         this.itemLbl.setText(auction.getItem().toString());
     }

    @Override
    void handleUpdate(Auction auction) {
        this.endTimeLbl.setText(auction.getEndTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        String remainingTime = String.format("%d days, %d hours, %d minutes, %d seconds",
                auction.getRemainingTime().toDays(), auction.getRemainingTime().toHoursPart(),
                auction.getRemainingTime().toMinutesPart(), auction.getRemainingTime().toSecondsPart());
        this.remainingTimeLbl.setText(remainingTime);
        Bid currentBid = auction.getCurrentBid();
        try{
            this.currentBidLbl.setText(currentBid.toString());
        } catch (NullPointerException e) {
            this.currentBidLbl.setText("---");
        }
    }
}
