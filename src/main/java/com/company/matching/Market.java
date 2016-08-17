package com.company.matching;

import java.util.*;

class BidComparator implements Comparator<Long> {

    public static final BidComparator INSTANCE = new BidComparator();

    public int compare(Long o1, Long o2) {
        return Long.signum(o2 - o1);
    }
}

class AskComparator implements Comparator<Long> {

    public static final AskComparator INSTANCE = new AskComparator();

    public int compare(Long o1, Long o2) {
        return Long.signum(o1 - o2);
    }
}

public class Market {
    private Map<Instrument, OrderBook> bids;
    private Map<Instrument, OrderBook> asks;

    private MarketListener listener;

    public Market(MarketListener listener) {
        asks = new LinkedHashMap<>();
        bids = new LinkedHashMap<>();

        Arrays.stream(Instrument.values()).forEach(instr -> {
            asks.put(instr, new OrderBook(instr, AskComparator.INSTANCE));
            bids.put(instr, new OrderBook(instr, BidComparator.INSTANCE));
        });
        this.listener = listener;
    }

    public List<Order> getBidOrders(Instrument instrument) {
        return bids.get(instrument).flattenOrders();
    }

    public List<Order> getAskOrders(Instrument instrument) {
        return asks.get(instrument).flattenOrders();
    }

    public void placeOrder(Order order) {
        switch (order.getType()) {
            case BUY:
                buy(order);
                break;
            case SELL:
                sell(order);
                break;
        }
    }

    private void buy(Order order) {
        Order matchedOrder = asks.get(order.getInstrument()).match(order);

        if (matchedOrder != null) {
            listener.match(matchedOrder, order);
        } else {
            bids.get(order.getInstrument()).add(order);
        }

    }

    private void sell(Order order) {
        Order matchedOrder = bids.get(order.getInstrument()).match(order);

        if (matchedOrder != null) {
            listener.match(matchedOrder, order);
        } else {
            asks.get(order.getInstrument()).add(order);
        }
    }

}
