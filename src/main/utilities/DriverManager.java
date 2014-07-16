package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

/**
 * Created By: bsmith on 3/10/14.
 * Package: none
 * Description: This class controls which browser is used, browser capabilities, and remote configuration settings.
 */
public class DriverManager {
    private static WebDriver driver = null;
    public enum Browser{CHROME, FIREFOX, IE, SAFARI}
    private static Browser chosenBrowser = null;
    public static final String BROWSER = "browser";

    // Set the browser being used.
    public static void set(Browser browser) {
        chosenBrowser = browser;

        // Close existing driver if it is present.
        if(driver != null){
            driver.quit();
            driver = null;
        }
    }

    // Get the driver object by passing in the the chosen browser.
    public static WebDriver get() {
        // Get the current operating system name.
        String osName = System.getProperty("os.name");
        String osNameShort = "";

        if (osName.contains("Mac")) {
            osNameShort = "osx";
        }else if (osName.contains("Windows")) {
            osNameShort = "win";
        }else if (osName.contains("Linux")) {
            osNameShort = "lin";
        }else {
            throw new RuntimeException("The operating system " + osName + " does not support this test suite.");
        }

        // Get the browser property and set it if available.
        String browserProperty = System.getProperty(BROWSER, "firefox");
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
        }

        switch (chosenBrowser) {
            case CHROME:
                // Change the driver location based on the operating system name found, above.
                String driverLocation = "src/main/utilities/drivers/chromedriver-2.9-" + osNameShort;
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
        }

        return driver;
    }
}
