package StepDefinitions;

import PageObjects.*;
import TestComponents.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;


public class StepDefinitions extends BaseTest {
    PromotionPage promotionPage;
    MainPage mainPage;
    ProductPage productPage;
    ProducersPage producersPage;
    ComparePage comparePage;

    @Given ("Customer lands on Main Page")
    public void customer_lands_on_main_page(){
        mainPage=launchGunfirePage();
    }

    @Given("Customer clicks Promotion Button")
    public void customer_clicks_PromotionButton() {
        logger.debug("Click promotion button");
        promotionPage = mainPage.goToPromotionsBottomButton();
    }

    @When("^Customer choose weapon types (.+) and manufacturers (.+) then confirm filters$")
    public void customer_choose_weapon_types_and_manufacturers_then_confirm_filters(String weaponTypes, String manufacturers) {
        logger.debug("Choose weapon types");
        String [] weaponTypesArray= weaponTypes.split(",");
        logger.debug("Choose manufacturers");
        String [] manufacturersArray= manufacturers.split(",");
        logger.debug("Apply filters");
        mainPage.applyChosenFilters(manufacturersArray, weaponTypesArray, null);
    }

    @When("^Customer choose category (.+) then confirm filters$")
    public void customer_choose_category_then_confirms_filters(String category) {
        String [] categories = {category};
        logger.debug("Apply filters");
        mainPage.applyChosenFilters(null, null, categories);
    }

    @When("^Customer choose categories (.+) and manufacturers (.+) then confirm filters$")
    public void customer_choose_categories_and_manufacturers_then_confirm_filters(String categories, String manufacturers) {
        logger.debug("Choose categories");
        String [] categoriesArray = categories.split(",");
        logger.debug("Choose manufacturers");
        String [] manufacturersArray = manufacturers.split(",");
        logger.debug("Apply filters");
        mainPage.applyChosenFilters(manufacturersArray, null, categoriesArray);
    }

    @Then("5 most expensive products print in output.")
    public void five_most_expensive_products_print_in_output() {
        promotionPage.printTopExpensive(5);
    }

    @Then("5 cheapest products print in output.")
    public void five_cheapest_products_print_in_output(){
        producersPage.printCheapest(5);
    }

    @Then ("One of most expensive products is {string}.")
    public void one_of_most_expensive_is(String weapon) {
        ArrayList<String> mostExpensives = promotionPage.getFirstElementsNames(5);
        try {
            Assert.assertTrue(mostExpensives.stream().anyMatch(s -> s.contains(weapon)));
            logger.debug(weapon + " is one of the most expensive products");
        }
        catch (Exception e){
            logger.error(weapon +" in not of one of the most expensive products");
        }
    }

    @Then ("One of the cheapest products is {string}.")
    public void one_of_the_cheapest_products_is(String product) {
        ArrayList<String> theCheapest = producersPage.getFirstElementsNames(5);
        try {
            Assert.assertTrue(theCheapest.stream().anyMatch(s -> s.contains(product)));
            logger.debug(product + " is one of the cheapest products");
        }
        catch (Exception e){
            logger.error(product +" in not of one of the cheapest products");
        }
    }




     @When("^Customer changes language on (.+)$")
    public void customer_changes_language(String language){
        logger.debug("Change language to " + language);
         mainPage.changeLanguage(language);
     }

     @Given("^Customer changes currency on (.+)$")
    public void customer_changes_currency(String currency){
        logger.debug("Change currency to " + currency);
        mainPage.changeCurrency(currency);
    }

    @When("^Customer search for product (.+) and click it.$")
    public void customer_search_for_product(String searchProduct){
        logger.debug("Search for " + searchProduct);
       productPage= mainPage.searchExactProduct(searchProduct);
    }

    @When("Customer click add to comparison button.")
    public void customer_click_add_to_comparison_button() {
        logger.debug("Add product to comparison");
        productPage.addProductForComparison();
    }

    @Then("^Price is in currency (.+)$")
    public void price_is_in_currency(String currency){
        String price= productPage.getPrice();
        String currencyInPrice = null;
        String expectedCurrencyValue = null;

        switch (currency) {
            case "PLN" -> {
                expectedCurrencyValue = "PLN";
                currencyInPrice = price.split(" ")[1];
            }
            case "EUR" -> {
                expectedCurrencyValue = "€";
                currencyInPrice = price.split(" ")[1];
            }
            case "GBP" -> {     // funt stoi przed ceną i nie ma spacji...
                expectedCurrencyValue = "£";
                currencyInPrice = price.split("")[0];
            }
            case "CZK" -> {
                expectedCurrencyValue = "Kč";      // w cenie jest kilka spacji...

                String[] currencyInPriceArray = price.split(" ");
                currencyInPrice = currencyInPriceArray[currencyInPriceArray.length - 1];
            }
            case "HUF" -> {
                expectedCurrencyValue = "Ft";    //też są spacje

                String[] currencyInPriceArray = price.split(" ");
                currencyInPrice = currencyInPriceArray[currencyInPriceArray.length - 1];
            }
        }

        logger.debug("Expected currency value is " + expectedCurrencyValue + ", currency in price is " + currencyInPrice);
        Assert.assertEquals(expectedCurrencyValue, currencyInPrice);
    }

    @Then("^Add to cart button is in (.+)$")
    public void add_to_cart_is_in(String language){
        String addToCartText= productPage.addToCartButtonText();
        String expectedAddToButtonText = switch (language) {
            case "pl" -> "Dodaj produkt do koszyka";
            case "en" -> "Add to basket";
            case "fr" -> "Ajouter au panier";
            case "es" -> "Añadir al carrito";
            case "nl" -> "Toevoegen aan winkelmandje";
            case "cz" -> "Přidat do košíku";
            default -> null;
        };
        logger.debug("Add to cart text is " + addToCartText + " and expected is " + expectedAddToButtonText);
        Assert.assertEquals(addToCartText, expectedAddToButtonText);

    }

    @Then("^Price is recalculated correctly from (.+).$")
    public void price_is_recalulated_correctly_from(String currency){ // trzeba przerobic step w Scenarios
        double priceInCurrencyFormatted = productPage.priceValue(productPage.getPrice());
        logger.debug("Formatted price in chosen currency is " + priceInCurrencyFormatted);

        double exchangeRatio = productPage.getPlnExchangeRatio();
        logger.debug("Exchange ratio is "+ exchangeRatio);

        productPage.changeCurrency("PLN");
        double priceInPln= productPage.priceValue(productPage.getPrice());
        logger.debug("Price in PLN is "+priceInPln);

        double priceRecalculatedToPLN = priceInCurrencyFormatted*exchangeRatio;
        logger.debug("Price recalculated in PLN is "+priceRecalculatedToPLN);

        Assert.assertTrue((Math.abs(priceInPln-priceRecalculatedToPLN)) < 0.05, "Price is recalculated incorrectly");
    }
    @When("Customer go to Compare Page")
    public ComparePage customer_go_to_compare_page() {
        logger.debug("Go to Compare Page");
        return comparePage= mainPage.goToComparePage();
    }

    @When("Click on differences button")
    public void click_on_differences_button(){
        logger.debug("Click on differences button");
        comparePage.clickOnDifferencesButton();
    }

    @Then("^(.+) are unique.$")
    public void product_codes_unique(String comparisonParameter) throws IOException {
        comparePage.takeScreenshot();
        Set<String> setOfUniqueCodes = new HashSet<>();
        for(int i = 1; i <4; i++){
            setOfUniqueCodes.add(comparePage.comparisonParametersOfProduct(i).get(comparisonParameter));
        }
        logger.debug("Codes of products are: "+ setOfUniqueCodes);
        Assert.assertEquals(setOfUniqueCodes.size(), 3);
    }


   @Given ("Customer clicks manufacturer button.")
   public void customer_clicks_manufacturer_button(){
        logger.debug("Go to producers");
        producersPage= mainPage.goToProducers();
   }

   @Given("^Customer choose manufacturer (.+).$")
   public void customer_choose_manufacturer(String producer){
        logger.debug("Choose manufacturer " + producer);
        producersPage.chooseProducer(producer);
   }

    @After
    public void quitDriver() throws InterruptedException {
        logger.debug("Quit driver");
        closeDriver();
    }
}
