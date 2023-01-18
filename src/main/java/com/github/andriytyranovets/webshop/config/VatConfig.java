package com.github.andriytyranovets.webshop.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.andriytyranovets.webshop.model.VatRate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class VatConfig {
    private static final String PATH = "configs/vat.json";

    private Map<String, VatRate> ratesForBooks;
    private Map<String, VatRate> ratesForOnline;

    public VatConfig() {
        List<VatRate> rates = ConfigLoader.<List<VatRate>>loadConfig(PATH, new TypeReference<List<VatRate>>() {});
        this.ratesForBooks = rates.stream()
                .filter(r -> "Book".equalsIgnoreCase(r.getType()) || "All".equalsIgnoreCase(r.getType()))
                .collect(Collectors.toUnmodifiableMap(VatRate::getCode, Function.identity()));

        this.ratesForOnline = rates.stream()
                .filter(r -> "Online".equalsIgnoreCase(r.getType()) || "All".equalsIgnoreCase(r.getType()))
                .collect(Collectors.toUnmodifiableMap(VatRate::getCode, Function.identity()));
    }

    public BigDecimal vatForBooks(String code) {
        return BigDecimal.valueOf(ratesForBooks.get(code).getRate());
    }

    public BigDecimal vatForOnline(String code) {
        return BigDecimal.valueOf(ratesForOnline.get(code).getRate());
    }
}
