package com.company.matching;

import org.junit.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class OrdersReaderTest {
    @Test
    public void testReadClientsFile() throws URISyntaxException {
        Path path = Paths.get(this.getClass().getResource("/orders-test.txt").toURI());
        List<Order> orders = MatchingEngine.readOrders(path);

        assertEquals(asList(
                new Order("C8", OrderType.BUY, Instrument.C, 15, 4)
                , new Order("C2", OrderType.SELL, Instrument.C, 14, 5)
                , new Order("C2", OrderType.SELL, Instrument.C, 13, 2)
                , new Order("C9", OrderType.BUY, Instrument.B, 6, 4)
                , new Order("C4", OrderType.BUY, Instrument.D, 5, 4)),
                orders);

    }
}
