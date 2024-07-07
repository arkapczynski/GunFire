package PageObjects;

import AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class ProducersPage extends AbstractComponent {

    WebDriver driver;

    public ProducersPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void chooseProducer(String producerName){
        driver.findElement(By.xpath("//a[@title='"+producerName+"']")).click();
    }

    public void cheapestProductsOfProducer(String producerName) {
        chooseProducer(producerName);
        sortBy("priceup");
        printFirstElements(5);
    }
}
