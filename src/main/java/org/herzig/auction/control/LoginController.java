package org.herzig.auction.control;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.herzig.auction.control.helper.View;
import org.herzig.auction.control.helper.ViewHelper;
import org.herzig.auction.model.Auction;
import org.herzig.auction.model.AuctionSystem;
import org.herzig.auction.model.User;
import org.herzig.auction.model.robot.Strategy;

import java.io.IOException;

public class LoginController extends BaseController {
    private Auction auction;

    @FXML
    private TextField usernameTF;

    @FXML
    private PasswordField passwordPF;

    @FXML
    public void login() throws IOException {
        if (this.auction.isRunning()) {
            loginUserAndOpenBidderView();
        } else {
            close();
        }
    }

    @Override
    void handleUpdate(Auction auction) {
        if(auction.isTerminated()) {
            close();
        }
    }

    private void loginUserAndOpenBidderView() throws IOException {
        if (validateUserInput()) {
            showBidderView(loginUser());
            close();
        }
    }

    private User loginUser(){
        return AuctionSystem.getInstance().login(this.usernameTF.getText(), this.passwordPF.getText());
    }

    private void showBidderView(User user) throws IOException {
        ViewHelper view = new ViewHelper(View.BIDDER_VIEW, user.getUserName());

        BidderController controller = ((BidderController)view.getController());
        controller.setAuction(auction);
        controller.setBidder(user);

        view.showView();
    }

    private boolean validateUserInput() {
        return validateText(this.usernameTF.getText()) &&
                validateText(this.passwordPF.getText());
    }

    private boolean validateText(String text) {
        return text != null && !text.isEmpty();
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
        this.auction.addObserver(this);
    }

    private void close() {
        this.auction.removeObserver(this);
        ((Stage)this.usernameTF.getScene().getWindow()).close();
    }
}
