package drivers.browserstack;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created By: Brian Smith on 4/22/15.
 * Package: utilities.drivers.browserstack
 * Description: This creates connections to the BrowserStack cloud.
 */
public class BrowserStack {

    private String cloudURL;
    private DesiredCapabilities bsCap;

    public BrowserStack() {
        String userName = System.getProperty("username", "annonymous");
        String authenticationID = System.getProperty("auth_key", "0");
        cloudURL = "http://" + userName + ":" + authenticationID + "@hub.browserstack.com/wd/hub";

        bsCap = new DesiredCapabilities();
        switch (System.getProperty("mobile", "false")) {
            case "true":
                bsCap.setCapability("browserName", System.getProperty("mobile_type", "iPad"));
                bsCap.setCapability("platform", System.getProperty("mobile_platform", "MAC"));
                bsCap.setCapability("device", System.getProperty("mobile_device", "iPad Air"));
                break;
            case "false":
                bsCap.setCapability("browser", System.getProperty("browser", "firefox"));
                bsCap.setCapability("browser_version", System.getProperty("browser_version", "34"));
                bsCap.setCapability("resolution", System.getProperty("resolution", "1024x768"));
                break;
            default:
                throw new Error("Mobile system property must be set to either true or false.");
        }

        // Set BrowserStack debug visual logs.
        bsCap.setCapability("browserstack.debug", System.getProperty("debug", "false"));

        Integer length = System.getProperty("build", "xxxx").length();
        String buildNumber = System.getProperty("build", "xxxx");
        for (int i = length; i < 4; i++) {
            buildNumber = "0" + buildNumber;
        }

        bsCap.setCapability("browserstack.local", "true");
        bsCap.setCapability("browserstack.localIdentifier", System.getProperty("test_id", "1"));
        bsCap.setCapability("build", System.getProperty("browser", "firefox") + "-" + buildNumber);
        bsCap.setCapability("project", System.getProperty("project_name", "Unknown"));
        bsCap.setCapability("browserstack.selenium_version", System.getProperty("selenium", "2.45.0"));
    }

    public WebDriver connect() {
        WebDriver driver;

        try {
            driver = new RemoteWebDriver(new URL(cloudURL), bsCap);
            ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
        } catch (MalformedURLException e) {
            throw new Error(e);
        }

        return driver;
    }
}
