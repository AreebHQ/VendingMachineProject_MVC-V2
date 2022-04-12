package com.Dao;

import com.Dto.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
@Component
public class Change {

    static int quarter;
    static int dime;
    static int nickel;
    static int penny;

    @Autowired
    public Change(int pennies)
    {
        int numQuarters = Coin.QUARTER.getCoinChange(pennies);

        pennies -= numQuarters * Coin.QUARTER.coinValue();
        int numDimes = Coin.DIME.getCoinChange(pennies);

        pennies -= numDimes * Coin.DIME.coinValue();
        int numNickels = Coin.NICKEL.getCoinChange(pennies);

        pennies -= numNickels*Coin.NICKEL.coinValue();

        quarter = numQuarters;
        dime = numDimes;
        nickel = numNickels;
        penny = pennies;
    }

    public static void getChange(int pennies)
    {
        int numQuarters = Coin.QUARTER.getCoinChange(pennies);
        pennies -= numQuarters * Coin.QUARTER.coinValue();

        int numDimes = Coin.DIME.getCoinChange(pennies);
        pennies -= numDimes * Coin.DIME.coinValue();

        int numNickels = Coin.NICKEL.getCoinChange(pennies);
        pennies -= numNickels*Coin.NICKEL.coinValue();

    }

     public static String getChangeInCoins()
    {
        String changeByCoins="";

        if(quarter>0) { changeByCoins+= "Quarters:"  +quarter; }

        if(dime>0) { changeByCoins+= " Dimes:"  +dime; }

        if(nickel>0) { changeByCoins+= " Nickels:"  +nickel; }

        if(penny>0)  { changeByCoins+= " Pennies:"  +penny; }

        if(changeByCoins.isEmpty()) { changeByCoins = "0 Coins"; }

        return changeByCoins;
    }


    public static String getChangeInDollars()
    {
        String t = ""+(quarter*Coin.QUARTER.coinValue()+dime*Coin.DIME.coinValue()+nickel*Coin.NICKEL.coinValue()+penny)/100.0;
        BigDecimal totalChange = new BigDecimal(t);
        totalChange.setScale(2,RoundingMode.HALF_UP);
        return totalChange.toString();
    }

}
