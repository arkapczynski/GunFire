package PageObjects;

import AbstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

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
    List<WebElement> differences;

    public void clickOnDifferencesButton(){
        differenceButton.click();
    }
    public void compareReplicas() {
              for (WebElement difference : differences) {
            System.out.println(difference.getText()+"\n");
        }
    }

    public ArrayList<String> compareProductsBy(String comparisonParameter) {
        ArrayList<String> checkIfUniqueValues= new ArrayList<String>();
        for (WebElement difference : differences) {
            if (difference.getText().contains(comparisonParameter)) {
                String[] differencesValues = difference.getText().split("\n");
                for (int i = 1; i < differencesValues.length; i++) {
                    checkIfUniqueValues.add(differencesValues[i]);
                }
            }
        }
        System.out.println(checkIfUniqueValues);
        return checkIfUniqueValues;
    }



}

