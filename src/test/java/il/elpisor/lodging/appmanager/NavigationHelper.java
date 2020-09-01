package il.elpisor.lodging.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {

    private Boolean wasTransitionToAnotherPage = false;

    public NavigationHelper(WebDriver wd) {
        super(wd);
    }

    public void groupPage() {
        if (isElementPresent(By.tagName("h1"))
                && wd.findElement(By.tagName("h1")).getText().equals("Groups")
                && isElementPresent(By.name("new"))) {
            // in case we are already on the GROUP page
            setTransition();
            return;
        }
        setTransition();
        click(By.linkText("groups"));
    }

    public void homePage() {
        if (isElementPresent(By.id("maintable"))) {
            setTransition();
            return;
        }
        setTransition();
        click(By.linkText("home"));
    }

    public void back() {
        setTransition();
        wd.navigate().back();
    }

    private void setTransition() {
        this.wasTransitionToAnotherPage = !wasTransitionToAnotherPage;
    }
}
