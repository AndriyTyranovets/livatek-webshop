package com.github.andriytyranovets.webshop.argprocessor;

public enum InvalidParams {
    NotEnoughArguments("Not enough arguments. Required at least 3 arguments."),
    InvalidAmount("Invalid <amount>. <amount> must be a positive integer."),
    InvalidPrice("Invalid <price>. <price> must be a positive number."),
    InvalidType("Invalid <type>. <type> can be either \"Online\" or \"Book\""),
    UnknownArgument("Unknown argument <%s>. Available arguments: " + ExtraParams.getAvailableExtras());

    private String error;
    InvalidParams(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
