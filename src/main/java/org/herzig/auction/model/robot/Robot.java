package org.herzig.auction.model.robot;

import org.herzig.auction.model.*;

import java.util.Random;

public final class Robot implements Runnable {
    private final Random random;
    private final User bidder;
    private final Auction auction;
    private final Strategy strategy;
    private Thread thread;

    public Robot(User bidder, Auction auction, Strategy strategy) {
        this.random = new Random();
        this.bidder = bidder;
        this.auction = auction;
        this.strategy = strategy;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted() && !this.auction.getStatus().equals(Auction.Status.TERMINATED)) {
            int sleepTime = (5 + this.random.nextInt(10)) * 1000;
            try {
                synchronized (this.auction) {
                    if (!this.auction.getStatus().equals(Auction.Status.TERMINATED)) {
                        try {
                            Bid currentBid = this.auction.getCurrentBid();
                            if (!currentBid.getBidder().equals(this.bidder)) {
                                double lastAmount = currentBid.getAmount();
                                Bid bid = this.strategy.createBid(this.bidder, lastAmount);
                                this.auction.placeBid(bid);
                            }
                        } catch (NoBidAvailableException e) {
                            // do nothing
                        }

                    }
                }
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch(InvalidBidException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        this.thread = new Thread(this);
        this.thread.start();
    }

    public void stop() {
        if(this.thread != null) {
            this.thread.interrupt();
            this.thread = null;
        }
    }
}
