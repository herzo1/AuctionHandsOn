package org.herzig.auction.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Auction {
    private class Ticker extends Thread {
        @Override
        public void run() {
            while (Auction.this.hasTimeLeft() && !isInterrupted()) {
                try {
                    Thread.sleep(1000);
                    synchronized (Auction.this) {
                        if(!Auction.this.hasTimeLeft()) {
                            Auction.this.status = Status.TERMINATED;
                        }
                    }
                    notifyObservers();
                } catch (InterruptedException e) {
                    interrupt();
                }
            }
        }
    }

    private enum Status {
        CREATED, RUNNING, TERMINATED;
    }

    private static final long DURATION = 1;

    private final Item item;
    private Status status;
    private LocalDateTime endTime;

    private final List<AuctionObserver> observers;
    private final List<Bid> bids;

    private final Ticker ticker;

    public Auction(Item item) {
        this.item = item;
        this.status = Status.CREATED;
        this.observers = new ArrayList<>();
        this.bids = new ArrayList<>();
        this.ticker = new Ticker();
    }

    public Item getItem() {
        return this.item;
    }

    public synchronized LocalDateTime getEndTime() {
        return this.endTime;
    }

    public Duration getRemainingTime() {
        return Duration.between(LocalDateTime.now(), this.endTime);
    }

    public synchronized Bid getCurrentBid() throws NoBidAvailableException {
        try{
            return bids.get(bids.size()-1);
        } catch (IndexOutOfBoundsException e) {
            throw new NoBidAvailableException();
        }
    }

    public void addObserver(AuctionObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(AuctionObserver observer) {
        this.observers.remove(observer);
    }

    public void notifyObservers() {
        List<AuctionObserver> copy = new CopyOnWriteArrayList<>(observers);
        copy.forEach(observer -> observer.update(this));
    }

    public synchronized void start() {
        if(!isCreated()) {
            throw new IllegalStateException("Auction is not in status CREATED");
        }
        this.status = Status.RUNNING;
        calculateEndTime();
        ticker.start();
        notifyObservers();

    }

    public synchronized void stop() {
        if(isRunning()) {
            this.ticker.interrupt();
            this.status = Status.TERMINATED;
            notifyObservers();
        }
    }

    public synchronized void placeBid(Bid bid) throws InvalidBidException {
        Bid currentBid;

        try {
            currentBid = getCurrentBid();
            if(bid.getAmount() <= currentBid.getAmount()) {
                throw new InvalidBidException("Amount must be higher than current bid.");
            }
            this.bids.add(bid);
            notifyObservers();
        } catch (NoBidAvailableException e) {
            if(bid.getAmount() < item.getMinimumPrice()) {
                throw new InvalidBidException("Amount must be equal or higher than minimun price.");
            }
            this.bids.add(bid);
            notifyObservers();
        }
    }

    public synchronized boolean isCreated() {
        return this.status == Status.RUNNING;
    }

    public synchronized boolean isRunning() {
        return this.status == Status.RUNNING;
    }

    public synchronized boolean isTerminated() {
        return this.status == Status.TERMINATED;
    }

    private boolean hasTimeLeft() {
        return !getRemainingTime().isNegative();
    }

    private void calculateEndTime() {
        this.endTime = LocalDateTime.now().plusMinutes(Auction.DURATION);
    }
}
