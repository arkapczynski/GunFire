package PageObjects;

import AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.*;
import java.util.stream.Collectors;

public class ComparePage extends AbstractComponent {

    WebDriver driver;

    public ComparePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//a[@href='#differences']")
    WebElement differenceButton;
    @FindBy(xpath = "//tr[@class='highlight']")
    List<WebElement> differencesParameterListWithValues;

    public void clickOnDifferencesButton(){
        differenceButton.click();
    }

    public List<String> getChosenParameterWithValues(String comparisonParameter) {
        List<String> parameterValues = new ArrayList<>();
        for (WebElement parameterWithValues : differencesParameterListWithValues) {
            List<WebElement> allChildElements = new ArrayList<>();
            allChildElements.addAll(parameterWithValues.findElements(By.xpath("./*")));
            if (allChildElements.get(0).getText().equals(comparisonParameter)) {
                    parameterValues = allChildElements.stream().map(s->s.getText()).collect(Collectors.toList());
                }
            }
        return parameterValues;
    }

    public Map<String, String> comparisonParametersOfProduct(int indexOfProduct) {
        Map<String, String> productComparisonMap = new HashMap<>();
        for (WebElement parameterWithValues : differencesParameterListWithValues) {
            List<WebElement> allChildElements = new ArrayList<>();
            allChildElements.addAll(parameterWithValues.findElements(By.xpath("./*")));
            String parameterName = allChildElements.get(0).getText();
            String parameterValue;
            if (parameterName.equals("Cena")) {
                String regex = "(?<=\\p{L})(?=\\d)"; // position between a letter and a digit
                String[] splitPrices = allChildElements.get(indexOfProduct).getText().split(regex);
                parameterValue = splitPrices[0].trim();
            } else {
                parameterValue = allChildElements.get(indexOfProduct).getText();
            }
            productComparisonMap.put(parameterName, parameterValue);
        }
        return productComparisonMap;
    }
}

