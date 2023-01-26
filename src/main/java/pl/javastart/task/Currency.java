package pl.javastart.task;

import java.math.BigDecimal;

public class Currency {
    private String designation;
    private BigDecimal rateToEur;

    public Currency(String designation, BigDecimal rateToEur) {
        this.designation = designation;
        this.rateToEur = rateToEur;
    }

    public String getDesignation() {
        return designation;
    }

    public BigDecimal getRateToEur() {
        return rateToEur;
    }
}
