package org.herzig.auction.control;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.herzig.auction.model.Auction;
import org.herzig.auction.model.Bid;
import org.herzig.auction.model.InvalidBidException;
import org.herzig.auction.model.User;
import org.herzig.auction.model.robot.Eager;
import org.herzig.auction.model.robot.Robot;
import org.herzig.auction.model.robot.Strategy;

public class BidderController extends BaseController {
    private Auction auction;
    private User bidder;
    private Robot robot;

    @FXML
    Label itemLbl;

    @FXML
    TextField amountTF;

    @FXML
    Button placeBitBtn;

    @FXML
    Label messageLbl;

    @FXML
    Label currentBidLbl;

    @FXML
    ChoiceBox<String> strategyCB;

    @FXML
    TextField limitTF;

    @FXML
    Button startBtn;

    @FXML
    Button stopBtn;

    public void initialize() {
        // todo: set other robots here
        this.strategyCB.setItems(FXCollections.observableArrayList("Eager"));
        this.strategyCB.getSelectionModel().select(0);
        this.stopBtn.setDisable(true);
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
        this.auction.addObserver(this);
        this.itemLbl.setText(auction.getItem().toString());
        updateCurrentBidLbl();
    }

    public void setBidder(User bidder) {
        this.bidder = bidder;
    }

    @FXML
    public void placeBid() {
        this.messageLbl.setText("");
        String amountStr = this.amountTF.getText();
        if(!amountStr.matches("\\d+(\\.\\d+)?")) {
            this.messageLbl.setText("Amount must be a positive number.");
            return;
        }
        double amount = Double.parseDouble(amountStr);
        Bid bid = new Bid(this.bidder, amount);
        try{
            auction.placeBid(bid);
        } catch (InvalidBidException e) {
            this.messageLbl.setText(e.getMessage());
        }
    }

    @FXML
    public void startRobot() {
        Strategy strategy = null;
        // todo: implement all robot strategies
        switch (this.strategyCB.getSelectionModel().getSelectedItem()) {
            case "Eager":
                strategy = new Eager();
                break;
        }
        if(strategy != null) {
            this.robot = new Robot(this.bidder, this.auction, strategy);
            this.robot.start();
            this.startBtn.setDisable(true);
            this.stopBtn.setDisable(false);
        }
    }

    @FXML
    public void stopRobot() {
        if (this.robot != null) {
            this.robot.stop();
            this.robot = null;
            this.stopBtn.setDisable(false);
            this.stopBtn.setDisable(true);
        }
    }

    @Override
    void handleUpdate(Auction auction) {
        if(auction.getStatus() == Auction.Status.RUNNING) {
            updateCurrentBidLbl();
        } else if(auction.getStatus() == Auction.Status.TERMINATED) {
            this.placeBitBtn.setDisable(true);
            if(this.robot != null) {
                this.robot.stop();
                this.robot = null;
            }
            this.startBtn.setDisable(true);
            this.stopBtn.setDisable(true);
        }
    }

    private void updateCurrentBidLbl() {
        try {
            this.currentBidLbl.setText(this.auction.getCurrentBid().toString());
        } catch (NullPointerException e) {
            this.currentBidLbl.setText("---");
        }
    }
}
