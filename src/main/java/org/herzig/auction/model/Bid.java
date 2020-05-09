package org.herzig.auction.model;

public class Bid {
    private final User bidder;
    private final double amount;

    public Bid(User bidder, double amount) {
        this.bidder = bidder;
        this.amount = amount;
    }

    public User getBidder() {
        return this.bidder;
    }

    public double getAmount() {
        return this.amount;
    }

    public String toString() {
        return this.amount + " by " + bidder.getUserName();
    }
}
