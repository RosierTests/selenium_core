import org.junit.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.authentication.LoginPage;
import utilities.Accounts;
import utilities.DriverManager;
import utilities.ElapsedTime;
import utilities.output.MessageLogger;
import utilities.users.User;

import static utilities.AssertSpecial.assertTrue;

/**
 * Created By: Brian Smith on 3/4/14.
 * Description: This test verifies the actions and error messages associated with the login process.
 */
public class LoginTest {
    static WebDriver localDriver;
    /* Set a new User object with basic user data. This must be valid info in the database. You could also assign this
     * dynamically through the createUser method of the Accounts page enhancing reusable account creation
     * at any point you need it.
     */
    private User user = new User("Tywin", "Lannister", "tlannister@gmail.com", "tlannister", "Pass1234");
    private LoginPage loginPage;
    private String emailErrorText = "You must enter a valid email address.";
    private String passwordErrorText = "You must enter your password.";

    @BeforeClass
    //Open the browser and navigate to the initial page.
    public static void openBrowser() {
        // Open a new browser window.
        localDriver = DriverManager.get();

        /* Set the size of the browser window before starting the test. Use this to control testing of the
         * minimum required resolution.
         */
        Dimension browserWindow = new Dimension(1050, 768);
        localDriver.manage().window().setSize(browserWindow);
    }

    @Before
    // Navigate to the login page.
    public void navigateToLoginPage() {
        ElapsedTime.setStartTime();

        /* Call the LoadableComponent get() method which in turn calls isLoaded() on the class extending
         * LoadableComponent. If isLoaded() causes a failure, the load() method is called and isLoaded() is called
         * again after.
         */
        loginPage = new LoginPage(localDriver);
        loginPage.get();
    }

    @Test
    // Verify errors display when attempting to login without credentials.
    public void loginWithoutCredentials() throws Exception {
        MessageLogger.logTestStart("LoginTest", "LoginWithoutCredentials");
        loginPage.submitLogin("", "", false);

        assertTrue("LoginTest", "LoginWithoutCredentials",
                "Check login error messages!",
                (emailErrorText + "|" + passwordErrorText).equals(loginPage.getInputErrors()));
    }

    @Test
    // Verify password error displays when attempting to login without one.
    public void loginWithoutPassword() throws Exception {
        MessageLogger.logTestStart("LoginTest", "LoginWithoutPassword");
        loginPage.submitLogin(user.getEmail(), "", false);

        assertTrue("LoginTest", "LoginWithoutPassword",
                "Check login error messages!", (passwordErrorText).equals(loginPage.getInputErrors()));
    }

    @Test
    // Verify email error displays when attempting to login without one.
    public void loginWithoutEmail() throws Exception {
        MessageLogger.logTestStart("LoginTest", "LoginWithoutEmail");
        loginPage.submitLogin("", user.getPassword(), false);

        assertTrue("LoginTest", "LoginWithoutEmail",
                "Check login error messages!", (emailErrorText).equals(loginPage.getInputErrors()));
    }

    @Test
    // Verify an error displays above the submit button when attempting to login with invalid credentials.
    public void loginWithInvalidCredentials() throws Exception {
        MessageLogger.logTestStart("LoginTest", "LoginWithInvalidCredentials");
        loginPage.submitLogin("nouser@gmail.com", "Pass1234", false);

        try {
            new WebDriverWait(localDriver, 15).until(
                    ExpectedConditions.visibilityOfElementLocated(loginPage.flashError));
        }catch (WebDriverException e) {
            MessageLogger.logError("LoginTest", "LoginWithInvalidCredentials", "No error message displays.");
        }

        assertTrue("LoginTest", "LoginWithInvalidCredentials",
                "Check flash alert message!",
                "You have entered an invalid email or password.".equals(loginPage.getFlashAlertError()));
    }

    @Test
    // Verify valid credentials can be used to log into an account.
    public void loginWithValidCredentials() throws Exception {
        MessageLogger.logTestStart("LoginTest", "LoginWithValidCredentials");
        Accounts.createAccount(user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getUserName(), user.getPassword());
        loginPage.submitLogin(user.getEmail(), user.getPassword(), false);

        try {
            new WebDriverWait(localDriver, 15).until(ExpectedConditions.titleIs("TestDriver - Gateway"));
        }catch (WebDriverException e) {
            MessageLogger.logError("LoginTest", "LoginWithValidCredentials", "Gateway page does not display.");
        }

        assertTrue("LoginTest", "LoginWithValidCredentials",
                "Cannot log into account.", localDriver.getTitle().equals("TestDriver - Gateway"));
    }

    @Test
    // Verify the version schema displays correctly.
    public void verifyAppVersionDisplaysCorrectly() throws Exception {
        String pattern = "v\\d.\\d.\\d \\[\\D{5,}\\]";
        MessageLogger.logTestStart("LoginTest", "VerifyAppVersionDisplaysCorrectly");

        assertTrue("LoginTest", "VerifyAppVersionDisplaysCorrectly",
                "Application version does not display correctly.", loginPage.getAppVersion().matches(pattern));
    }

    @After
    // Log out of user's account post-test.
    public void logOutOfAccountIfLoggedIn() {
        localDriver.navigate().to("http://localhost:3000/logout");
    }

    @AfterClass
    public static void closeApplication() {
        localDriver.quit();
    }
}
