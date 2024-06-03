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

    public void addProductForComparison(){
        driver.findElement(By.xpath("//a[@class=\"projector_prodstock_compare\"]")).click();
    }

    public String getPrice(){
       String price= basePrice.getText();
       return price;
    }

    public String addToCartButtonText(){
        String cartButtonText= addToCartButton.getText();
        return cartButtonText;
    }

    public double parseProductPriceFromString(){
        String productPrice= (getPrice().replaceAll(" ", "").replaceAll(",",".").split("PLN"))[0];
        double priceInDouble = Double.parseDouble(productPrice);
        return priceInDouble;
    }

}
