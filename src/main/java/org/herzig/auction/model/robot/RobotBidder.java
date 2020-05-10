package org.herzig.auction.model.robot;

public enum RobotBidder {
    EAGER("Eager", new Eager()),
    LIMITED("Limited", new Limited()),
    AWAITING("Awaiting", new Awaiting());

    private final String name;
    private final Strategy strategy;

    public static RobotBidder getRobotBidder(String name) {
        for(RobotBidder robot : RobotBidder.values()){
            if(robot.getName().equals(name)) {
                return robot;
            }
        }
        return null;
    }

    RobotBidder(String name, Strategy strategy) {
        this.name = name;
        this.strategy = strategy;
    }

    public String getName() {
        return this.name;
    }

    public Strategy getStrategy() {
        return this.strategy;
    }
}
