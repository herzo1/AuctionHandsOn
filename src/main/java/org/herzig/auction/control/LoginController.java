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
            String username = this.usernameTF.getText();
            String password = this.passwordPF.getText();
            if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
                User user = AuctionSystem.getInstance().login(username, password);

                ViewHelper view = new ViewHelper(View.BIDDER_VIEW);

                BidderController controller = ((BidderController)view.getController());
                controller.setAuction(auction);
                controller.setBidder(user);

                view.showView();

                close();
            }
        } else {
            close();
        }
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
        this.auction.addObserver(this);
    }

    @Override
    void handleUpdate(Auction auction) {
        if(auction.isTerminated()) {
            close();
        }
    }

    private void close() {
        this.auction.removeObserver(this);
        ((Stage)this.usernameTF.getScene().getWindow()).close();
    }
}
