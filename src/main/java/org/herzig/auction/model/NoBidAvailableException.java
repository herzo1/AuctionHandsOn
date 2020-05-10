package org.herzig.auction.model;

public class NoBidAvailableException extends Exception {
    public NoBidAvailableException() {
        super("No bid is made in the moment.");
    }
}
