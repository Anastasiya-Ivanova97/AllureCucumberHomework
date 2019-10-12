package ru.aplana.autotest.pages;

import cucumber.api.java.en.When;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import ru.aplana.autotest.steps.Settings;

import java.util.function.Function;

public class StartPage extends BasePage{
    static {
        Settings.init();
    }

    ////ul[@class='lg-menu__list']/descendant::span[text()='Ипотека']/ancestor::li/descendant::ul[@class='lg-menu__sub-list']/descendant::a[text()='Ипотека на готовое жильё']

    @FindBy(xpath = "//ul[@class='lg-menu__list']")
    WebElement menuBox;

    @Step
    @When("пользователь наводит курсор на элемент Ипотека верхнего меню")
    public void hoverOnElement(String menuItem) {
        Actions builder = new Actions(Settings.getDriver());
        element = menuBox.findElement(By.xpath("//ul[@class='lg-menu__list']/descendant::span[text()='"+menuItem+"']"));
        builder.moveToElement(element).build().perform();
    }
    @Step
    @When("Когда пользователь выбирает элемент Ипотека на готовое жилье из открывшегося меню")
    public void chooseSubMenuItem(String subMenuItem) {
        WebElement newElement = element.findElement(By.xpath("//ul[@aria-label='Подменю']"));
        element = newElement.findElement(By.xpath("//ul[@aria-label='Подменю']/descendant::a[text()='"+subMenuItem+"']"));
        element.click();

    }

}
