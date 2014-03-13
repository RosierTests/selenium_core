import authentication.CreateAccountPage;
import authentication.LoginPage;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import users.User;

import static junit.framework.Assert.assertTrue;

/**
 * Created By: Brian Smith on 3/4/14.
 * Description:
 */
public class CreateAccountTest {
    static WebDriver localDriver;
    /* Set a new User object with basic user data. This must be valid info in the database. You could also assign this
     * dynamically through the createUser method of the CreateAccount page enhancing reusable account creation
     * at any point you need it.
     */
    private User user = new User("Donald", "Knuth", "dknuth@gmail.com", "dknuth", "Pass1234");
    private CreateAccountPage createAccountPage;
    private String firstNameErrorText = "You must enter your first name.";
    private String lastNameErrorText = "You must enter your last name.";

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
    // The first action before each test is always to navigate to the create account page.
    public void navigateToLoginPage() {
        /* x
         *
         */
        LoginPage loginPage = new LoginPage(localDriver);
        createAccountPage = new CreateAccountPage(localDriver, loginPage);
    }

    @Test
    // Verify errors display when attempting to login without credentials.
    public void createAccountWithoutAccountInfo() {
        createAccountPage.submitAccountCreationRequest("", "", "", "", "", "");
        assertTrue("Check login error messages!", (firstNameErrorText + "|" + lastNameErrorText)
                .equals(createAccountPage.getInputErrors()));
    }

    @AfterClass
    public static void closeApplication() {
        localDriver.quit();
    }
}
