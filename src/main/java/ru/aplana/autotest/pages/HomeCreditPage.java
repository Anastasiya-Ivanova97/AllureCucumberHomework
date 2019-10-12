package ru.aplana.autotest.pages;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import ru.aplana.autotest.steps.Settings;

import java.util.function.Function;

import static ru.aplana.autotest.steps.Settings.driver;

public class HomeCreditPage extends BasePage{
    public HomeCreditPage() {
    }

    private String value;

    @FindBy(xpath = "//div[@id='calc-main']")
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

    ////div[@id='calc-main']/descendant::div[@class='dcCalc_switch-tablet__title' and text()='Есть зарплатная карта Сбербанка']/following-sibling::div/descendant::span[@class='dcCalc_switch__control']/ancestor::div[@class='dcCalc_switch-tablet']/following-sibling::div/descendant::div[@class='dcCalc_switch-tablet__title']
    @Step
    @When("Когда пользователь вводит в поле Стоимость недвижимости 51800000")
    public void chooseAndFillFormField(String formName, String keys) {
        driver.switchTo().frame(0);
        System.out.println(driver.getTitle());
        value = monthlyPayment.getText();
        element = formBox.findElement(By.xpath("//div[@id='calc-main']/descendant::div[text()='"+formName+"']/following-sibling::div/descendant::input"));
        element.click();
        element.clear();
        element.sendKeys(keys);

        Function<? super WebDriver, Object> isValueChanged = new ExpectedCondition<Object>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return element.getText().equals(keys);
            }
        };
        wait.until(isValueChanged);

        waitForChangedPResult();
        driver.switchTo().defaultContent();
    }

    @Step
    @When("Когда пользователь снимает галочку Есть зарплатная карта сбербанка")
    public void chooseOptions(String option) {

        value = monthlyPayment.getText();
        element = formBox.findElement(By.xpath("//div[@id='calc-main']/descendant::div[@class='dcCalc_switch-tablet__title' and text()='"+option+"']/following-sibling::div/descendant::span[@class='dcCalc_switch__control']"));
        element.click();
        if (option.equals("Есть зарплатная карта Сбербанка")) {
            Function<? super WebDriver, Object> isAdditionalValueAppeared = new ExpectedCondition<Object>() {
                @Override
                public Boolean apply(WebDriver webDriver) {
                    return element.findElement(By.xpath("//div[@id='calc-main']/descendant::div[@class='dcCalc_switch-tablet__title' and text()='Есть зарплатная карта Сбербанка']/following-sibling::div/descendant::span[@class='dcCalc_switch__control']/ancestor::div[@class='dcCalc_switch-tablet']/following-sibling::div/descendant::div[@class='dcCalc_switch-tablet__title']")).isDisplayed();
                }
            };
            wait.until(isAdditionalValueAppeared);
        }
        waitForChangedPResult();

    }
    @Step
    public void waitForChangedPResult() {
        element = resultBox.findElement(By.xpath("//div[@class='dcCalc_calcResult_layout']/descendant::span[@data-test-id='monthlyPayment']"));
        Function<? super WebDriver, Object> isResultChanged = new ExpectedCondition<Object>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return !value.equals(element.getText());
            }
        };
        wait.until(isResultChanged);
    }
    @Step
    @Then("Тогда пользователь проверяет 2122000, 18937, 31561, 11")
    public void checkValues(Integer sum, Integer mp, Integer income, Double percent) {
        Assert.assertEquals(sum,toNumber(amountOfCredit.getText()));
        Assert.assertEquals(mp,toNumber(monthlyPayment.getText()));
        Assert.assertEquals(income,toNumber(requiredIncome.getText()));
        Assert.assertEquals(percent,toNumber(rate.getText()));

    }
    public Object toNumber(String before) {
            String newString = before.replaceAll("\\D^|,|", "");
            if (before.contains(",")) {
                return Double.parseDouble(newString);
            }
        return Integer.parseInt(newString);
    }
}
