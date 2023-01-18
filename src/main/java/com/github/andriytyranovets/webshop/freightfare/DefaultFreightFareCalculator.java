package com.github.andriytyranovets.webshop.freightfare;

import java.math.BigDecimal;

public class DefaultFreightFareCalculator implements FreightFareCalculator {
    public final static int BASE_FARE = 50;
    public final static int SURCHARGE = 25;

    @Override
    public BigDecimal getRate(int amount) {
        var baseFare = BigDecimal.valueOf(BASE_FARE);
        var surcharge = BigDecimal.valueOf((amount / 10 - (amount % 10 == 0 ? 1L : 0L)) * SURCHARGE);
        return   amount <= 10 ? baseFare : baseFare.add(surcharge);
    }
}
