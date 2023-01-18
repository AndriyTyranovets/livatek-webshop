package com.github.andriytyranovets.webshop.pricingfactor;

import java.math.BigDecimal;

public class CurrencyConversionFactor implements PricingFactor {
    private BigDecimal conversionRate;

    public CurrencyConversionFactor(BigDecimal conversionRate) {
        this.conversionRate = conversionRate;
    }

    @Override
    public BigDecimal adjustPrice(BigDecimal original) {
        return original.multiply(conversionRate);
    }
}
