package ru.aplana.autotest.pages;

import cucumber.api.java.en.When;
import io.cucumber.java.en.Then;
import io.qameta.allure.Step;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class StartPage extends BasePage{


    @FindBy(xpath = "//ul[@class='lg-menu__list']")
    WebElement menuBox;

    @Then("^пользователь выбирает элемент верхнего меню с параметром \"(.+)\"")
    @Step("Пользователь выбирает элемент верхнего меню с параметром String {menuItem}")
    public void hoverOnElement(String menuItem) {
        wait.until(ExpectedConditions.visibilityOf(Settings.driver.findElement(By.xpath("//ul[@class='lg-menu__list']"))));
        Actions builder = new Actions(Settings.getDriver());
        element = Settings.driver.findElement(By.xpath("//ul[@class='lg-menu__list']/descendant::span[text()='"+menuItem+"']"));
        builder.moveToElement(element).build().perform();
    }
    @When("^Пользователь выбирает подменю с параметром String \"(.+)\"")
    @Step ("Пользователь выбирает подменю с параметром {subMenuItem}")
    public void chooseSubMenuItem(String subMenuItem) {
        WebElement newElement = element.findElement(By.xpath("//ul[@aria-label='Подменю']"));
        element = newElement.findElement(By.xpath("//ul[@aria-label='Подменю']/descendant::a[text()='"+subMenuItem+"']"));
        element.click();
    }

}
