package site;

import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.DriverManager;
import utilities.output.Screenshot;

/**
 * Created By: Brian Smith on 10/8/14.
 * Package: test/java
 */
public class BaseTest {
    public static WebDriver localDriver;

    @Rule
    public TestName testName = new TestName();

    public static void getScreenShot() {
        Screenshot screenshot = new Screenshot(localDriver);
        screenshot.getScreenShot();
    }

    public static void hideElement(WebElement element) {
        ((JavascriptExecutor)localDriver).executeScript("arguments[0].style.visibility='hidden'", element);
        new WebDriverWait(localDriver, 15).until(
                ExpectedConditions.not(ExpectedConditions.visibilityOf(element)));
    }

    public static void manualWait(Integer seconds) {
        try {
            Thread.sleep(seconds * 1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void navigate_to(String page) {
        localDriver.navigate().to("http://localhost:3000/" + page);
    }

    public static void openBrowser(String testName) {
        localDriver = DriverManager.get(testName);
        if (System.getProperty("remote", "false").equals("false")) {
            setBrowserDimensions(1024,768);
        }
    }

    public static void setBrowserDimensions(Integer w, Integer h) {
        Dimension browserWindow = new Dimension(w, h);
        localDriver.manage().window().setSize(browserWindow);
    }
}
