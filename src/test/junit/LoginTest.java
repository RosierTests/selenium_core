import authentication.LoginPage;
import org.junit.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import output.LogResults;
import users.User;

import static junit.framework.Assert.assertTrue;

/**
 * Created By: Brian Smith on 3/4/14.
 * Description: This test verifies the actions and error messages associated with the login process.
 */
public class LoginTest {
    static WebDriver localDriver;
    /* Set a new User object with basic user data. This must be valid info in the database. You could also assign this
     * dynamically through the createUser method of the CreateAccount page enhancing reusable account creation
     * at any point you need it.
     */
    private User user = new User("Donald", "Knuth", "dknuth@gmail.com", "dknuth", "Pass1234");
    private LoginPage loginPage;
    private String emailErrorText = "You must enter a valid email address.";
    private String passwordErrorText = "You must enter your password.";

    @BeforeClass
    //Open the browser and navigate to the initial page.
    public static void openBrowser() {
        // Open a new browser window.
        DriverManager.set(DriverManager.Browser.FIREFOX);
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
        /* Call the LoadableComponent get() method which in turn calls isLoaded() on the class extending
         * LoadableComponent. If isLoaded() causes a failure, the load() method is called and isLoaded() is called
         * again after.
         */
        loginPage = new LoginPage(localDriver);
        loginPage.get();
    }

    @Test
    // Verify errors display when attempting to login without credentials.
    public void loginWithoutCredentials() {
        LogResults.logTestStart("LoginTest", "loginWithoutCredentials()");
        loginPage.submitLogin("", "", false);
        assertTrue("Check login error messages!", (emailErrorText + "|" + passwordErrorText)
                .equals(loginPage.getInputErrors()));
    }

    @Test
    // Verify password error displays when attempting to login without one.
    public void loginWithoutPassword() {
        LogResults.logTestStart("LoginTest", "loginWithEmailWithoutPassword()");
        loginPage.submitLogin(user.getEmail(), "", false);
        assertTrue("Check login error messages!", (passwordErrorText)
                .equals(loginPage.getInputErrors()));
    }

    @Test
    // Verify email error displays when attempting to login without one.
    public void loginWithoutEmail() {
        LogResults.logTestStart("LoginTest", "loginWithPasswordWithoutEmail()");
        loginPage.submitLogin("", user.getPassword(), false);
        assertTrue("Check login error messages!", (emailErrorText)
                .equals(loginPage.getInputErrors()));
    }

    @Test
    // Verify an error displays above the submit button when attempting to login with invalid credentials.
    public void loginWithInvalidCredentials() {
        LogResults.logTestStart("LoginTest", "loginWithInvalidCredentials()");
        loginPage.submitLogin("nouser@gmail.com", "Pass1234", false);
        assertTrue("Check flash alert message!", "You have entered an invalid email or password.".
                equals(loginPage.getFlashAlertError()));
    }

    @Ignore
    public void loginWithValidCredentials() {
        LogResults.logTestStart("LoginTest", "loginWithValidCredentials()");
        loginPage.submitLogin(user.getEmail(), user.getPassword(), false);
    }

    @Test
    // Verify the version schema displays correctly.
    public void verifyAppVersionDisplaysCorrectly() {
        String pattern = "v\\d.\\d.\\d \\[\\D{5,}\\]";
        LogResults.logTestStart("LoginTest", "verifyAppVersionDisplaysCorrectly()");
        assertTrue("Application version does not display correctly.", loginPage.getAppVersion().matches(pattern));
    }

    @AfterClass
    public static void closeApplication() {
        localDriver.quit();
    }
}
