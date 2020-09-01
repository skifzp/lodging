package il.elpisor.lodging.appmanager;

import org.openqa.selenium.*;

import java.io.File;

public class HelperBase {
    protected WebDriver wd;

    public HelperBase(WebDriver wd) {
        this.wd = wd;
    }

    protected void click(By locator) {
        wd.findElement(locator).click();
    }

    protected void type(By locator, String text) {
        click(locator);
        if (text != null) { // for default value
            String existText = wd.findElement(locator).getAttribute("value");
            if (!text.equals(existText)) {
                wd.findElement(locator).clear();
                wd.findElement(locator).sendKeys(text);
            }
        }
    }

    protected void attach(By locator, File file) {
        if (file != null) { // for default value
            wd.findElement(locator).sendKeys(file.getAbsolutePath());
        }
    }

    protected void closeAlertWindow() {
        try {
            // Check the presence of alert
            Alert alert = wd.switchTo().alert();
            // if present consume the alert
            alert.accept();
        } catch (NoAlertPresentException ex) {
            System.out.println("failed to close alert window");
        }
    }

    public boolean isAlertPresent() {
        try {
            wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    protected boolean isElementPresent(By locator) {
        try {
            wd.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }

    }
}
