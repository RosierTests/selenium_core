package site.authentication;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import site.Accounts;
import site.BaseTest;
import site.pages.authentication.LoginPage;
import utilities.users.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

/**
 * Created By: Brian Smith on 3/4/14.
 * Description: This test verifies the actions and error messages associated with the login process.
 */
@Description("Perform various login tests. This includes verifying errors displayed when attempting to login with invalid credentials.")
public class LoginTest extends BaseTest{
    private LoginPage loginPage;
    private User user = new User("Tywin", "Lannister", "tlannister@gmail.com", "tlannister", "Pass1234");
    private String emailErrorText = "You must enter a valid email address.";
    private String passwordErrorText = "You must enter your password.";

    @Before
    public void setUp() {
        openBrowser(testName.getMethodName());

        loginPage = new LoginPage(localDriver);
        loginPage.get();
    }

    @Features("Login")
    @Stories("Login without credentials")
    @Test
    public void loginWithoutCredentials() throws Exception {
        loginPage.submitLogin("", "", false);
        assertThat("Check login error messages!", loginPage.getInputErrors(),
                is(equalTo((emailErrorText + "|" + passwordErrorText))));
    }

    @Features("Login")
    @Stories("Login without password")
    @Test
    public void loginWithoutPassword() throws Exception {
        loginPage.submitLogin(user.getEmail(), "", false);
        assertThat("Check login error messages!", loginPage.getInputErrors(), is(equalTo(passwordErrorText)));
    }

    @Features("Login")
    @Stories("Login without email")
    @Test
    public void loginWithoutEmail() throws Exception {
        loginPage.submitLogin("", user.getPassword(), false);
        assertThat("Check login error messages!", loginPage.getInputErrors(), is(equalTo(emailErrorText)));
    }

    @Features("Login")
    @Stories("Login with invalid credentials")
    @Test
    public void loginWithInvalidCredentials() throws Exception {
        loginPage.submitLogin("nouser@gmail.com", "Pass1234", false);
        assertThat("Check flash alert message!", loginPage.getFlashAlertError(),
                is(equalTo("You have entered an invalid email or password.")));
    }

    @Features("Login")
    @Stories("Login with valid credentials")
    @Test
    public void loginWithValidCredentials() throws Exception {
        Accounts.createAccount(user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getUserName(), user.getPassword());
        loginPage.submitLogin(user.getEmail(), user.getPassword(), false);
        assertThat("Cannot log into account.", loginPage.getPageTitle(), is(equalTo("TestDriver - Gateway")));
    }

    @Features("Login")
    @Stories("Verify app version displays correctly")
    @Test
    public void verifyAppVersionDisplaysCorrectly() throws Exception {
        String pattern = "v\\d.\\d.\\d";
        assertTrue("Application version does not display correctly.", loginPage.getAppVersion().matches(pattern));
    }

    @After
    public void tearDown() {
        BaseTest.getScreenShot();
        BaseTest.localDriver.quit();
    }
}
