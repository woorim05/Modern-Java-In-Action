package chap05;

import java.util.Objects;

public class Trader {

    private String name;
    private String city;

    public Trader(String n, String c) {
        name = n;
        city = c;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String newCity) {
        city = newCity;
    }

    @Override
    public String toString() {
        return String.format("Trader:%s in %s", name, city);
    }

}
