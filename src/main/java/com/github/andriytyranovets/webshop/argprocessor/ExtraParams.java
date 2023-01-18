package com.github.andriytyranovets.webshop.argprocessor;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum ExtraParams {
    VAT("--vat"),
    InputCurrency("--input-currency"),
    OutputCurrency("--output-currency");

    private static String allAvailable;
    private String name;

    ExtraParams(String name) {
        this.name = name;
    }

    public boolean isMatching(String param) {
        return param != null && param.startsWith(name);
    }

    public String getName() {
        return name;
    }

    public static boolean matchesAny(String param) {
        return Arrays.stream(ExtraParams.values()).anyMatch(e -> e.isMatching(param));
    }

    public static ExtraParams match(String param) {
        return Arrays.stream(ExtraParams.values()).filter(e -> e.isMatching(param)).findFirst().get();
    }

    public static String getAvailableExtras() {
        if(allAvailable == null){
            allAvailable = Arrays.stream(ExtraParams.values())
                    .map(p -> "<" + p.name + ">")
                    .collect(Collectors.joining(", "));
        }

        return allAvailable;
    }
}
