package com.github.andriytyranovets.webshop.priceadjustment;

import com.github.andriytyranovets.webshop.pricingfactor.PricingFactor;

import java.util.Optional;

public interface PriceAdjustmentService {
    Optional<PricingFactor> getVatAdjustment();
    Optional<PricingFactor> getInputCurrencyAdjustment();
    Optional<PricingFactor> getOutputCurrencyAdjustment();
}
