package com.company.matching;

public enum OrderType {
    BUY('b'),
    SELL('s');

    private final char letter;

    OrderType(char c) {
        this.letter = c;
    }

    public static OrderType valueOf(char letter) throws IllegalArgumentException {
        switch (letter) {
            case 'b':
                return BUY;
            case 's':
                return SELL;
            default:
                throw new IllegalArgumentException("Illegal letter given: '" + letter + "'");
        }
    }
}