package PageObjects;

import AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductPage extends AbstractComponent {

    WebDriver driver;

    public ProductPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "projector_price_value")
    WebElement basePrice;
    @FindBy(id = "projector_button_basket")
    WebElement addToCartButton;

    @FindBy(xpath = "//a[@class='projector_prodstock_compare']")
    WebElement addToComparisonButton;

    public void addProductForComparison() {
        addToComparisonButton.click();
    }

    public String getPrice() {
        return basePrice.getText();
    }

    public String addToCartButtonText() {
        return addToCartButton.getText();
    }

    public double parseProductPriceFromString() {
        String productPrice = (getPrice().replaceAll(" ", "").replaceAll(",", ".").split("PLN"))[0];
        return Double.parseDouble(productPrice);
    }
}
