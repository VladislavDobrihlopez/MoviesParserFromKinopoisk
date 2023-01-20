package com.voitov.movieskinopoisk;

import com.google.gson.annotations.SerializedName;

public class Budget {
    @SerializedName("value")
    private final long amount;
    @SerializedName("currency")
    private final String currency;

    public Budget(long amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public long getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }
}
