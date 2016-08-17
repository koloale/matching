package com.company.matching;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

class Order {

    private String  clientId;
    private OrderType type;
    private Instrument instrument;
    private long price;
    private long quantity;

    public Order(String clientId, OrderType type, Instrument instrument, long price, long quantity) {
        this.clientId = clientId;
        this.type = type;
        this.instrument = instrument;
        this.price = price;
        this.quantity = quantity;
    }

    public String getClientId() {
        return clientId;
    }

    public OrderType getType() {
        return type;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public long getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return new EqualsBuilder()
                .append(price, order.price)
                .append(quantity, order.quantity)
                .append(clientId, order.clientId)
                .append(type, order.type)
                .append(instrument, order.instrument)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(clientId)
                .append(type)
                .append(instrument)
                .append(price)
                .append(quantity)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("clientId", clientId)
                .append("type", type)
                .append("instrument", instrument)
                .append("price", price)
                .append("quantity", quantity)
                .toString();
    }
}
