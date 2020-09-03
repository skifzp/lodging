package il.elpisor.lodging.appmanager;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class AppManager {

    protected WebDriver wd;
    private GroupHelper groupHelper;
    private NavigationHelper navigationHelper;
    private SessionHelper sessionHelper;
    private ContactHelper contactHelper;
    private String browser;
    private Properties properties;
    private DbHelper db;

    public AppManager(String browser) {
        this.browser = browser;
        this.properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        //String target = System.getProperty("target");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
        db = new DbHelper(properties);
        setWd();
        groupHelper = new GroupHelper(wd);
        navigationHelper = new NavigationHelper(wd);
        contactHelper = new ContactHelper(wd);
        sessionHelper = new SessionHelper(wd);
        sessionHelper.login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPWD"));
    }

    public void stop() {
        wd.quit();
        db.stop();
    }

    public DbHelper db() {
        return db;
    }

    public GroupHelper group() {
        return groupHelper;
    }

    public ContactHelper contact() {
        return contactHelper;
    }

    public NavigationHelper goTo() {
        return navigationHelper;
    }

    public byte[] getScreenShot(){
        return ((TakesScreenshot) wd).getScreenshotAs(OutputType.BYTES);
    }

    private void setWd() throws MalformedURLException {
        //System.out.println("browser is " + System.getProperty("browser"));

        if("".equals(properties.getProperty("selenium.server"))) {
            if (browser.equals(BrowserType.FIREFOX)) {
                wd = new FirefoxDriver();
            } else if (browser.equals(BrowserType.CHROME)) {
                wd = new ChromeDriver();
            } else if (browser.equals(BrowserType.IE)) {
                wd = new InternetExplorerDriver();
            }
        }else{
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(browser);
            capabilities.setPlatform(Platform.fromString(System.getProperty("platform", "win10")));
            //capabilities.setAcceptInsecureCerts(true);
            //capabilities.setJavascriptEnabled(true);
            wd = new RemoteWebDriver(new URL(properties.getProperty("selenium.server")), capabilities);
        }
        wd.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        wd.get(properties.getProperty("web.baseUrl"));
    }

}
