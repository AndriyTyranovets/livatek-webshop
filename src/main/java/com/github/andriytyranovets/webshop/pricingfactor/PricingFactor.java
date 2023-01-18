package com.github.andriytyranovets.webshop.pricingfactor;

import java.math.BigDecimal;

public interface PricingFactor {
    BigDecimal adjustPrice(BigDecimal original);
}
