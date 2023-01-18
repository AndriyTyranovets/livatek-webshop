package com.github.andriytyranovets.webshop.freightfare;

import java.math.BigDecimal;

public interface FreightFareCalculator {
    BigDecimal getRate(int amount);
}
