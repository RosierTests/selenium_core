package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

/**
 * Created By: bsmith on 3/10/14.
 * Package: none
 * Description: This class controls which browser is used, browser capabilities, and remote configuration settings.
 */
public class DriverManager {
    public enum Browser{CHROME, FIREFOX, IE, SAFARI}

    // Get the driver object by passing in the the chosen browser.
    public static WebDriver get(String testName) {
        WebDriver driver;

        String userName;
        String authenticationID;
        String cloudURL;

        switch (System.getProperty("remote", "false")) {
            case "saucelabs":
                userName = System.getProperty("username", "annonymous");
                authenticationID = System.getProperty("auth_key", "0");
                cloudURL = "http://" + userName + ":" + authenticationID + "@ondemand.saucelabs.com:80/wd/hub";
                DesiredCapabilities slCap;

                switch (System.getProperty("browser", "firefox")) {
                    case "firefox":
                        slCap = DesiredCapabilities.firefox();
                        break;
                    case "chrome":
                        slCap = DesiredCapabilities.chrome();
                        break;
                    case "ie":
                        slCap = DesiredCapabilities.internetExplorer();
                        break;
                    case "safari":
                        slCap = DesiredCapabilities.safari();
                        break;
                    default:
                        throw new Error("You must choose a browser to test against.");
                }

                slCap.setCapability("version", System.getProperty("browser_version", "34.0"));
                slCap.setCapability("platform", System.getProperty("platform", "Windows 7"));
                slCap.setCapability("screen-resolution", System.getProperty("resolution", "1024x768"));
                slCap.setCapability("name", testName);

                try {
                    driver = new RemoteWebDriver(new URL(cloudURL), slCap);
                    ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
                } catch (MalformedURLException e) {
                    throw new Error(e);
                }

                return driver;
            case "browserstack":
                userName = System.getProperty("username", "annonymous");
                authenticationID = System.getProperty("auth_key", "0");
                cloudURL = "http://" + userName + ":" + authenticationID + "@hub.browserstack.com/wd/hub";

                DesiredCapabilities bsCap = new DesiredCapabilities();
                switch (System.getProperty("mobile", "false")) {
                    case "true":
                        bsCap.setCapability("browserName", System.getProperty("mobile_type", "iPad"));
                        bsCap.setCapability("platform", System.getProperty("mobile_platform", "MAC"));
                        bsCap.setCapability("device", System.getProperty("mobile_device", "iPad Air"));
                        break;
                    case "false":
                        bsCap.setCapability("browser", System.getProperty("browser", "firefox"));
                        bsCap.setCapability("browser_version", System.getProperty("browser_version", "34"));
                        bsCap.setCapability("resolution", "1024x768");
                        break;
                    default:
                        throw new Error("Mobile system property must be set to either true or false.");
                }

                // Set BrowserStack debug visual logs.
                bsCap.setCapability("browserstack.debug", System.getProperty("debug_mode", "false"));

                Integer length = System.getProperty("build", "xxxx").length();
                String buildNumber = System.getProperty("build", "xxxx");
                for (int i = length; i < 4; i++) {
                    buildNumber = "0" + buildNumber;
                }

                // Create random number as local identifier if not supplied.
                Random randomGenerator = new Random();
                Integer randomNum = randomGenerator.nextInt(100000);

                bsCap.setCapability("browserstack.local", "true");
                bsCap.setCapability("browserstack.localIdentifier", System.getProperty("test_id", randomNum.toString()));
                bsCap.setCapability("build", System.getProperty("browser", "firefox") + "-" + buildNumber);
                bsCap.setCapability("project", System.getProperty("project_name", "Unknown"));

                try {
                    driver = new RemoteWebDriver(new URL(cloudURL), bsCap);
                    ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
                } catch (MalformedURLException e) {
                    throw new Error(e);
                }

                return driver;
            case "false":
                // Get the current operating system name.
                String osName = System.getProperty("os.name");
                String osNameShort;

                if (osName.contains("Mac")) {
                    osNameShort = "osx";
                } else if (osName.contains("Windows")) {
                    osNameShort = "win";
                } else if (osName.contains("Linux")) {
                    osNameShort = "lin";
                } else {
                    throw new RuntimeException("The operating system " + osName + " does not support this test suite.");
                }

                // Get the browser property and set it if available.
                String browserProperty = System.getProperty("browser", "firefox");
                Browser chosenBrowser;
                switch (browserProperty.toLowerCase()) {
                    case "chrome":
                        chosenBrowser = Browser.CHROME;
                        break;
                    case "firefox":
                        chosenBrowser = Browser.FIREFOX;
                        break;
                    case "ie":
                        chosenBrowser = Browser.IE;
                        break;
                    case "safari":
                        chosenBrowser = Browser.SAFARI;
                        break;
                    default:
                        throw new Error("You must choose a browser to test against.");
                }

                switch (chosenBrowser) {
                    case CHROME:
                        // Change the driver location based on the operating system name found, above.
                        String driverLocation = "src/main/utilities/drivers/chromedriver-2.12-" + osNameShort;
                        System.setProperty("webdriver.chrome.driver", driverLocation);

                        ChromeOptions options = new ChromeOptions();
                        options.addArguments("disable-plugins");
                        options.addArguments("disable-extensions");

                        driver = new ChromeDriver(options);
                        break;
                    case FIREFOX:
                        driver = new FirefoxDriver();
                        break;
                    case IE:
                        driver = new InternetExplorerDriver();
                        break;
                    case SAFARI:
                        driver = new SafariDriver();
                        break;
                    default:
                        throw new Error("Local browser not configured.");
                }

                return driver;
            default:
                throw new Error("Remote system property must be set to either true or false.");
        }
    }
}
