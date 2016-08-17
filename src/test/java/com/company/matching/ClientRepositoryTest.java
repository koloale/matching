package com.company.matching;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClientRepositoryTest {
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
    public void testSellLimit() {
        market.placeOrder(new Order("C1", OrderType.BUY, Instrument.D, 100, 5));

        market.placeOrder(new Order("C2", OrderType.SELL, Instrument.D, 100, 5));

        assertEquals(new Client("C1", 500, 10, 5, 15, 5),
                clientRepository.find("C1"));
        assertEquals(new Client("C2", 2500, 3, 35, 40, 5),
                clientRepository.find("C2"));
    }

    @Test
    public void testBuyMarket() {
        market.placeOrder(new Order("C1", OrderType.SELL, Instrument.A, 100, 10));
        market.placeOrder(new Order("C2", OrderType.SELL, Instrument.B, 50, 30));

        market.placeOrder(new Order("C1", OrderType.BUY, Instrument.B, 100, 30));

        assertEquals(new Client("C1", -500, 10, 35, 15, 0),
                clientRepository.find("C1"));
        assertEquals(new Client("C2", 3500, 3, 5, 40, 10),
                clientRepository.find("C2"));
    }


    @Test
    public void testSellMarket() {
        market.placeOrder(new Order("C1", OrderType.BUY, Instrument.A, 100, 10));
        market.placeOrder(new Order("C2", OrderType.BUY, Instrument.C, 50, 30));

        market.placeOrder(new Order("C2", OrderType.SELL, Instrument.A, 90, 10));

        assertEquals(new Client("C1", 0, 20, 5, 15, 0),
                clientRepository.find("C1"));
        assertEquals(new Client("C2", 3000, -7, 35, 40, 10),
                clientRepository.find("C2"));
    }
}
