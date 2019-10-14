package ru.aplana.autotest.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;


import static ru.aplana.autotest.pages.Settings.driver;

public class BasePage {
    public WebElement element;
    public WebDriverWait wait;

    public BasePage() {
        PageFactory.initElements(driver, this);
        wait =  new WebDriverWait(driver,10);
    }

}
