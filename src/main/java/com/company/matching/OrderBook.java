package com.company.matching;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

public class OrderBook {
    private TreeMap<Long, Orders> priceOrders;
    private Comparator<Long> comparator;
    private Instrument instrument;

    public OrderBook(Instrument instrument, Comparator<Long> comparator) {
        this.instrument = instrument;
        this.priceOrders = new TreeMap<Long, Orders>(comparator);
        this.comparator = comparator;
    }

    public Orders getBestOrders() {
        if (priceOrders.isEmpty())
            return null;

        return priceOrders.get(priceOrders.firstKey());
    }

    public void add(Order order) {
        Orders orders = priceOrders.get(order.getPrice());
        if (orders == null) {
            orders = new Orders(order.getPrice());
        }

        orders.add(order);
        priceOrders.put(orders.getPrice(), orders);
    }

    public Order match(Order order) {
        Orders bestOrders = getBestOrders();

        if (bestOrders != null &&
                comparator.compare(bestOrders.getPrice(), order.getPrice()) <= 0) {
            Order matched = bestOrders.match(order);
            if (bestOrders.isEmpty()) {
                delete(bestOrders);
            }
            return matched;
        }

        return null;
    }

    public void delete(Orders orders) {
        priceOrders.remove(orders.getPrice());
    }

    public List<Order> flattenOrders() {
        ArrayList<Order> ordersList = new ArrayList<>();
        for (Orders orders : priceOrders.values()) {
            ordersList.addAll(orders.getOrders());
        }
        return ordersList;
    }
}
