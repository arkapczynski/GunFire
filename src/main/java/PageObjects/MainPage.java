package PageObjects;

import AbstractComponents.AbstractComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends AbstractComponent {

    public PromotionPage promotionSearch;

    WebDriver driver;
    @FindBy(xpath="//a[@href='/pl/promotions/promocja.html']")
    WebElement promotionsBottomButton;

    public MainPage (WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public PromotionPage goToPromotionsBottomButton(){
        logger.debug("Go to promotions");
        promotionsBottomButton.click();
        return promotionSearch=new PromotionPage(driver);
    }

    public void dealWithNewsletter(){

    }

    public void dealWithCookies(){

    }
}
