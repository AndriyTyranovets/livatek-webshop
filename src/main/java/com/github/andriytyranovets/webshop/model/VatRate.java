package com.github.andriytyranovets.webshop.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@Value.Style(typeAbstract = "*", typeImmutable = "*Impl")
@JsonDeserialize(as = VatRateImpl.class)
public interface VatRate {
    String getCode();
    String getType();
    double getRate();
}
