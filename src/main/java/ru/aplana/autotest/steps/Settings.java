package ru.aplana.autotest.steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.aplana.autotest.utils.TestProperties;

import java.util.concurrent.TimeUnit;

public class Settings {
    public static WebDriver driver;
    public static TestProperties testProperties = TestProperties.getInstance();
    @Given("открывается стартовая страница")
    public static void init() {
        System.setProperty("webdriver.chrome.driver", testProperties.getProperty("chromedriver"));
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(testProperties.getProperty("url"));
        driver .manage().timeouts().implicitlyWait(Integer.parseInt(testProperties.getProperty("timeout.global")), TimeUnit.SECONDS);
       // driver .manage().timeouts().pageLoadTimeout(Integer.parseInt(testProperties.getProperty("timeout.pageLoad")), TimeUnit.SECONDS);
    }
    @After
    public static void shutDown(){
        driver.quit();
    }



    @Attachment(type = "image/png", value = "Screenshot")
    public static byte[] takeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }


    public static WebDriver getDriver(){
        return driver;
    }
}
