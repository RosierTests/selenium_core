import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

/**
 * Created By: bsmith on 3/10/14.
 * Package: none
 * Description: This class controls which browser is used, browser capabilities, and remote configuration settings.
 */
public class DriverManager {
    private static WebDriver driver = null;
    public enum Browser{CHROME, FIREFOX, IE, OPERA, SAFARI, REMOTE}
    private static Browser chosenBrowser = null;

    // Set the browser being used.
    public static void set(Browser browser){
        chosenBrowser = browser;

        // Close existing driver if it is present.
        if(driver != null){
            driver.quit();
            driver = null;
        }
    }

    // Get the driver object by passing in the the chosen browser.
    public static WebDriver get() {
        if (driver == null) {
            switch (chosenBrowser) {
                case CHROME:
                    break;
                case FIREFOX:
                    FirefoxProfile profile = new FirefoxProfile();
                    driver = new FirefoxDriver();
                    break;
                case IE:
                    break;
                case OPERA:
                    break;
                case SAFARI:
                    break;
            }
        }else {
            // The driver should not already be available.
        }

        return driver;
    }
}
