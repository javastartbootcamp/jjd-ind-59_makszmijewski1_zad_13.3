package pl.javastart.task;

import java.math.BigDecimal;

public class Product {
    private String name;
    private String currency;
    private BigDecimal priceInCurrency;
    private BigDecimal priceInEuro;

    public Product(String name, String currency, BigDecimal priceInCurrency) {
        this.name = name;
        this.currency = currency;
        this.priceInCurrency = priceInCurrency;
    }

    public void setPriceInEuro(BigDecimal priceInEuro) {
        this.priceInEuro = priceInEuro;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getPriceInCurrency() {
        return priceInCurrency;
    }

    public BigDecimal getPriceInEuro() {
        return priceInEuro;
    }
}
