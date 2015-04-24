package site;

import drivers.DriverManager;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import output.Screenshot;

/**
 * Created By: Brian Smith on 10/8/14.
 * Package: test/java
 */
public class BaseTest {

    public WebDriver localDriver;

    @Rule
    public TestName testName = new TestName();

    public Boolean getScreenShot() {
        try {
            Screenshot screenshot = new Screenshot(localDriver);
            screenshot.getScreenShot();
            return true;
        }catch (NullPointerException e) {
            System.out.println("Cannot take screenshot as the browser is not available.");
            return false;
        }
    }

    public void hideElement(WebElement element) {
        ((JavascriptExecutor)localDriver).executeScript("arguments[0].style.visibility='hidden'", element);
        new WebDriverWait(localDriver, 15).until(
                ExpectedConditions.not(ExpectedConditions.visibilityOf(element)));
    }

    public void manualWait(Integer seconds) {
        try {
            Thread.sleep(seconds * 1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void navigate_to(String page) {
        localDriver.navigate().to(System.getProperty("url", "http://no_valid_url") + "/" + page);
    }

    public void openBrowser(String testName) {
        do {
            localDriver = DriverManager.get(testName);
        }while (localDriver.toString().contains("null"));

        if (System.getProperty("remote", "false").equals("false")) {
            String res = System.getProperty("resolution", "1024x768");
            String[] sRes = res.split("x");
            setBrowserDimensions(Integer.parseInt(sRes[0]), Integer.parseInt(sRes[1]));
        }
    }

    public void quit() {
        try {
            localDriver.quit();
        }catch (NullPointerException e) {
            System.out.println("Cannot quit as the browser is not available.");
        }
    }

    public void setBrowserDimensions(Integer w, Integer h) {
        Dimension browserWindow = new Dimension(w, h);
        localDriver.manage().window().setSize(browserWindow);
    }
}
