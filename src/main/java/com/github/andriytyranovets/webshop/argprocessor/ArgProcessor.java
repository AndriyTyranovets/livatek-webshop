package com.github.andriytyranovets.webshop.argprocessor;

import com.github.andriytyranovets.webshop.model.Context;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArgProcessor {
    private final static Set<String> knownParams = Set.of("--vat", "--input-currency", "--output-currency");
    private final static Set<String> validTypes = Set.of("book", "online");

    private Context context;

    public ArgProcessor(String[] args) {
        collectArgs(args);
    }

    public Context getContext() {
        return context;
    }

    private void collectArgs(String[] args) {
        if (args.length < 3)
            showUsageAndFail(InvalidParams.NotEnoughArguments);

        int amount = Integer.MIN_VALUE;
        try {
            amount = Integer.parseInt(args[0]);
        } catch (NumberFormatException ex) {
            showUsageAndFail(InvalidParams.InvalidAmount);
        }

        BigDecimal price = BigDecimal.ONE.negate();
        try {
            price = new BigDecimal(args[1]);
        } catch (NumberFormatException ex) {
            showUsageAndFail(InvalidParams.InvalidPrice);
        }

        var type = args[2];
        if(validTypes.stream().noneMatch(t -> t.equalsIgnoreCase(type)))
            showUsageAndFail(InvalidParams.InvalidType);

        var otherArgs = Stream.of(args)
                .skip(3)
                .collect(Collectors.toSet());
        validateExtras(otherArgs);
        var extras = Collections.unmodifiableMap(otherArgs.stream()
                .map(p -> p.split("="))
                .collect(Collectors.toMap(p -> p[0], p -> p[1])));

        this.context = com.github.andriytyranovets.webshop.model.ContextImpl.builder()
                .amount(amount)
                .price(price)
                .type(type)
                .extras(extras)
                .build();
    }

    private void validateExtras(Set<String> extras) {
        extras.stream()
                .filter(p -> !ExtraParams.matchesAny(p))
                .findAny()
                .ifPresent(u -> showUsageAndFail(InvalidParams.UnknownArgument, u));
    }

    private void showUsageAndFail(InvalidParams error) {
        showUsageAndFail(error, null);
    }

    private void showUsageAndFail(InvalidParams error, String invalidArg) {
        var msg = error == InvalidParams.UnknownArgument
                ? String.format(error.getError(), invalidArg == null ? "unknown" : invalidArg)
                : error.getError();

        System.out.println(msg);
        System.out.println("Usage:");
        System.out.println("java -jar webshop.jar <amount> <price> <type> [<extra>...]");

        throw new RuntimeException(msg);
    }
}
