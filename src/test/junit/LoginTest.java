import authentication.LoginPage;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import users.User;

import static junit.framework.Assert.assertTrue;

/**
 * Created By: Brian Smith on 3/4/14.
 * Description:
 */
public class LoginTest {
    static WebDriver localDriver;
    /* Set a new User object with basic user data. This must be valid info in the database. You could also assign this
     * dynamically through the createUser method of the CreateAccount page enhancing reusable account creation
     * at any point you need it.
     */
    private User user = new User("Donald", "Knuth", "rocheTesting@gmail.com", "dKnuth", "Pass1234");
    private LoginPage loginPage;
    private String emailErrorText = "You must enter a valid email address.";
    private String passwordErrorText = "You must enter your password.";

    @BeforeClass
    //Open the browser and navigate to the initial page.
    public static void openBrowser() {
        // Open a new browser window.
        localDriver = new FirefoxDriver();

        /* Set the size of the browser window before starting the test. Use this to control testing of the
         * minimum required resolution.
         */
        Dimension browserWindow = new Dimension(1050, 768);
        localDriver.manage().window().setSize(browserWindow);
    }

    @Before
    // The first action before each test is always to navigate to the login page.
    public void navigateToLoginPage() {
        /* Call the LoadableComponent get() method which in turn calls isLoaded() on the class extending
         * LoadableComponent. If isLoaded() causes a failure, the load() method is called and isLoaded() is called
         * again after.
         */
        loginPage = new LoginPage(localDriver);
        loginPage.get();
    }

    @Test
    public void loginWithoutCredentials() {
        loginPage.submitLogin();
        assertTrue("Check input error messages!", (emailErrorText + "|" + passwordErrorText)
                .equals(loginPage.getErrorMessages()));
    }

    @Test
    public void loginWithInvalidCredentials() {

    }

    @Test
    public void loginWithValidCredentials() {

    }

    @Test
    public void verifyAppVersionDisplaysCorrectly() {
        assertTrue("Application version does not display correctly.",
                loginPage.version.getText().equals("v0.1.0 [daggerfall]"));
    }

    @AfterClass
    public static void closeApplication() {
        localDriver.quit();
    }
}
