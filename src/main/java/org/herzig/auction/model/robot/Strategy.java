package org.herzig.auction.model.robot;

import org.herzig.auction.model.Bid;
import org.herzig.auction.model.User;

public abstract class Strategy {

    abstract Bid createBid(User bidder, double amount);
}
