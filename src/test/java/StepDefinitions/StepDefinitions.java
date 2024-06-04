package StepDefinitions;

import PageObjects.*;
import TestComponents.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Set;

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
        mainPage.applyChosenFilters(manufacturersArray, weaponTypesArray);
    }

    @Then("5 most expensive products print in output.")
    public void five_most_expensive_products_print_in_output() {
        promotionPage.printTopExpensive();
    }

    @Then("5 cheapest products print in output.")
    public void five_cheapest_products_print_in_output(){
        producersPage.printCheapest();
    }

    @Then ("One of most expensive products is {string}.")
    public void one_of_most_expensive_is(String weapon) {
        ArrayList<String> mostExpensives = promotionPage.getFirst5();
        try {
            Assert.assertTrue(mostExpensives.stream().anyMatch(s -> s.contains(weapon)));
            logger.debug(weapon + " is one of the most expensive products");
        }
        catch (Exception e){
            logger.error(weapon +" in not of one of the most expensive products");
        }
    }




     @When("^Customer changes language on (.+)$")
    public void customer_changes_language(String language){
        logger.debug("Change language to" + language);
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
    public void product_codes_unique(String comparisonParameter){
        ArrayList<String> elo= comparePage.compareProductsBy(comparisonParameter);
        Set<String> secik = null;
        for(String e : elo){
            secik.add(e);
        }
        assert elo.size() == secik.size();
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
    public void quitDriver(){
        logger.debug("Quit driver");
        driver.quit();
    }


}
