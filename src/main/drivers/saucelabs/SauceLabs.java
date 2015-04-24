package drivers.saucelabs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created By: Brian Smith on 4/22/15.
 * Package: utilities.drivers.saucelabs
 * Description: This creates connections to the SauceConnect cloud.
 */
public class SauceLabs {

    private String cloudURL;
    private DesiredCapabilities cap;

    public SauceLabs(String testName) {
        String userName = System.getProperty("username", "annonymous");
        String authenticationID = System.getProperty("auth_key", "0");
        cloudURL = "http://" + userName + ":" + authenticationID + "@ondemand.saucelabs.com:80/wd/hub";

        switch (System.getProperty("browser", "firefox")) {
            case "firefox":
                cap = DesiredCapabilities.firefox();
                break;
            case "chrome":
                cap = DesiredCapabilities.chrome();
                break;
            case "ie":
                cap = DesiredCapabilities.internetExplorer();
                break;
            case "safari":
                cap = DesiredCapabilities.safari();
                break;
            default:
                throw new Error("You must choose a browser to test against.");
        }

        cap.setCapability("version", System.getProperty("browser_version", "34.0"));
        cap.setCapability("platform", System.getProperty("platform", "Windows 7"));
        cap.setCapability("screen-resolution", System.getProperty("resolution", "1024x768"));
        cap.setCapability("name", testName);
    }

    public WebDriver connect() {
        WebDriver driver;

        try {
            driver = new RemoteWebDriver(new URL(cloudURL), cap);
            ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
        } catch (MalformedURLException e) {
            throw new Error(e);
        }

        return driver;
    }
}
