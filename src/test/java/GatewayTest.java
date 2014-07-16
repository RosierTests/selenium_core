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
import utilities.Accounts;
import utilities.DriverManager;
import utilities.ElapsedTime;
import utilities.output.MessageLogger;

import static utilities.AssertSpecial.assertTrue;

/**
 * Created By: Brian Smith on 3/19/14.
 * Description: This test verifies the actions and error messages associated within the main gateway page.
 */
public class GatewayTest {
    static WebDriver localDriver;
    private static String[] user = new String[]{"Daenerys", "Targaryen", "dtargaryen@gmail.com",
            "dtargaryen", "Pass1234"};
    private GatewayPage gatewayPage;

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

        /* Call the LoadableComponent get() method which in turn calls isLoaded() on the class extending
         * LoadableComponent. If isLoaded() causes a failure, the load() method is called and isLoaded() is called
         * again after.
         */
        LoginPage loginPage = new LoginPage(localDriver);
        gatewayPage = new GatewayPage(localDriver, loginPage, user[2], user[4]);
        gatewayPage.get();
    }

    @Test
    // Verify the welcome message displays the current user's name.
    public void verifyWelcomeMessage() throws Exception {
        MessageLogger.logTestStart("GatewayTest", "VerifyWelcomeMessage");
        assertTrue("GatewayTest", "VerifyWelcomeMessage",
                "Welcome message is incorrect: \"" + gatewayPage.getWelcomeMessage() + "\"",
                gatewayPage.getWelcomeMessage().equals("Welcome, " + user[0] + " " + user[1] + "."));
    }

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

        assertTrue("GatewayTest", "Logout", "Logout failed.",
                localDriver.getTitle().equals("TestDriver - Login"));
    }

    @AfterClass
    public static void closeApplication() { localDriver.quit(); }
}
