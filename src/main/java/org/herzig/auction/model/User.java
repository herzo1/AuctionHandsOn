package org.herzig.auction.model;

public class User {
    private final String username;
    private final String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean isValid(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public String getUserName() {
        return this.username;
    }
}
