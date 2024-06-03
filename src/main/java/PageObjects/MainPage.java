package PageObjects;

import AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends AbstractComponent {

    WebDriver driver;
    public PromotionPage promotionSearch;

    public MainPage (WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
        public PromotionPage goToPromotionsBottomButton(){
         driver.findElement(By.xpath("//a[@href='/pl/promotions/promocja.html']")).click();
         return promotionSearch=new PromotionPage(driver);
    }

    public void dealWithNewsletter(){

    }

    public void dealWithCookies(){

    }
}
