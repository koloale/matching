package com.company.matching;

import java.util.*;
import java.util.stream.Collectors;

public class ClientRepository implements MarketListener {
    private Map<String, Client> clients = new LinkedHashMap<>();

    @Override
    public void match(Order matchedOrder, Order incomingOrder) {
        Client matchedClient = find(matchedOrder.getClientId());
        Client incomingClient = find(incomingOrder.getClientId());
        save(fulfillClientOrder(matchedClient.getId(), matchedOrder, matchedOrder.getPrice()));
        save(fulfillClientOrder(incomingClient.getId(), incomingOrder, matchedOrder.getPrice()));

    }

    public void printInstrumentsDepo(String prefix) {
        HashMap<Instrument, Long> allInstrument = new LinkedHashMap<>();
        for (Client client : findAll()) {
            for (Map.Entry<Instrument, Long> e : client.getBalances().entrySet()) {
                Long instrQuantity = allInstrument.get(e.getKey());
                if (instrQuantity == null) {
                    instrQuantity = 0L;
                    allInstrument.put(e.getKey(), instrQuantity);
                }
                allInstrument.put(e.getKey(), instrQuantity + e.getValue());
            }
        }

        System.out.println(prefix + allInstrument.entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining(", ")));
    }

    public Client find(String clientId) {
        return clients.get(clientId);
    }

    public void save(Client client) {
        clients.put(client.getId(), client);
    }

    public Client fulfillClientOrder(String clientId, Order order, long price) {
        int instrSign = order.getType() == OrderType.BUY ? 1 : -1;
        Client client = find(clientId);
        long newInstrQuantity = client.getBalanceInstrument(order.getInstrument()) + instrSign * order.getQuantity();
        long newBalanceUsd = client.getBalanceUsd() - instrSign * order.getQuantity() * price;
        Map<Instrument, Long> instrBalances = new LinkedHashMap<>(client.getBalances());
        instrBalances.put(order.getInstrument(), newInstrQuantity);

        return new Client(client.getId(),
                newBalanceUsd,
                instrBalances
                );

    }

    public List<Client> findAll() {
        return new ArrayList<>(clients.values());
    }
}
