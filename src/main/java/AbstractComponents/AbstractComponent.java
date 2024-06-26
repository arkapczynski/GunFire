package AbstractComponents;

import PageObjects.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class AbstractComponent {

    WebDriver driver;

    public AbstractComponent(WebDriver driver) {
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[@href='https://gunfire.com/pl/']")
    WebElement bannerButton;
    @FindBy(linkText ="Repliki airsoft" )
    WebElement replicasButton;
    @FindBy(linkText ="Wyposażenie taktyczne")
    WebElement tacticalGearButton;

    @FindBy(linkText ="Części i akcesoria" )
    WebElement partsAndAccessoriesButton;
    @FindBy(linkText ="Usługi serwisowe" )
    WebElement serviceButton;
    @FindBy(linkText ="Marki" )
    WebElement producersButton;
    @FindBy(linkText ="Nowości" )
    WebElement newsButton;
    @FindBy(linkText ="Outlet" )
    WebElement outletButton;
    @FindBy(linkText ="Promocje" )
    WebElement promotionsButton;
    @FindBy(id = "menu_basket")
    WebElement cartButton;
    @FindBy(xpath = "//i[@class='icon-compare-gf']")
    WebElement compareButton;
    @FindBy(xpath = "//span[@class='open_currency_currency']")
    WebElement currencyButton;
    @FindBy(name ="curr")
    WebElement showCurrenciesButton;
    @FindBy(xpath = "//ul[@class='bg_alter']/li[@class='buttons']/button[@type='submit']")
    WebElement applyCurrencyButton;
    @FindBy(xpath = "//span[@class='open_language']")
    WebElement languagesButton;
    @FindBy(id = "s_setting_0")
    WebElement sortButton;
    @FindBy(xpath = "//a[@href='#price-d']")
    WebElement priceDownButton;
    @FindBy(xpath = "//a[@href='#price-a']")
    WebElement priceUpButton;
    @FindBy(xpath = "//a[@href='#date-d']")
    WebElement dateDownButton;
    @FindBy(xpath = "//a[@href='#date-a']")
    WebElement dateUpButton;
    @FindBy(id = "menu_search_text")
    WebElement searchBar;

    @FindBy(xpath = "//a[@href='#acceptAll']")
    WebElement cookiesAbroad;

//    @FindBy(xpath = "//div[@class='prefixbox-autocomplete-product-container  ']")
//    List <WebElement> suggestedSearchItems;
//    @FindBy(xpath = "//*[contains(@class, 'prefixbox-group-container')]")
//    List <WebElement> suggestedSearchItems;
    @FindBy(xpath = "//div[@class='prefixbox-autocomplete-body']")
    List <WebElement> suggestedSearchItems;

    @FindBy(xpath = "//div[contains(@class,'product_wrapper')]/h3")
    List <WebElement> first5;

    public void goToMainPage(){
        bannerButton.click();
    }

    public ReplicasPage goToReplicas(){
       replicasButton.click();
        ReplicasPage replicas= new ReplicasPage(driver);
        return replicas;
    }

    public PartsAndAccessoriesPage goToPartsAndAccessories(){
        partsAndAccessoriesButton.click();
        PartsAndAccessoriesPage partsAndAccessoriesPage= new PartsAndAccessoriesPage(driver);
        return partsAndAccessoriesPage;
    }

    public TacticalGearPage goToTacticalGear(){
        tacticalGearButton.click();
        TacticalGearPage tacticalGear= new TacticalGearPage(driver);
        return tacticalGear;
    }

    public ServicePage goToService(){
        serviceButton.click();
        ServicePage service=new ServicePage(driver);
        return service;
    }

    public ProducersPage goToProducers(){
        producersButton.click();
        ProducersPage manufacturersPage= new ProducersPage(driver);
        return manufacturersPage;
    }

    public NewsPage goToNews(){
        newsButton.click();
        NewsPage newsPage= new NewsPage(driver);
        return newsPage;
    }

    public OutletPage goToOutlet(){
       outletButton.click();
        OutletPage outletPage= new OutletPage(driver);
        return outletPage;
    }
    public PromotionPage goToPromotions(){
       promotionsButton.click();
        PromotionPage promotionPage= new PromotionPage(driver);
        return promotionPage;
    }

    public CartPage goToCart(){
        cartButton.click();
        CartPage cartPage= new CartPage(driver);
        return cartPage;
    }

    public ComparePage goToComparePage(){
        compareButton.click();
        ComparePage comparePage=new ComparePage(driver);
        return comparePage;
    }


    public void changeCurrency(String currency){
        // do wyboru PLN / EUR/ CZK / HUF / GBP
        currencyButton.click();
        showCurrenciesButton.click();
        driver.findElement(By.xpath("//option[@value='"+currency+"']")).click();
        applyCurrencyButton.click();
    }


    public void changeLanguage(String language) {
        languagesButton.click();
        driver.findElement(By.xpath("//div[@class=\"open_language__options_wrapper\"]//a[@data-lang-id='"+language+"']")).click();
//  available: pl/cz/es/en/fr/nl
        if(language.equals("nl") || language.equals("en") || language.equals("cz") ||language.equals("fr")){
           cookiesAbroad.click();
        }



        // To da się streamem jakoś rozwiązać, ale trzeba pogłówkować.
//        List<WebElement> languages= driver.findElements(By.xpath("//span[@class=\"open_language_item\"]"));
//        for (int i=1; i<languages.size(); i++) {
//                driver.findElements(By.xpath("(//span[@class=\"open_language_item\"])["+i+"]/span[@data-lang-id=\""+language+"\"]"))
//        }
//        languages.stream().map(s->s.findElement(By.cssSelector("span[@data-lang-id='"+language+"']")))
        // languages.stream().filter(s->
       //         if(s.findElement())
        //   data-lang-id="es"

    }


    public void waitforElementVisibility (WebElement elementToWait) {
        WebDriverWait waiting = new WebDriverWait(driver, Duration.ofSeconds(15));
        waiting.until(ExpectedConditions.visibilityOf(elementToWait));
    }


    public void sortBy(String sortType) {

       sortButton.click();
        if(sortType.equals("pricedown")) {
            priceDownButton.click();
        }
        else if(sortType.equals("priceup")){
           priceUpButton.click();
        }
        else if(sortType.equals("datedown")){
           dateDownButton.click();
        }
        else if(sortType.equals("dateup")){
            dateUpButton.click();
        }
    }

    public void applyChosenFilters(){

    }

    public void printFirst5() {
        for (int i = 0; i < 5; i++) {
            String productName = first5.get(i).getText();
            System.out.println(productName);
        }
    }

    public ArrayList<String> getFirst5(){
        ArrayList<String> productNames = new <String>ArrayList();
        for (int i = 0; i < 5; i++) {
            productNames.add(first5.get(i).getText());
        }
        //System.out.println(productNames[i]);
        return productNames;
    }

        public void printTopExpensive() {
        sortBy("pricedown");
        printFirst5();
    }

    public void printCheapest(){
        sortBy("priceup");
        printFirst5();
    }


    public void searchBarTyping(String searchPhrase){

        searchBar.sendKeys(searchPhrase);
    }
    public ProductPage searchExactProduct(String searchPhrase){
        searchBarTyping(searchPhrase);
        suggestedSearchItems.stream().filter(s->s.getText().contains(searchPhrase)).findFirst().orElse(null).click();
        ProductPage productPage= new ProductPage(driver);
        return productPage;
    }

    public void filterManufacturer(String[] manufacturers) { //da się loopa zrobić żeby można było podać kilku manufactorów
        driver.findElement(By.xpath("//a[@data-id=\"filter_producer\"]/span[@class=\"--show\"]")).click();
        List<WebElement> producersList = driver.findElements(By.xpath("//ul[@id=\"filter_producer_content\"]/li[contains(@class,\"filters__item\")]/div/label"));
        for (String manufacturer : manufacturers) {
            producersList.stream().filter(s -> s.getText().contains(manufacturer)).forEach(s -> s.click());
        }
    }

    public void filterElectricWeaponType(String[] types) {
        List<WebElement> weaponType = driver.findElements(By.xpath("//ul[@id=\"filter_traits6471_content\"]/li/div/label/span[@class=\"--name\"]"));
        for (String type : types) {

            weaponType.stream().filter(s -> s.getText().equalsIgnoreCase(type)).forEach(s -> s.click());
        }
    }

    public void applyFilters() {
        driver.findElement(By.xpath("//button[@class=\"__buttons-submit\"]")).click();
    }


    public void applyChosenFilters(String[] manufacturers, String[] types) {
        //zrobimy później więcej filtrów i będzie się matchowało na podstawie DataProvidera
        filterManufacturer(manufacturers);
        filterElectricWeaponType(types);
        applyFilters();
    }


}
