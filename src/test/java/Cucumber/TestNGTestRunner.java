package Cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

    @CucumberOptions(features ="src/test/java/Cucumber", glue = "StepDefinitions", tags = "@mostExpensiveProductOfProducerPromotion",monochrome = true)
    public class TestNGTestRunner extends AbstractTestNGCucumberTests {

    }
