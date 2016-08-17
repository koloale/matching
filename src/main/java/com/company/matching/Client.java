package com.company.matching;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Client {
    private String id;
    private long balanceUsd;
    private Map<Instrument, Long> balanceInstruments;

    public Client(String id, long balanceUsd, long balanceA, long balanceB, long balanceC, long balanceD) {
        this.id = id;
        this.balanceUsd = balanceUsd;
        LinkedHashMap<Instrument, Long> balances = new LinkedHashMap<>();
        balances.put(Instrument.A, balanceA);
        balances.put(Instrument.B, balanceB);
        balances.put(Instrument.C, balanceC);
        balances.put(Instrument.D, balanceD);
        this.balanceInstruments = Collections.unmodifiableMap(balances);
    }

    public Client(String id, long balanceUsd, Map<Instrument, Long> balanceInstruments) {
        this.id = id;
        this.balanceUsd = balanceUsd;
        this.balanceInstruments = balanceInstruments;
    }

    public String getId() {
        return id;
    }

    public long getBalanceUsd() {
        return balanceUsd;
    }

    public Map<Instrument, Long> getBalances() {
        return balanceInstruments;
    }

    public long getBalanceInstrument(Instrument instrument) {
        return balanceInstruments.get(instrument);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        return new EqualsBuilder()
                .append(balanceUsd, client.balanceUsd)
                .append(id, client.id)
                .append(balanceInstruments, client.balanceInstruments)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(balanceUsd)
                .append(balanceInstruments)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("balanceUsd", balanceUsd)
                .append("balanceInstruments", balanceInstruments)
                .toString();
    }

    public String toOutputString() {
        return Stream.concat(Stream.of(id, balanceUsd), balanceInstruments.values().stream())
                .map(String::valueOf)
                .collect(Collectors.joining("\t"));
    }
}
