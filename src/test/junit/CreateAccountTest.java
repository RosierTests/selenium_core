import authentication.CreateAccountPage;
import authentication.LoginPage;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import output.LogResults;
import users.User;

import static junit.framework.Assert.assertTrue;

/**
 * Created By: Brian Smith on 3/4/14.
 * Description: This test verifies creating accounts and error messages associated with failed attempts.
 */
public class CreateAccountTest {
    static WebDriver localDriver;
    /* Set a new User object with basic user data. This must be valid info in the database. You could also assign this
     * dynamically through the createUser method of the CreateAccount page enhancing reusable account creation
     * at any point you need it.
     */
    private User user = new User("Donald", "Knuth", "dknuth@gmail.com", "dknuth", "Pass1234");
    private CreateAccountPage createAccountPage;

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
    // Navigate to the create account page.
    public void navigateToLoginPage() {
        /* x
         *
         */
        LoginPage loginPage = new LoginPage(localDriver);
        createAccountPage = new CreateAccountPage(localDriver, loginPage);
        createAccountPage.get();
    }

    @Test
    // Verify errors display when attempting to login without credentials.
    public void createAccountWithoutAccountInfo() {
        LogResults.logTestStart("CreateAccountTest", "createAccountWithoutAccountInfo()");
        createAccountPage.submitAccountCreationRequest("", "", "", "", "", "");
        assertTrue("Check create account error messages!", ("You must enter your first name.|" +
                "You must enter your last name.|You must enter a valid email address.|You must enter a username.|" +
                "You must enter a password of at least 8 characters.")
                .equals(createAccountPage.getInputErrors()));
    }

    @Test
    // Verify errors display when attempting to login without matching passwords.
    public void createAccountWithNonMatchingPasswords() {
        LogResults.logTestStart("CreateAccountTest", "createAccountWithTwoDifferentPasswords()");
        createAccountPage.submitAccountCreationRequest(user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getUserName(), user.getPassword(), "Pass0000");
        assertTrue("Check create account error messages!", ("Passwords must match.")
                .equals(createAccountPage.getInputErrors()));
    }

    @Test
    // Verify errors display when attempting to login without matching passwords.
    public void createAccountWithValidData() {
        LogResults.logTestStart("CreateAccountTest", "createAccountWithValidData()");
        createAccountPage.submitAccountCreationRequest(user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getUserName(), user.getPassword(), user.getPassword());
        assertTrue("Create account failed.", localDriver.getTitle().equals("TestDriver - Gateway"));
    }

    @Test
    // Verify you can navigate back to the login page.
    public void navigateBackToLoginPage() {
        LogResults.logTestStart("CreateAccountTest", "navigateBackToLoginPage()");
        createAccountPage.navigateBackToLoginPage();
        assertTrue("Login page does not display.", localDriver.getTitle().equals("TestDriver - Login"));
    }

    @AfterClass
    public static void closeApplication() {
        localDriver.quit();
    }
}
