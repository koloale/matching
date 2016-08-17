package com.company.matching;

import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class MarketTest {

    private ClientRepository clientRepository;
    private Market market;

    @Before
    public void setUp() {
        clientRepository = new ClientRepository();
        clientRepository.save(new Client("C1", 1000, 10, 5, 15, 0));
        clientRepository.save(new Client("C2", 2000, 3, 35, 40, 10));
        clientRepository.save(new Client("C3", 3000, 0, 15, 0, 20));
        market = new Market(clientRepository);
    }

    @Test
    public void testPlaceBuy() {
        market.placeOrder(new Order("C1", OrderType.BUY, Instrument.A, 100, 10));
        market.placeOrder(new Order("C1", OrderType.BUY, Instrument.A, 105, 15));
        market.placeOrder(new Order("C2", OrderType.BUY, Instrument.A, 100, 10));

        assertEquals(asList(
                new Order("C1", OrderType.BUY, Instrument.A, 105, 15),
                new Order("C1", OrderType.BUY, Instrument.A, 100, 10),
                new Order("C2", OrderType.BUY, Instrument.A, 100, 10)),
                market.getBidOrders(Instrument.A));
    }

    @Test
    public void testPlaceSell() {
        market.placeOrder(new Order("C1", OrderType.SELL, Instrument.A, 100, 10));
        market.placeOrder(new Order("C1", OrderType.SELL, Instrument.A, 100, 10));
        market.placeOrder(new Order("C2", OrderType.SELL, Instrument.A, 110, 1));
        market.placeOrder(new Order("C1", OrderType.SELL, Instrument.A, 99, 100));

        assertEquals(asList(
                new Order("C1", OrderType.SELL, Instrument.A, 99, 100),
                new Order("C1", OrderType.SELL, Instrument.A, 100, 10),
                new Order("C1", OrderType.SELL, Instrument.A, 100, 10),
                new Order("C2", OrderType.SELL, Instrument.A, 110, 1)),
                market.getAskOrders(Instrument.A));
    }

    @Test
    public void testSellMarket() {
        market.placeOrder(new Order("C1", OrderType.BUY, Instrument.A, 105, 15));

        market.placeOrder(new Order("C3", OrderType.SELL, Instrument.A, 105, 15));

        assertEquals(asList(),
                market.getBidOrders(Instrument.A));
        assertEquals(asList(),
                market.getAskOrders(Instrument.A));
    }

    @Test
    public void testSellNotEnoughQuantity() {
        market.placeOrder(new Order("C1", OrderType.BUY, Instrument.A, 105, 10));

        market.placeOrder(new Order("C3", OrderType.SELL, Instrument.A, 105, 15));

        assertEquals(asList(new Order("C1", OrderType.BUY, Instrument.A, 105, 10)),
                market.getBidOrders(Instrument.A));
        assertEquals(asList(new Order("C3", OrderType.SELL, Instrument.A, 105, 15)),
                market.getAskOrders(Instrument.A));
    }

    @Test
    public void testBuyNotEnoughQuantity() {
        market.placeOrder(new Order("C1", OrderType.SELL, Instrument.A, 105, 10));

        market.placeOrder(new Order("C3", OrderType.BUY, Instrument.A, 105, 15));

        assertEquals(asList(new Order("C3", OrderType.BUY, Instrument.A, 105, 15)),
                market.getBidOrders(Instrument.A));
        assertEquals(asList(new Order("C1", OrderType.SELL, Instrument.A, 105, 10)),
                market.getAskOrders(Instrument.A));
    }

    @Test
    public void testBuyDifferentInstruments() {
        market.placeOrder(new Order("C1", OrderType.SELL, Instrument.A, 105, 10));

        market.placeOrder(new Order("C3", OrderType.BUY, Instrument.B, 105, 10));

        assertEquals(asList(new Order("C3", OrderType.BUY, Instrument.B, 105, 10)),
                market.getBidOrders(Instrument.B));
        assertEquals(asList(),
                market.getBidOrders(Instrument.A));
        assertEquals(asList(new Order("C1", OrderType.SELL, Instrument.A, 105, 10)),
                market.getAskOrders(Instrument.A));
        assertEquals(asList(),
                market.getAskOrders(Instrument.B));
    }
}
