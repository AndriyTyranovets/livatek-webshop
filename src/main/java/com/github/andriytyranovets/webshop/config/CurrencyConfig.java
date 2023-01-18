package com.github.andriytyranovets.webshop.config;

import com.fasterxml.jackson.core.type.TypeReference;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Map;

public final class CurrencyConfig {
    private static final String PATH = "configs/currencies.json";

    private Map<String, Double> currencies;
    public CurrencyConfig() {
        this.currencies = ConfigLoader.loadConfig(PATH, new TypeReference<Map<String, Double>>() {});
    }

    public BigDecimal getConversionRate(String from, String to) {
        if(from == to) {
            return BigDecimal.ONE;
        }
        return BigDecimal.valueOf(currencies.get(from))
                .divide(BigDecimal.valueOf(currencies.get(to)), MathContext.DECIMAL64);
    }
}
