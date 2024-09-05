package com.haratres.haratres.Utils;

public class PriceUtil {

    private static final double USD_TO_TRY = 34.04;
    private static final double EUR_TO_TRY = 37.61;

    public static double convertCurrencyToTRY(double price, String currency){
        if ("USD".equalsIgnoreCase(currency)){
            return price * USD_TO_TRY;
        }
        else if ("EUR".equalsIgnoreCase(currency)){
            return price * EUR_TO_TRY;
        }
        else if ("TRY".equalsIgnoreCase(currency)){
            return price;
        }
        else {
            throw new IllegalArgumentException("Desteklenmeyen para birimi : " + currency);
        }
    }
}
