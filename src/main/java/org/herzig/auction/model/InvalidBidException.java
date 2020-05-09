package org.herzig.auction.model;

public class InvalidBidException extends Exception {
    public InvalidBidException(String msg) {
        super(msg);
    }
}
