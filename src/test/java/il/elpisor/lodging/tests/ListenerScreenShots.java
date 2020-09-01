package il.elpisor.lodging.tests;

import il.elpisor.lodging.appmanager.AppManager;
import io.qameta.allure.Attachment;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerScreenShots implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        AppManager app = (AppManager) result.getTestContext().getAttribute("app");
        saveScreenshot(app.getScreenShot());
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshot(byte[] screenShot) {
        return screenShot;
    }

}
