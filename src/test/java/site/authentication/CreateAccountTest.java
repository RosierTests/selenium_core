package site.authentication;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import site.BaseTest;
import site.pages.authentication.CreateAccountPage;
import site.pages.authentication.LoginPage;
import utilities.users.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

/**
 * Created By: Brian Smith on 3/4/14.
 * Description: This test verifies creating accounts and error messages associated with failed attempts.
 */
@Description("Validate created accounts and errors displayed when attempting to create accounts with invalid data.")
public class CreateAccountTest extends BaseTest{
    private User user = new User("Cersei", "Lannister", "clannister@gmail.com", "clannister", "Pass1234");
    private CreateAccountPage createAccountPage;

    @Before
    public void setUp() {
        openBrowser(testName.getMethodName());

        LoginPage loginPage = new LoginPage(localDriver);
        createAccountPage = new CreateAccountPage(localDriver, loginPage);
        createAccountPage.get();
    }

    @Features("Create Account")
    @Stories("Create account without account info")
    @Test
    public void createAccountWithoutAccountInfo() throws Exception {
        createAccountPage.submitAccountCreationRequest("", "", "", "", "", "");
        assertThat("Check create account error messages!", createAccountPage.getInputErrors(),
                is(equalTo("You must enter your first name.|" +
                        "You must enter your last name.|You must enter a valid email address.|" +
                        "You must enter a username.|You must enter a password of at least 8 characters.|" +
                        "Passwords must match.")));
    }

    @Features("Create Account")
    @Stories("Create account with non-matching passwords")
    @Test
    public void createAccountWithNonMatchingPasswords() throws Exception {
        createAccountPage.submitAccountCreationRequest(user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getUserName(), user.getPassword(), "Pass0000");
        assertThat("Check create account error messages!", createAccountPage.getInputErrors(),
                is(equalTo("Passwords must match.")));
    }

    @Features("Create Account")
    @Stories("Create account with valid data")
    @Test
    public void createAccountWithValidData() throws Exception {
        createAccountPage.submitAccountCreationRequest(user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getUserName(), user.getPassword(), user.getPassword());
        assertThat("Account cannot be created.", createAccountPage.getPageTitle(), is(equalTo("TestDriver - Gateway")));
    }

    @Features("Create Account")
    @Stories("Navigate back to login page")
    @Test
    public void navigateBackToLoginPage() throws Exception {
        createAccountPage.navigateBackToLoginPage();
        assertThat("Login page does not display.", createAccountPage.getPageTitle(), is(equalTo("TestDriver - Login")));
    }

    @After
    public void tearDown() {
        BaseTest.getScreenShot();
        BaseTest.localDriver.quit();
    }
}
