package com.company.matching;

public interface MarketListener {
    void match(Order matchedOrder, Order incomingOrder);
}
