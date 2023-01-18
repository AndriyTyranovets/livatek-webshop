package com.github.andriytyranovets.webshop.pricingfactor;

import java.math.BigDecimal;

public class VatFactor implements PricingFactor {
    private BigDecimal vat;

    public VatFactor(BigDecimal vat) {
        this.vat = BigDecimal.ONE.add(vat);
    }

    @Override
    public BigDecimal adjustPrice(BigDecimal original) {
        return original.multiply(vat);
    }
}
