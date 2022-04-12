package com.Dto;

public enum Coin
{
    PENNY(1), NICKEL(5), DIME(10), QUARTER(25);

    private final int coinValue;

    Coin(int coinValue)
    {
        this.coinValue = coinValue;
    }

    public int coinValue()
    {
        return coinValue;
    }

    public int getCoinChange(int numPennies)
    {
        return numPennies / coinValue;
    }
}


