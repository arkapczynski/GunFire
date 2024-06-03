package PageObjects;

import AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ReplicasPage extends AbstractComponent {

    WebDriver driver;

    public ReplicasPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath ="//a[@data-id='filter_producer']/span[@class='--show']")
    WebElement showProducersButton;

    @FindBy(xpath = "//ul[@id='filter_producer_content']/li[contains(@class,'filters__item')]/div/label")
    List <WebElement> producersList;

    @FindBy(xpath = "//ul[@id='filter_traits6471_content']/li/div/label/span[@class='--name']")
    List<WebElement> weaponType;

    @FindBy(xpath = "//button[@class='__buttons-submit']")
    WebElement applyFiltersButton;

    public void filterManufacturer(String[] manufacturers) { //da się loopa zrobić żeby można było podać kilku manufactorów
        showProducersButton.click();
        for (String manufacturer : manufacturers) {
            producersList.stream().filter(s -> s.getText().contains(manufacturer)).forEach(s -> s.click());
        }
    }

    public void filterElectricWeaponType(String[] types) {
        for (String type : types) {
            weaponType.stream().filter(s -> s.getText().equalsIgnoreCase(type)).forEach(s -> s.click());
        }
    }

    public void applyFilters() {
        applyFiltersButton.click();
    }


    public void applyChosenFilters(String[] manufacturers, String[] types) {
        //zrobimy później więcej filtrów i będzie się matchowało na podstawie DataProvidera
           filterElectricWeaponType(types);
            filterManufacturer(manufacturers);
        applyFilters();
    }
}
