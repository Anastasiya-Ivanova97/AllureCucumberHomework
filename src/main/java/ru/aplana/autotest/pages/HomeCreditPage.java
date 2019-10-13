package ru.aplana.autotest.pages;

import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.function.Function;

import static ru.aplana.autotest.pages.Settings.driver;

public class HomeCreditPage extends BasePage {
    public HomeCreditPage() {
    }

    private String value;

    @FindBy(xpath = "//*[@id=\"calc-main\"]/div/div")
    WebElement formBox;

    @FindBy(xpath = "//div[@class='dcCalc_calcResult_layout']")
    WebElement resultBox;

    @FindBy(xpath = "//div[@class='dcCalc_calcResult_layout']/descendant::span[@data-test-id='monthlyPayment']")
    WebElement monthlyPayment;

    @FindBy(xpath = "//div[@class='dcCalc_calcResult_layout']/descendant::span[@data-test-id='amountOfCredit']")
    WebElement amountOfCredit;

    @FindBy(xpath = "//div[@class='dcCalc_calcResult_layout']/descendant::span[@data-test-id='requiredIncome']")
    WebElement requiredIncome;

    @FindBy(xpath = "//div[@class='dcCalc_calcResult_layout']/descendant::span[@data-test-id='rate']")
    WebElement rate;

    @FindBy(id = "estateCost")
    WebElement inputCost;

    @FindBy(id = "initialFee")
    WebElement inputFee;

    @FindBy(id = "creditTerm")
    WebElement creditTerm;

    @FindBy(css = ".dcCalc_switch:nth-child(1) > .dcCalc_switch__icon-on")
    WebElement yesCard;


    @Step("Пользователь заполняет поле стоимость недвижимости")
    @When("^Пользователь заполняет поле стоимость недвижимости")
    public void sendInputCost()  {
        driver.switchTo().frame(0);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"domclickEnv\"]")));
        js.executeScript("window.scrollTo(0,900)");
        inputCost.click();
        inputCost.clear();
        inputCost.sendKeys("5180000");
        driver.switchTo().defaultContent();
    }
    @And("^пользователь вводит сумму первоначального взноса")
    @Step("пользователь вводит сумму первоначального взноса")
    public void sendInputFee() {
        driver.switchTo().frame(0);
        inputFee.click();
        inputFee.clear();
        inputFee.sendKeys("3058000");
        driver.switchTo().defaultContent();}
    @Step("пользователь заполняет поле срок кредита")
    @And("^пользователь заполняет поле срок кредита")
    public void sendCreditTerm() {
        driver.switchTo().frame(0);
        creditTerm.click();
        creditTerm.clear();
        creditTerm.sendKeys("30");
        driver.switchTo().defaultContent();}

    @Step("пользователь снимает галочку есть зарплатная карта сбербанка")
    @And("^пользователь снимает галочку есть зарплатная карта сбербанка")
    public void fieldClick() {
        driver.switchTo().frame(0);
        yesCard.click();
        driver.switchTo().defaultContent();
    }



  /*  public void chooseOptions(String option) {
        value = monthlyPayment.getText();
        element = formBox.findElement(By.xpath("/descendant::div[@class='dcCalc_switch-tablet__title' and text()='"+option+"']/following-sibling::div/descendant::span[@class='dcCalc_switch__control']"));
        element.click();
        if (option.equals("Есть зарплатная карта Сбербанка")) {
            Function<? super WebDriver, Object> isAdditionalValueAppeared = new ExpectedCondition<Object>() {
                @Override
                public Boolean apply(WebDriver webDriver) {
                    return element.findElement(By.xpath("/ancestor::div[@class='dcCalc_switch-tablet']/following-sibling::div/descendant::div[@class='dcCalc_switch-tablet__title']")).isDisplayed();
                }
            };
            wait.until(isAdditionalValueAppeared);
        }
        waitForChangedPResult();
    } */

    @Step("пользователь ждет изменения суммы ежемесячного платежа")
    @Then("^пользователь ждет изменения суммы ежемесячного платежа")
    public void waitForChangedPResult() {
        driver.switchTo().frame(0);
        element = resultBox.findElement(By.xpath("//span[@data-test-id='monthlyPayment']"));
        Function<? super WebDriver, Object> isResultChanged = new ExpectedCondition<Object>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return !value.equals(element.getText());
            }
        };
        wait.until(isResultChanged);
        driver.switchTo().defaultContent();
    }
    @And("^Пользователь проверяет сумму кредита Integer \"(.*)\"")
    @Step("Пользователь проверяет сумму кредита {sum}")
    public void checkAmount(Integer sum) {
        driver.switchTo().frame(0);
        Assert.assertEquals(sum,toNumber(amountOfCredit.getText()));
        driver.switchTo().defaultContent();
    }
    @And("^Пользователь проверяет ежемесячный платеж Integer \"(.*)\"")
    @Step("Пользователь проверяет ежемесячный платеж {mp}")
    @Attachment(type = "image/png", value = "Screenshot")
    public void checkMPayment(Integer mp) {
        driver.switchTo().frame(0);
        Assert.assertEquals(mp,toNumber(monthlyPayment.getText()));
        driver.switchTo().defaultContent();
    }
    @And("^Пользователь проверяет необходимый доход Integer \"(.*)\"")
    @Step("Пользователь проверяет необходимый доход {income}")
    public void checkIncome(Integer income) {
        driver.switchTo().frame(0);
        Assert.assertEquals(income,toNumber(requiredIncome.getText()));
        driver.switchTo().defaultContent();
    }
    @And("^Пользователь проверяет процент Double \"(.*)\"")
    @Step("Пользователь проверяет процент {percent}")
    public void checkPercent(Double percent) {
        driver.switchTo().frame(0);
        Assert.assertEquals(percent,toNumber(rate.getText()));
        driver.switchTo().defaultContent();
    }

    public Object toNumber(String before) {
        String newString = before.replaceAll("\\D^|,|", "");
        if (before.contains(",")) {
            return Double.parseDouble(newString);
        }
        return Integer.parseInt(newString);
    }

    public static void shutDown(){
        driver.quit();
    }
  /*  @After
	public void tearDown(){
    	driver.quit();
	} */
}
