package com.github.andriytyranovets.webshop.pricecalculator;

import com.github.andriytyranovets.webshop.model.Context;
import com.github.andriytyranovets.webshop.pricingfactor.PricingFactor;

import java.math.BigDecimal;
import java.util.Collection;

public interface PriceCalculator {
    BigDecimal calculatePrice(Context context);

    void addUnitPriceAdjustment(PricingFactor factor);

    void addAllUnitPriceAdjustments(Collection<PricingFactor> factors);

    void addTotalPriceAdjustment(PricingFactor factor);

    void addAllTotalPriceAdjustments(Collection<PricingFactor> factors);
}
