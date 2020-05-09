package org.herzig.auction.model;

import java.util.ArrayList;
import java.util.List;

public final class AuctionSystem {
    public static AuctionSystem instance;

    private Catalogue catalogue;
    private List<User> users;

    private AuctionSystem() {
        this.catalogue = new Catalogue();
        this.users = new ArrayList<>();
    }

    public static AuctionSystem getInstance() {
        if(AuctionSystem.instance == null) {
            AuctionSystem.instance = new AuctionSystem();
        }
        return AuctionSystem.instance;
    }

    public Catalogue getCatalogue() {
        return this.catalogue;
    }

    public User login(String username, String password) {
        for(User user : this.users) {
            if(user.isValid(username, password)) {
                return user;
            }
        }
        User user = new User(username, password);
        this.users.add(user);
        return user;
    }
}
