package drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import drivers.browserstack.BrowserStack;
import drivers.saucelabs.SauceLabs;

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

        switch (System.getProperty("remote", "false")) {
            case "saucelabs":
                SauceLabs slConnection = new SauceLabs(testName);
                slConnection.connect();
            case "browserstack":
                BrowserStack bsConnection = new BrowserStack();
                bsConnection.connect();
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
                String browserProperty = System.getProperty("browser", "none");
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
                        String driverLocation = "src/main/drivers/local/chrome/chromedriver-2.15-" + osNameShort;
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
                throw new Error("Remote system property must be set to browserstack or saucelabs. False is the default.");
        }
    }
}
