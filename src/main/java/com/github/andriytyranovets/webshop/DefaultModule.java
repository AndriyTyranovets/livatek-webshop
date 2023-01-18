package com.github.andriytyranovets.webshop;

import com.github.andriytyranovets.webshop.config.CurrencyConfig;
import com.github.andriytyranovets.webshop.config.VatConfig;
import com.github.andriytyranovets.webshop.freightfare.DefaultFreightFareCalculator;
import com.github.andriytyranovets.webshop.freightfare.FreightFareCalculator;
import com.github.andriytyranovets.webshop.model.Context;
import com.github.andriytyranovets.webshop.priceadjustment.DefaultPriceAdjustmentService;
import com.github.andriytyranovets.webshop.priceadjustment.PriceAdjustmentService;
import com.github.andriytyranovets.webshop.pricecalculator.DefaultPriceCalculator;
import com.github.andriytyranovets.webshop.pricecalculator.PriceCalculator;

public final class DefaultModule {
    private Context context;

    public DefaultModule(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public FreightFareCalculator getDefaultFreightFareCalculator() {
        return new DefaultFreightFareCalculator();
    }

    public PriceCalculator getDefaultPriceCalculator() {
        return new DefaultPriceCalculator(getDefaultFreightFareCalculator());
    }

    public PriceAdjustmentService getDefaultPriceAdjustmentService() {
        return new DefaultPriceAdjustmentService(getContext(), getVatConfig(), getCurrencyConfig());
    }

    //Singletons
    private VatConfig vatConfig;
    private CurrencyConfig currencyConfig;

    public VatConfig getVatConfig() {
        if(vatConfig == null) {
            vatConfig = new VatConfig();
        }
        return vatConfig;
    }

    public CurrencyConfig getCurrencyConfig() {
        if (currencyConfig == null) {
            currencyConfig = new CurrencyConfig();
        }
        return currencyConfig;
    }
}
