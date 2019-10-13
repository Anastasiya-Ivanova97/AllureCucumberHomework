package ru.aplana.autotest.utils;

import io.qameta.allure.Attachment;
import io.qameta.allure.junit4.AllureJunit4;
import org.junit.runner.notification.Failure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import ru.aplana.autotest.pages.Settings;

public class AllureListener extends AllureJunit4 {
    @Override
    public void testFailure(Failure failure) {
        super.testFailure(failure);
        takeScreenshot(Settings.getDriver());
    }


    @Attachment(type = "image/png", value = "Screenshot")
    public static byte[] takeScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
