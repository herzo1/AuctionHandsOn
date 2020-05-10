package org.herzig.auction.model.robot;

import org.herzig.auction.model.Bid;
import org.herzig.auction.model.User;

public class Eager extends Strategy {
    @Override
    Bid createBid(User bidder, double amount) {
        return new Bid(bidder, amount + 1);
    }
}
