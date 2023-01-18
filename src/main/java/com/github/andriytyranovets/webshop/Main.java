package com.github.andriytyranovets.webshop;

import com.github.andriytyranovets.webshop.argprocessor.ArgProcessor;
import com.github.andriytyranovets.webshop.argprocessor.ExtraParams;
import com.github.andriytyranovets.webshop.model.Context;

import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        //process args and create "context" from args
        var context = new ArgProcessor(args).getContext();

        //call base price module with context
        var modules = new DefaultModule(context);
        var priceCalculator = modules.getDefaultPriceCalculator();
        var basePrice = priceCalculator.calculatePrice(context);
        System.out.println(String.join(" ", "1. Base price:", basePrice.toString(), context.getDefaultCurrency()));

        //call adjusted price module with context
        var adjustedPriceCalculator = modules.getDefaultPriceCalculator();
        var priceAdjustmentService = modules.getDefaultPriceAdjustmentService();
        var inputCurrencyAdjustment = priceAdjustmentService.getInputCurrencyAdjustment();
        inputCurrencyAdjustment.ifPresent(adjustedPriceCalculator::addUnitPriceAdjustment);
        List.of(
                        priceAdjustmentService.getVatAdjustment(),
                        priceAdjustmentService.getOutputCurrencyAdjustment()
                ).stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(adjustedPriceCalculator::addTotalPriceAdjustment);
        var adjustedPrice = adjustedPriceCalculator.calculatePrice(context);
        System.out.println(String.join(" ",
                "2. Adjusted price:",
                adjustedPrice.setScale(2, RoundingMode.HALF_UP).toString(),
                getOutputCurrency(context)));
    }

    private static String getOutputCurrency(Context context) {
        return context.getExtras().getOrDefault(ExtraParams.OutputCurrency.getName(), context.getDefaultCurrency());
    }
}
