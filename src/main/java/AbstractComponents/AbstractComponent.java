package AbstractComponents;

import PageObjects.*;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AbstractComponent {
    public static final Logger logger = LogManager.getLogger(AbstractComponent.class);

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
    @FindBy(xpath = "//button[@class='__buttons-submit']")
    WebElement applyFilters;
    @FindBy(xpath = "//div[@id='filter_node1_expand']//div[@class='f-group --small --checkbox mb-0']//span[@class=\"--name\"]")
    List <WebElement> categoriesList;
    @FindBy(xpath = "//ul[@id='filter_producer_content']/li[contains(@class,'filters__item')]/div/label/span[@class='--name']")
    List <WebElement> producersList;
    @FindBy(xpath = "//ul[@id='filter_traits6471_content']/li/div/label/span[@class='--name']")
    List<WebElement> weaponTypesList;
    @FindBy(xpath = "//div[@class='prefixbox-autocomplete-body']")
    List <WebElement> suggestedSearchItems;

    //ul[@role="listbox"]
    @FindBy(xpath = "//div[contains(@class,'product_wrapper')]/h3")
    List <WebElement> first5;
    @FindBy(xpath = "//a[@data-id='filter_producer']/span[@class='--show']")
    WebElement extendProducersListButton;
    @FindBy(xpath = "//select[@class=\"form-control\"]/option[1]")
    WebElement plnCurrencyButton;

    public void goToMainPage(){
        bannerButton.click();
    }

    public ReplicasPage goToReplicas(){
       replicasButton.click();
        return new ReplicasPage(driver);
    }

    public PartsAndAccessoriesPage goToPartsAndAccessories(){
        partsAndAccessoriesButton.click();
        return new PartsAndAccessoriesPage(driver);
    }

    public TacticalGearPage goToTacticalGear(){
        tacticalGearButton.click();
        return new TacticalGearPage(driver);
    }

    public ServicePage goToService(){
        serviceButton.click();
        return new ServicePage(driver);
    }

    public ProducersPage goToProducers(){
        producersButton.click();
        return new ProducersPage(driver);
    }

    public NewsPage goToNews(){
        newsButton.click();
        return new NewsPage(driver);
    }

    public OutletPage goToOutlet(){
       outletButton.click();
        return new OutletPage(driver);
    }
    public PromotionPage goToPromotions(){
       promotionsButton.click();
        return new PromotionPage(driver);
    }

    public CartPage goToCart(){
        cartButton.click();
        return new CartPage(driver);
    }

    public ComparePage goToComparePage(){
        compareButton.click();
        return new ComparePage(driver);
    }


    public void changeCurrency(String currency) {
        // PLN / EUR/ CZK / HUF / GBP
        currencyButton.click();
        showCurrenciesButton.click();
        WebElement chosenCurrencyElement = driver.findElement(By.xpath("//option[@value='"+currency+"']"));
        chosenCurrencyElement.click();
        applyCurrencyButton.click();
    }

    public double getPlnExchangeRatio() {
        // EUR/ CZK / HUF / GBP
        languagesButton.click();
        currencyButton.click();
        showCurrenciesButton.click();
        return priceValue(plnCurrencyButton.getText());
    }


    public void changeLanguage(String language) {
        //  available: pl/cz/es/en/fr/nl
        languagesButton.click();
        driver.findElement(By.xpath("//div[@class=\"open_language__options_wrapper\"]//a[@data-lang-id='"+language+"']")).click();
        if(language.equals("nl") || language.equals("en") || language.equals("cz") ||language.equals("fr")) {
            cookiesAbroad.click();
        }
    }

    public double priceValue(String price){
        if (price.contains(",")){
            price = price.replace(",",".");
        }

        // Define the patterns
        String pattern = "\\d+\\.\\d+";
        String pattern2 = "\\d+ \\d+";

        // Create a Pattern objects
        Pattern regex = Pattern.compile(pattern);
        Pattern regex2 = Pattern.compile(pattern2);

        // Create Matcher objects
        Matcher matcher = regex.matcher(price);
        Matcher matcher2 = regex2.matcher(price);

        String value = null;

        // Find and extract the numeric value
        if (matcher.find()) {
            value = matcher.group();
        } else if (matcher2.find()) {
            String value1 = matcher2.group();  // Retrieve the matched substring
            value = value1.replace(" ", "");
        } else {
            logger.debug("Numeric value not found in the input");
        }
        return Double.parseDouble(value);
    }

    public void waitforElementVisibility (WebElement elementToWait) {
        WebDriverWait waiting = new WebDriverWait(driver, Duration.ofSeconds(15));
        waiting.until(ExpectedConditions.visibilityOf(elementToWait));
    }

    public void waitforElementClickable (WebElement elementToWait) {
        WebDriverWait waiting = new WebDriverWait(driver, Duration.ofSeconds(15));
        waiting.until(ExpectedConditions.elementToBeClickable(elementToWait));
    }


    public void sortBy(String sortType) {
       sortButton.click();
        switch (sortType) {
            case "pricedown" -> priceDownButton.click();
            case "priceup" -> priceUpButton.click();
            case "datedown" -> dateDownButton.click();
            case "dateup" -> dateUpButton.click();
        }
    }

    public List<WebElement> getFirstElements(int numberOfElements) {
        ArrayList <WebElement> listOfElements= new ArrayList <>();

        for (int i = 0; i < numberOfElements; i++) {
            WebElement productName = first5.get(i);
            listOfElements.add(productName);
        }
        return listOfElements;
    }

    public ArrayList<String> getFirstElementsNames(int numberOfElements){
        ArrayList<String> productNames = new ArrayList <>();
        for (WebElement element : getFirstElements(numberOfElements)) {
            productNames.add(element.getText());
        }
        return productNames;
    }

    public void printFirstElements(int numberOfElements) {
        List <String> elementsList= getFirstElementsNames(numberOfElements);
        for (String element : elementsList) {
            logger.debug(element);
        }
    }

        public void printTopExpensive(int numberOfElements) {
        sortBy("pricedown");
        printFirstElements(numberOfElements);
    }

    public void printCheapest(int numberOfElements){
        sortBy("priceup");
        printFirstElements(numberOfElements);
    }

    public void searchBarTyping(String searchPhrase){
        searchBar.sendKeys(searchPhrase);
    }
    public ProductPage searchExactProduct(String searchPhrase){
        searchBarTyping(searchPhrase);
        suggestedSearchItems.stream().filter(s->s.getText().contains(searchPhrase)).findFirst().orElse(null).click();
        return new ProductPage(driver);
    }

    public void filterManufacturer(String[] manufacturers) {
        extendProducersListButton.click();
        logger.debug("Manufacturers " + Arrays.stream(manufacturers).toList());

        for (String manufacturer : manufacturers) {
            logger.debug("Manufacturer: " + manufacturer);
            List <WebElement> listOfProducers = producersList.stream().filter(s -> s.getText().contains(manufacturer)).toList();
            WebElement producerToClick = listOfProducers.stream().findFirst().orElse(null);
            scrollToElementWithOffset(driver, producerToClick);
            // Click on the producer
            waitforElementClickable(producerToClick);
            producerToClick.click();
        }
    }

    public void filterElectricWeaponType(String[] types) {
        logger.debug("Electric Weapon Types: " + Arrays.stream(types).toList());
        for (String type : types) {
            weaponTypesList.stream().filter(s -> s.getText().equalsIgnoreCase(type)).findFirst().orElse(null).click();
        }
    }

    public void filterCategory(String[] categories) {
        logger.debug("Categories: " + Arrays.stream(categories).toList());
        for (String category : categories) {
            WebElement categoryElement = categoriesList.stream().filter(s -> s.getText().equalsIgnoreCase(category)).findFirst().orElse(null);
            scrollToElementWithOffset(driver, categoryElement);

            // Click on the category
            waitforElementClickable(categoryElement);
            categoryElement.click();
        }
    }

    public void applyFilters() {
        logger.debug("Apply all choosen filters");
        applyFilters.click();
    }

    public void applyChosenFilters(String[] manufacturers, String[] weaponTypes, String[] categories) {
        if (manufacturers != null){
            filterManufacturer(manufacturers);
            logger.debug("Manufacturers has been chosen");
        }

        if (weaponTypes != null){
            filterElectricWeaponType(weaponTypes);
            logger.debug("Weapon types has been chosen");
        }

        if (categories != null){
            filterCategory(categories);
            logger.debug("Categories has been chosen");
        }

        applyFilters();
    }

    private static void scrollToElementWithOffset(WebDriver driver, WebElement element) {
        String scrollScript = "var element = arguments[0];"
                + "var yOffset = arguments[1];"
                + "var rect = element.getBoundingClientRect();"
                + "var yPosition = rect.top + window.pageYOffset + yOffset;"
                + "window.scrollTo({ top: yPosition, behavior: 'smooth' });";
        ((JavascriptExecutor) driver).executeScript(scrollScript, element, -150);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void takeScreenshot() throws IOException {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd-HH_mm-ss");
        String dateInString = dateFormat.format(currentDate);
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src, new File(".//target//evidences/" + "Evidence-UI" + dateInString + ".png"));
    }
}