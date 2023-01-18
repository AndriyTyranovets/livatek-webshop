package com.github.andriytyranovets.webshop.priceadjustment;

import com.github.andriytyranovets.webshop.argprocessor.ExtraParams;
import com.github.andriytyranovets.webshop.config.CurrencyConfig;
import com.github.andriytyranovets.webshop.config.VatConfig;
import com.github.andriytyranovets.webshop.model.Context;
import com.github.andriytyranovets.webshop.pricingfactor.CurrencyConversionFactor;
import com.github.andriytyranovets.webshop.pricingfactor.PricingFactor;
import com.github.andriytyranovets.webshop.pricingfactor.VatFactor;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Predicate;

public class DefaultPriceAdjustmentService implements PriceAdjustmentService {
    private Context context;
    private VatConfig vatConfig;
    private CurrencyConfig currencyConfig;

    public DefaultPriceAdjustmentService(Context context, VatConfig vatConfig, CurrencyConfig currencyConfig) {
        this.context = context;
        this.vatConfig = vatConfig;
        this.currencyConfig = currencyConfig;
    }

    @Override
    public Optional<PricingFactor> getVatAdjustment() {
        return Optional.ofNullable(context.getExtras().get(ExtraParams.VAT.getName()))
                .map(this::codeToRate)
                .map(VatFactor::new);
    }

    @Override
    public Optional<PricingFactor> getInputCurrencyAdjustment() {
        return Optional.ofNullable(context.getExtras().get(ExtraParams.InputCurrency.getName()))
                .filter(Predicate.not(context.getDefaultCurrency()::equalsIgnoreCase))
                .map(c -> currencyConfig.getConversionRate(c, context.getDefaultCurrency()))
                .map(CurrencyConversionFactor::new);
    }

    @Override
    public Optional<PricingFactor> getOutputCurrencyAdjustment() {
        return Optional.ofNullable(context.getExtras().get(ExtraParams.OutputCurrency.getName()))
                .filter(Predicate.not(context.getDefaultCurrency()::equalsIgnoreCase))
                .map(c -> currencyConfig.getConversionRate(context.getDefaultCurrency(), c))
                .map(CurrencyConversionFactor::new);
    }

    private BigDecimal codeToRate(String countryCode) {
        return "book".equalsIgnoreCase(context.getType())
                ? vatConfig.vatForBooks(countryCode)
                : vatConfig.vatForOnline(countryCode);
    }
}
