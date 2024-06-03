package StepDefinitions;

import PageObjects.*;
import TestComponents.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.ArrayList;

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
        promotionPage = mainPage.goToPromotionsBottomButton();
    }

    @When("^Customer choose weapon types (.+) and manufacturers (.+) then confirm filters$")
    public void customer_choose_weapon_types_and_manufacturers_then_confirm_filters(String weaponTypes, String manufacturers) {
        String [] weaponTypesArray= weaponTypes.split(",");
        String [] manufacturersArray= manufacturers.split(",");

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
    public void one_of_most_expenive_is(String weapon){
      ArrayList<String> mostExpensives= promotionPage.getFirst5();
      Assert.assertTrue(mostExpensives.stream().anyMatch(s->s.contains(weapon)));
     }




     @When("^Customer changes language on (.+)$")
    public void customer_changes_language(String language){
         mainPage.changeLanguage(language);
     }

     @Given("^Customer changes currency on (.+)$")
    public void customer_changes_currency(String currency){
         mainPage.changeCurrency(currency);
    }

    @When("^Customer search for product (.+) and click it.$")
    public void customer_search_for_product(String searchProduct){
       productPage= mainPage.searchExactProduct(searchProduct);
    }

    @When("Customer click add to comparison button.")
    public void customer_click_add_to_comparison_button()
    {productPage.addProductForComparison();}

    @Then("^Price is in currency (.+)$")
    public void price_is_in_currency(String currency){
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

        else if (currency.equals("HUF")) {
            expectedCurrencyValue= "Ft";    //też są spacje
            String currencyInPriceArray[]= price.split(" ");
            currencyInPrice= currencyInPriceArray[currencyInPriceArray.length];
        }

        Assert.assertEquals(expectedCurrencyValue, currencyInPrice);


    }

    @Then("^Add to cart button is in (.+)$")
    public void add_to_cart_is_in(String language){

        String addToCartText= productPage.addToCartButtonText();
        String expectedAddToButtonTetxt= null;

        if (language.equals("pl")){
            expectedAddToButtonTetxt ="Dodaj produkt do koszyka";
        }

        else if (language.equals("en")){
            expectedAddToButtonTetxt="Add to basket";
        }

        else if (language.equals("fr")){
            expectedAddToButtonTetxt="Ajouter au panier";
        }

        else if (language.equals("es")){
            expectedAddToButtonTetxt="Añadir al carrito";
        }

        else if (language.equals("nl")){
            expectedAddToButtonTetxt="Toevoegen aan winkelmandje";
        }

        else if (language.equals("cz")){
            expectedAddToButtonTetxt="Přidat do košíku";
        }

        Assert.assertEquals(addToCartText, expectedAddToButtonTetxt);

    }

    @When("Customer go to Compare Page")
    public ComparePage customer_go_to_compare_page() {
        return comparePage= mainPage.goToComparePage();
    }

    @When("Click on differences button")
    public void click_on_differences_button(){
        comparePage.clickOnDifferencesButton();
    }

    @Then("^(.+) are unique.$")
    public void product_codes_unique(String comparisonParameter){
        ArrayList<String> elo= comparePage.compareProductsBy(comparisonParameter);
    }


   @Given ("Customer clicks manufacturer button.")
   public void customer_clicks_manufacturer_button(){
       producersPage= mainPage.goToProducers();
   }

   @Given("^Customer choose manufacturer (.+).$")
   public void customer_choose_manufacturer(String producer){
        producersPage.chooseProducer(producer);
   }

    @After
    public void quitDriver(){

        driver.quit();
    }


}
