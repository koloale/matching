package com.company.matching;

import org.junit.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;


public class ClientsReaderTest {
    @Test
    public void testReadClientsFile() throws URISyntaxException {
        Path path = Paths.get(this.getClass().getResource("/clients-test.txt").toURI());
        List<Client> clients = MatchingEngine.readClients(path);

        assertEquals(asList(
                new Client("C1", 1000, 130, 240, 760, 320)
                , new Client("C2", 4350, 370, 120, 950, 560)
                , new Client("C3", 2760, 0, 0, 0, 0)
                , new Client("C4", 560, 450, 540, 480, 950)
                , new Client("C5", 1500, 0, 0, 400, 100)
                ),
                clients);
    }
}
