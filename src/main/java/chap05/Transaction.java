package chap05;

import java.util.Objects;

public class Transaction {

    private Trader trader;
    private int year;
    private int value;

    public Transaction(Trader trader, int year, int value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    public Trader getTrader() {
        return trader;
    }

    public int getYear() {
        return year;
    }

    public int getValue() {
        return value;
    }

    @SuppressWarnings("boxing")
    @Override
    public String toString() {
        return String.format("{%s, year: %d, value: %d}", trader, year, value);
    }

}
