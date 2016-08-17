package com.company.matching;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Orders {
    private ArrayList<Order> orders = new ArrayList<>();
    private long price;

    public Orders(long price) {
        this.price = price;
    }

    public long getPrice() {
        return price;
    }

    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }

    public void add(Order order) {
        assert order.getPrice() == price;

        orders.add(order);
    }

    public Order match(Order order) {
        for (Order placedOrder : orders) {
           if (placedOrder.getQuantity() == order.getQuantity()
                   && placedOrder.getInstrument() == order.getInstrument()
                   && placedOrder.getType() != order.getType()) {
               orders.remove(placedOrder);
               return placedOrder;
           }
        }

        return null;
    }

    public boolean isEmpty() {
        return orders.isEmpty();
    }
}
