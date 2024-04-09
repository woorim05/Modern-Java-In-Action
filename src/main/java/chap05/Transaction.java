package chap05;

import java.util.Objects;

public class Transaction {

    private Trader trader;
    private int year;
    private int value;
    private String referenceCode;

    public Transaction(Transaction t) {
        this.trader = t.getTrader();
        this.year = t.getYear();
        this.value = t.getValue();
        this.referenceCode = t.getReferenceCode();
    }

    public void setTrader(Trader trader) {
        this.trader = trader;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }


    public Transaction(Trader trader, int year, int value, String rc) {
        this.trader = trader;
        this.year = year;
        this.value = value;
        this.referenceCode = rc;
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

    public String getReferenceCode() {
        return referenceCode;
    }



    @SuppressWarnings("boxing")
    @Override
    public String toString() {
        return String.format("{%s, year: %d, value: %d, referenceCode: %s}", trader, year, value, referenceCode);
    }

}
