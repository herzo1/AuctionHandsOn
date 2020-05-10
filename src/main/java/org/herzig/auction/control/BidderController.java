package org.herzig.auction.control;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import org.herzig.auction.model.*;
import org.herzig.auction.model.robot.Eager;
import org.herzig.auction.model.robot.Robot;
import org.herzig.auction.model.robot.RobotBidder;
import org.herzig.auction.model.robot.Strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BidderController extends BaseController {
    private Auction auction;
    private User bidder;
    private Robot robot;

    @FXML
    Label itemLbl;

    @FXML
    TextField amountTF;

    @FXML
    Button placeBidBtn;

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
        fillStrategyChoiceBoxAndSetDefault();
        disableRobotButton(false, true);
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
        this.auction.addObserver(this);
        setItemLabel(auction);
        updateCurrentBidLbl();
    }

    public void setBidder(User bidder) {
        this.bidder = bidder;
    }

    @FXML
    public void placeBid() {
        this.messageLbl.setText("");
        try {
            this.auction.placeBid(new Bid(
                    this.bidder,
                    getAmountFromAmountTF()));
        } catch (InvalidBidException e) {
            this.messageLbl.setText(e.getMessage());
        }
    }

    @FXML
    public void startRobot() {
        setupRobot();
        disableRobotButton(true, false);
    }

    @FXML
    public void stopRobot() {
        if (hasRobot()) {
            terminateRobot();
            disableRobotButton(false, true);
        }
    }

    @Override
    void handleUpdate(Auction auction) {
        if(auction.isRunning()) {
            updateCurrentBidLbl();
        } else if(auction.isTerminated()) {
            updateUIForTerminatedStatus();
        }
    }

    private void fillStrategyChoiceBoxAndSetDefault() {
        this.strategyCB.setItems(FXCollections.observableArrayList(getStrategyList()));
        this.strategyCB.getSelectionModel().select(0);
    }

    private List<String> getStrategyList(){
        List<String> list = new ArrayList<>();
        for(RobotBidder robot : RobotBidder.values()){
            list.add(robot.getName());
        }
        return list;
    }

    private void updateUIForTerminatedStatus() {
        this.placeBidBtn.setDisable(true);
        if(hasRobot()) {
            terminateRobot();
        }
        disableRobotButton(true, true);
    }

    private void setItemLabel(Auction auction) {
        this.itemLbl.setText(auction.getItem().toString());
    }

    private void disableRobotButton(boolean disableStartBtn, boolean disableStopBtn){
        this.startBtn.setDisable(disableStartBtn);
        this.stopBtn.setDisable(disableStopBtn);
    }

    private double getAmountFromAmountTF() throws InvalidBidException {
        String amountStr = this.amountTF.getText();
        if(!amountStr.matches("\\d+(\\.\\d+)?")) {
            throw new InvalidBidException("Amount must be a positive number.");
        }
        return Double.parseDouble(amountStr);
    }

    private void setupRobot() {
        Strategy strategy = Objects.requireNonNull(RobotBidder.getRobotBidder(this.strategyCB.getSelectionModel()
                .getSelectedItem()))
                .getStrategy();
        this.robot = new Robot(this.bidder, this.auction, strategy);
        this.robot.start();
    }

    private boolean hasRobot() {
        return this.robot != null;
    }

    private void terminateRobot() {
        this.robot.stop();
        this.robot = null;
    }

    private void updateCurrentBidLbl() {
        try {
            this.currentBidLbl.setText(this.auction.getCurrentBid().toString());
        } catch (NoBidAvailableException e) {
            this.currentBidLbl.setText("---");
        }
    }
}
