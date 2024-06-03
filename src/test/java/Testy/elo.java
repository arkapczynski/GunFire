package Testy;

import PageObjects.ProductPage;
import TestComponents.BaseTest;
import org.testng.annotations.Test;

public class elo extends BaseTest {

    @Test
    public void dadas (){
        String [] productNames= new String[5];
        for (int i = 0; i < 5; i++) {
            productNames[i] = "elo"+i;
            System.out.println(productNames[i]);
    }

    }

    @Test
    public void dsdsad() throws InterruptedException {

        String currency= "CZK";
String searchProduct= "CM028";
        mainPage.changeCurrency(currency);
        ProductPage productPage= mainPage.searchExactProduct(searchProduct);

        String price= productPage.getPrice();
        String currencyInPrice = null;
        String expectedCurrencyValue = null;

        if (currency.equals("PLN")){
            expectedCurrencyValue= "PLN";
            currencyInPrice= price.split(" ")[1];
        }
        else if(currency.equals("EUR")){
            expectedCurrencyValue= "€";
            currencyInPrice= price.split(" ")[1];
        }

        else if (currency.equals("GBP")) {    // funt stoi przed ceną i nie ma spacji...
            expectedCurrencyValue= "£";
            currencyInPrice= price.split("")[0];
        }

        else if (currency.equals("CZK")) {
            expectedCurrencyValue= "Kč";      // w cenie jest kilka spacji...
            String currencyInPriceArray[]= price.split(" ");
            currencyInPrice= currencyInPriceArray[currencyInPriceArray.length-1];
        }

        else if (currency.equals("HUF")) {
            expectedCurrencyValue= "Ft";    //też są spacje
            String currencyInPriceArray[]= price.split(" ");
            currencyInPrice= currencyInPriceArray[currencyInPriceArray.length-1];
        }

        System.out.println(expectedCurrencyValue);
        System.out.println(currencyInPrice);

    }
}
