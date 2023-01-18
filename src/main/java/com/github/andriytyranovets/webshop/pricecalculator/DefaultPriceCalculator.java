package com.github.andriytyranovets.webshop.pricecalculator;

import com.github.andriytyranovets.webshop.freightfare.FreightFareCalculator;
import com.github.andriytyranovets.webshop.model.Context;
import com.github.andriytyranovets.webshop.pricingfactor.PricingFactor;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class DefaultPriceCalculator implements PriceCalculator {
    private FreightFareCalculator freightFareCalculator;
    private List<PricingFactor> unitPriceAdjustments;
    private List<PricingFactor> totalPriceAdjustments;

    public DefaultPriceCalculator(FreightFareCalculator freightFareCalculator) {
        this.freightFareCalculator = freightFareCalculator;
        this.unitPriceAdjustments = new LinkedList<>();
        this.totalPriceAdjustments = new LinkedList<>();
    }

    @Override
    public BigDecimal calculatePrice(Context context) {
        var fare = this.freightFareCalculator.getRate(context.getAmount());
        var unitPrice = unitPriceAdjustments.stream().reduce(context.getPrice(), (a, b) -> b.adjustPrice(a), BigDecimal::add);
        var basePrice = unitPrice.multiply(BigDecimal.valueOf(context.getAmount())).add(fare);
        return totalPriceAdjustments.stream().reduce(basePrice, (a, b) -> b.adjustPrice(a), BigDecimal::add);
    }

    @Override
    public void addUnitPriceAdjustment(PricingFactor factor) {
        this.unitPriceAdjustments.add(factor);
    }

    @Override
    public void addAllUnitPriceAdjustments(Collection<PricingFactor> factors) {
        this.unitPriceAdjustments.addAll(factors);
    }

    @Override
    public void addTotalPriceAdjustment(PricingFactor factor) {
        this.totalPriceAdjustments.add(factor);
    }

    @Override
    public void addAllTotalPriceAdjustments(Collection<PricingFactor> factors) {
        this.totalPriceAdjustments.addAll(factors);
    }
}
