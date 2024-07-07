package Testy;

import PageObjects.*;
import TestComponents.BaseTest;
import org.testng.annotations.Test;

public class TestBase extends BaseTest {


    @Test
    public void topExpensiveAtPromotion() {
        String[] weaponTypes = new String[]{"Karabiny/Karabinki", "Karabiny wyborowe"};
        String[] manufacturers = new String[]{"ARES", "LCT", "CYMA", "Bolle"};
        PromotionPage promotionPage = mainPage.goToPromotionsBottomButton();
        promotionPage.applyChosenFilters(manufacturers, weaponTypes, null);
        promotionPage.printTopExpensive(5);
    }

    @Test
    public void checkPriceOfCM028InESInCZK() {
        String language = "es";
        String currency = "CZK";
        String searchProduct = "CM028";
        mainPage.changeLanguage(language);
        mainPage.changeCurrency(currency);
        mainPage.searchExactProduct(searchProduct);
    }

    @Test
    public void compareReplicas() {
        String firstReplica = "LCK-12";
        String secondReplica = "CM032";

        ProductPage productPage = mainPage.searchExactProduct(firstReplica);
        productPage.addProductForComparison();

        mainPage.searchExactProduct(secondReplica);
        productPage.addProductForComparison();

        ComparePage comparePage = mainPage.goToComparePage();
        comparePage.clickOnDifferencesButton();
    }

    @Test
    public void topExpensiveCyma() {
        String[] weaponTypes = new String[]{"Karabiny/Karabinki", "Karabiny wyborowe", "Karabiny maszynowe"};
        String[] manufacturers = new String[]{"CYMA"};
        ReplicasPage replicasPage = mainPage.goToReplicas();
        replicasPage.applyChosenFilters(manufacturers, weaponTypes);
        replicasPage.printTopExpensive(5);
    }

    @Test
    public void topExpensiveElectric() {
        String[] weaponTypes = new String[]{"Karabiny/Karabinki", "Karabiny wyborowe", "Karabiny maszynowe", "Pistolety Maszynowe", "Pistolety"};
        ReplicasPage replicasPage = mainPage.goToReplicas();
        replicasPage.filterElectricWeaponType(weaponTypes);
        replicasPage.applyFilters();
        replicasPage.printTopExpensive(5);
    }

    @Test
    public void printingCheapestProducersProducts() {
        String producer = "CYMA";
        ProducersPage producersPage = mainPage.goToProducers();
        producersPage.cheapestProductsOfProducer(producer);
    }

    @Test
    public void checkIfReplicaIsCheaperThan() {
        ProductPage productPage = mainPage.searchExactProduct("LCK-12");
        double actualPrice = productPage.parseProductPriceFromString();
        if (actualPrice > 2000) {
            System.out.println("Waaaait, wait....");
        } else {
            System.out.println("NOW! ");
        }
    }
}