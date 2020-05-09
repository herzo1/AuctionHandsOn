package org.herzig.auction.model;

import java.util.ArrayList;
import java.util.List;

public class Catalogue {
    private List<Item> items;

    public Catalogue() {
        this.items = new ArrayList<>();
        this.items.add(new Item("Bike", 40.0));
        this.items.add(new Item("Book", 5.0));
    }

    public List<Item> getItems() {
        return items;
    }
}
