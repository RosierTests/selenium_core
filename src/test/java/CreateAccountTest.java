import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.authentication.CreateAccountPage;
import pages.authentication.LoginPage;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import utilities.DriverManager;
import utilities.ElapsedTime;
import utilities.output.MessageLogger;
import utilities.output.Screenshot;
import utilities.users.User;

import static utilities.AssertSpecial.assertTrue;

/**
 * Created By: Brian Smith on 3/4/14.
 * Description: This test verifies creating accounts and error messages associated with failed attempts.
 */
@Description("Validate created accounts and errors displayed when attempting to create accounts with invalid data.")
public class CreateAccountTest {
    static WebDriver localDriver;
    /* Set a new User object with basic user data. This must be valid info in the database. You could also assign this
     * dynamically through the createUser method of the Accounts page enhancing reusable account creation
     * at any point you need it.
     */
    private User user = new User("Cersei", "Lannister", "clannister@gmail.com", "clannister", "Pass1234");
    private CreateAccountPage createAccountPage;
    private Screenshot screenshot;

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
    // Navigate to the create account page.
    public void navigateToLoginPage() {
        ElapsedTime.setStartTime();
        screenshot = new Screenshot(localDriver);

        /* x
         *
         */
        LoginPage loginPage = new LoginPage(localDriver);
        createAccountPage = new CreateAccountPage(localDriver, loginPage);
        createAccountPage.get();
    }

    @Features("Create Account")
    @Stories("Create account without account info")
    @Test
    // Verify errors display when attempting to login without credentials.
    public void createAccountWithoutAccountInfo() throws Exception {
        MessageLogger.logTestStart("CreateAccountTest", "CreateAccountWithoutAccountInfo");
        createAccountPage.submitAccountCreationRequest("", "", "", "", "", "");

        screenshot.getScreenShot();
        assertTrue("CreateAccountTest", "CreateAccountWithoutAccountInfo", "Check create account error messages!",
                ("You must enter your first name.|" +
                "You must enter your last name.|You must enter a valid email address.|You must enter a username.|" +
                "You must enter a password of at least 8 characters.").equals(createAccountPage.getInputErrors()));
    }

    @Features("Create Account")
    @Stories("Create account with non-matching passwords")
    @Test
    // Verify errors display when attempting to login without matching passwords.
    public void createAccountWithNonMatchingPasswords() throws Exception {
        MessageLogger.logTestStart("CreateAccountTest", "CreateAccountWithNonMatchingPasswords");
        createAccountPage.submitAccountCreationRequest(user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getUserName(), user.getPassword(), "Pass0000");

        screenshot.getScreenShot();
        assertTrue("CreateAccountTest", "CreateAccountWithNonMatchingPasswords",
                "Check create account error messages!", ("Passwords must match.")
                        .equals(createAccountPage.getInputErrors()));
    }

    @Features("Create Account")
    @Stories("Create account with valid data")
    @Test
    // Verify errors display when attempting to login without matching passwords.
    public void createAccountWithValidData() throws Exception {
        MessageLogger.logTestStart("CreateAccountTest", "CreateAccountWithValidData");
        createAccountPage.submitAccountCreationRequest(user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getUserName(), user.getPassword(), user.getPassword());

        try {
            new WebDriverWait(localDriver, 15).until(ExpectedConditions.titleIs("TestDriver - Gateway"));
        }catch (WebDriverException e) {
            MessageLogger.logError("Accounts", "CreateAccount",
                        localDriver.findElement(createAccountPage.flashError).getText());
        }

        screenshot.getScreenShot();
        assertTrue("CreateAccountTest", "CreateAccountWithValidData",
                "Account cannot be created.", localDriver.getTitle().equals("TestDriver - Gateway"));
    }

    @Features("Create Account")
    @Stories("Navigate back to login page")
    @Test
    // Verify you can navigate back to the login page.
    public void navigateBackToLoginPage() throws Exception {
        MessageLogger.logTestStart("CreateAccountTest", "NavigateBackToLoginPage");
        createAccountPage.navigateBackToLoginPage();

        try {
            new WebDriverWait(localDriver, 15).until(ExpectedConditions.titleIs("TestDriver - Login"));
        }catch (WebDriverException e) {
            MessageLogger.logError("CreateAccountTest", "NavigateBackToLoginPage", "Login page does not display.");
        }

        screenshot.getScreenShot();
        assertTrue("CreateAccountTest", "NavigateBackToLoginPage",
                "Login page does not display.", localDriver.getTitle().equals("TestDriver - Login"));
    }

    @AfterClass
    public static void closeApplication() {
        localDriver.quit();
    }
}
