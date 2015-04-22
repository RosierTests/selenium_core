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

    public  WebDriver localDriver;

    @Rule
    public TestName testName = new TestName();

    public Boolean getScreenShot() {
        if (!localDriver.toString().contains("null")) {
            Screenshot screenshot = new Screenshot(localDriver);
            screenshot.getScreenShot();
            return true;
        }else {
            return false;
        }
    }

    public  void hideElement(WebElement element) {
        ((JavascriptExecutor)localDriver).executeScript("arguments[0].style.visibility='hidden'", element);
        new WebDriverWait(localDriver, 15).until(
                ExpectedConditions.not(ExpectedConditions.visibilityOf(element)));
    }

    public  void manualWait(Integer seconds) {
        try {
            Thread.sleep(seconds * 1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  void navigate_to(String page) {
        localDriver.navigate().to(System.getProperty("url", "http://no_valid_url") + "/" + page);
    }

    public  void openBrowser(String testName) {
        do {
            localDriver = DriverManager.get(testName);
        }while (localDriver.toString().contains("null"));

        if (System.getProperty("remote", "false").equals("false")) {
            setBrowserDimensions(1024,768);
        }
    }

    public void quit() {
        if (!localDriver.toString().contains("null")) {
            localDriver.quit();
        }
    }

    public  void setBrowserDimensions(Integer w, Integer h) {
        Dimension browserWindow = new Dimension(w, h);
        localDriver.manage().window().setSize(browserWindow);
    }
}
