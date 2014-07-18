import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.GatewayPage;
import pages.authentication.LoginPage;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import utilities.Accounts;
import utilities.DriverManager;
import utilities.ElapsedTime;
import utilities.output.MessageLogger;
import utilities.output.Screenshot;

import static utilities.AssertSpecial.assertTrue;

/**
 * Created By: Brian Smith on 3/19/14.
 * Description: This test verifies the actions and error messages associated within the main gateway page.
 */
@Description("Verify various home page data elements and controls.")
public class GatewayTest {
    static WebDriver localDriver;
    private static String[] user = new String[]{"Daenerys", "Targaryen", "dtargaryen@gmail.com",
            "dtargaryen", "Pass1234"};
    private GatewayPage gatewayPage;
    private Screenshot screenshot;

    @BeforeClass
    //Open the browser and navigate to the initial page.
    public static void openBrowser() {
        // Create an account.
        Accounts.createAccount(user[0], user[1], user[2], user[3], user[4]);

        // Open a new browser window.
        localDriver = DriverManager.get();

        /* Set the size of the browser window before starting the test. Use this to control testing of the
         * minimum required resolution.
         */
        Dimension browserWindow = new Dimension(1050, 768);
        localDriver.manage().window().setSize(browserWindow);
    }

    @Before
    // Navigate to the gateway page.
    public void navigateToLoginPage() {
        ElapsedTime.setStartTime();
        screenshot = new Screenshot(localDriver);

        /* Call the LoadableComponent get() method which in turn calls isLoaded() on the class extending
         * LoadableComponent. If isLoaded() causes a failure, the load() method is called and isLoaded() is called
         * again after.
         */
        LoginPage loginPage = new LoginPage(localDriver);
        gatewayPage = new GatewayPage(localDriver, loginPage, user[2], user[4]);
        gatewayPage.get();
    }

    @Features("Gateway")
    @Stories("Verify welcome message")
    @Test
    // Verify the welcome message displays the current user's name.
    public void verifyWelcomeMessage() throws Exception {
        String welcomeMessage = gatewayPage.getWelcomeMessage();
        MessageLogger.logTestStart("GatewayTest", "VerifyWelcomeMessage");

        screenshot.getScreenShot();
        assertTrue("GatewayTest", "VerifyWelcomeMessage",
                "Welcome message is incorrect: \"" + welcomeMessage + "\"",
                welcomeMessage.equals("Welcome, " + user[0] + " " + user[1] + "."));
    }

    @Features("Gateway")
    @Stories("Logout")
    @Test
    // Verify user can log out of their account.
    public void logout() throws Exception {
        MessageLogger.logTestStart("GatewayTest", "Logout");
        gatewayPage.logout();

        try {
            new WebDriverWait(localDriver, 15).until(ExpectedConditions.titleIs("TestDriver - Login"));
        }catch (WebDriverException e) {
            MessageLogger.logError("GatewayTest", "Logout", "Login page does not display.");
        }

        screenshot.getScreenShot();
        assertTrue("GatewayTest", "Logout", "Logout failed.",
                localDriver.getTitle().equals("TestDriver - Login"));
    }

    @AfterClass
    public static void closeApplication() { localDriver.quit(); }
}
