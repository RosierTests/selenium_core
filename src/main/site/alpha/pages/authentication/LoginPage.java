package site.alpha.pages.authentication;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Parameter;
import ru.yandex.qatools.allure.annotations.Step;
import site.BasePage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

/**
 * Created By: Brian Smith on 2/24/14.
 * Package: authentication
 * Description: This page represents the login page.
 */
public class LoginPage extends BasePage{
    @Parameter("Email Error Message")
    private String emailErrorMessage;

    @Parameter("Password Error Message")
    private String passwordErrorMessage;

    @Parameter("Flash Alert Message")
    private String flashAlertMessage;

    @Parameter("App Version")
    private String appVersion;

    @FindBy(css = ".email-field input")
    public WebElement emailInput;

    @FindBy(css = ".email-field small:last-child")
    public WebElement emailInputError;

    @FindBy(css = ".password-field input")
    public WebElement passwordInput;

    @FindBy(css = ".password-field small:last-child")
    public WebElement passwordInputError;

    @FindBy(css = "form input:last-child")
    public WebElement rememberComputer;

    @FindBy(css = "form button:first-child")
    public WebElement submitButton;

    @FindBy(css = "form button:last-child")
    public WebElement createAccountButton;

    @FindBy(css = "form .row:last-child div")
    public WebElement version;

    public By flashError = By.cssSelector("form #flash_alert");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Load the login page.
     */
    @Override
    protected void load() {
        removeAllCookies();
        navigate_to("login");
        waitUntilTitleIs("TestDriver - Login", 30);
    }

    /**
     * Throw an error if the login page cannot load.
     * @throws Error
     */
    @Override
    protected void isLoaded() throws Error {
        //Assert if the title does not match.
        assertThat("Login page does not display.", getPageTitle(), is(equalTo("TestDriver - Login")));
    }

    /**
     * Enter your account credentials, select if credentials are cached, and submit. You can enter empty strings for
     * email address or password to test for errors.
     * @param email your account email address
     * @param password your account password
     * @param isCached is your account info cached?
     */
    @Step("Submit login with email \"{0}\" and password \"{1}\".")
    public void submitLogin(String email, String password, Boolean isCached) {
        input(email, emailInput);
        input(password, passwordInput);

        if (isCached) { click(rememberComputer); }
        click(submitButton);
    }

    /**
     * Return error messages displayed for email addresses and passwords.
     * @return a concatenated string of both user name and password errors, or an empty string if none.
     */
    @Step
    public String getInputErrors() {
        String concatenatedErrorMessage = "";

        if (elementDisplays(emailInputError)) {
            emailErrorMessage = emailInputError.getText();
            concatenatedErrorMessage += emailErrorMessage;
        }

        if (!concatenatedErrorMessage.equals("") && elementDisplays(passwordInputError)) {concatenatedErrorMessage += "|";}

        if (elementDisplays(passwordInputError)) {
            passwordErrorMessage = passwordInputError.getText();
            concatenatedErrorMessage += passwordErrorMessage;
        }

        return concatenatedErrorMessage;
    }

    /**
     * Return Rails flash error message.
     * @return a string flash message error
     */
    @Step
    public String getFlashAlertError() {
        waitUntilVisible(getElementBy(flashError), 15);
        flashAlertMessage = (!elementDisplays(getElementBy(flashError)) ? "" : getElementBy(flashError).getText());
        return flashAlertMessage;
    }

    /**
     * Navigate to the create account page.
     */
    @Step
    public void createAccount() {
        click(createAccountButton);
    }

    /**
     * Return application version string.
     * @return application version
     */
    @Step
    public String getAppVersion() {
        appVersion = version.getText();
        return appVersion;
    }
}
