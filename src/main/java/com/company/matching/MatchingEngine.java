package com.company.matching;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MatchingEngine {

    public static void main(String[] args) throws IOException {
        if (args.length != 3)
            usage();

        ClientRepository clientRepository =  new ClientRepository();
        Market market = new Market(clientRepository);

        readClients(Paths.get(args[0])).forEach(clientRepository::save);
        clientRepository.printInstrumentsDepo("Instruments Depo Before: ");

        readOrders(Paths.get(args[1])).forEach(market::placeOrder);

        writeClientsToFile(clientRepository.findAll(), Paths.get(args[2]));

        System.out.println("Processing orders finished.");
        clientRepository.printInstrumentsDepo("Instruments Depo After:  ");
    }

    static List<Client> readClients(Path path) {

        ArrayList<Client> clients = new ArrayList<>();
        try(Stream<String> lines = Files.lines(path)) {
            lines.forEach(s -> {
                String[] fields = s.split("\\s+");
                if (fields.length == 6) {
                    try {
                        clients.add(new Client(
                                fields[0]
                                , Long.valueOf(fields[1])
                                , Long.valueOf(fields[2])
                                , Long.valueOf(fields[3])
                                , Long.valueOf(fields[4])
                                , Long.valueOf(fields[5])
                        ));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return clients;
    }

    static List<Order> readOrders(Path path) {

        ArrayList<Order> clients = new ArrayList<>();
        try(Stream<String> lines = Files.lines(path)) {
            lines.forEach(s -> {
                String[] fields = s.split("\\s+");
                if (fields.length == 5) {
                    try {
                        clients.add(new Order(
                                fields[0]
                                , OrderType.valueOf(fields[1].charAt(0))
                                , Instrument.valueOf(fields[2])
                                , Long.valueOf(fields[3])
                                , Long.valueOf(fields[4])
                        ));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return clients;
    }

    static void writeClientsToFile(List<Client> clients, Path path) {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(clients.stream().map(Client::toOutputString).collect(Collectors.joining("\n")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void usage() {
        System.err.println("Usage: matching <clients-file> <orders-file> <result-file>");
        System.exit(2);
    }
}
