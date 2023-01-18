package com.github.andriytyranovets.webshop.model;

import org.immutables.value.Value;

import java.math.BigDecimal;
import java.util.Map;

@Value.Immutable
@Value.Style(typeAbstract = "*", typeImmutable = "*Impl")
public interface Context {
    int getAmount();
    BigDecimal getPrice();
    String getType();
    Map<String, String> getExtras();

    default String getDefaultCurrency() {
        return "DKK";
    }
}
