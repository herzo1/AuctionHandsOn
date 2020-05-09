package org.herzig.auction.model;

public class Item {
    private final String description;
    private final double minimumPrice;

    public Item(String description, double minimumPrice) {
        this.description = description;
        this.minimumPrice = minimumPrice;
    }

    public double getMinimumPrice() {
        return this.minimumPrice;
    }

    public String toString() {
        return this.description + " " + this.minimumPrice;
    }
}
