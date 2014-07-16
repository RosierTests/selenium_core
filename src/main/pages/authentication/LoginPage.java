package pages.authentication;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.output.MessageLogger;

import static junit.framework.TestCase.assertTrue;

/**
 * Created By: Brian Smith on 2/24/14.
 * Package: authentication
 * Description: This page represents the login page.
 */
public class LoginPage extends LoadableComponent<LoginPage>{
    private WebDriver localDriver;

    @FindBy(how= How.CSS, using=".email-field input")
    public WebElement emailInput;

    @FindBy(how= How.CSS, using=".email-field small:last-child")
    public WebElement emailInputError;

    @FindBy(how= How.CSS, using=".password-field input")
    public WebElement passwordInput;

    @FindBy(how= How.CSS, using=".password-field small:last-child")
    public WebElement passwordInputError;

    @FindBy(how= How.CSS, using="form input:last-child")
    public WebElement rememberComputer;

    @FindBy(how= How.CSS, using="form button:first-child")
    public WebElement submitButton;

    @FindBy(how= How.CSS, using="form button:last-child")
    public WebElement createAccountButton;

    @FindBy(how= How.CSS, using="form .row:last-child div")
    public WebElement version;

    public By flashError = By.cssSelector("form #flash_alert");

    public LoginPage(WebDriver driver) {
        this.localDriver = driver;
        PageFactory.initElements(this.localDriver, this);
    }

    /**
     * Load the login page.
     */
    @Override
    protected void load() {
        localDriver.manage().deleteAllCookies();
        MessageLogger.logAction("LoginPage", "load()", "Delete all cookies.");

        localDriver.navigate().to("http://localhost:3000");
        new WebDriverWait(localDriver, 30).until(
                ExpectedConditions.titleIs("TestDriver - Login"));

        MessageLogger.logAction("LoginPage", "load()", "Navigate to login page.");
    }

    /**
     * Throw an error if the login page cannot load.
     * @throws Error
     */
    @Override
    protected void isLoaded() throws Error {
        //Assert if the title does not match.
        assertTrue("The login page does not display.", localDriver.getTitle().equals("TestDriver - Login"));
    }

    /**
     * Enter your account credentials, select if credentials are cached, and submit. You can enter empty strings for
     * email address or password to test for errors.
     * @param email your account email address
     * @param password your account password
     * @param isCached is your account info cached?
     */
    public void submitLogin(String email, String password, Boolean isCached) {
        this.emailInput.sendKeys(email);
        this.passwordInput.sendKeys(password);
        if (isCached) { rememberComputer.click(); }
        submitButton.click();

        MessageLogger.logAction("LoginPage", "submitLogin()", "Email: \"" + email + "\", Password: \"" + password + "\", IsCached: " +
                isCached);
    }

    /**
     * Return error messages displayed for email addresses and passwords.
     * @return a concatenated string of both user name and password errors, or an empty string if none.
     */
    public String getInputErrors() {
        String concatenatedErrorMessage = "";
        if (emailInputError.isDisplayed()) {concatenatedErrorMessage += emailInputError.getText();}
        if (!concatenatedErrorMessage.equals("") && passwordInputError.isDisplayed()) {concatenatedErrorMessage += "|";}
        if (passwordInputError.isDisplayed()) {concatenatedErrorMessage += passwordInputError.getText();}

        MessageLogger.logAction("LoginPage", "getInputErrors()", "Email Error: \"" + emailInputError.getText() +
                "\", Password Error: \"" + passwordInputError.getText() + "\"");
        return concatenatedErrorMessage;
    }

    /**
     * Return Rails flash error message.
     * @return a string flash message error
     */
    public String getFlashAlertError() {
        String message;
        message = (!localDriver.findElement(flashError).isDisplayed() ? "" : localDriver.findElement(flashError).getText());

        MessageLogger.logAction("LoginPage", "getFlashAlertError()", "Flash Error: \"" + message + "\"");
        return message;
    }

    /**
     * Navigate to the create account page.
     */
    public void createAccount() {
        createAccountButton.click();
        MessageLogger.logAction("LoginPage", "createAccount()", "Click create account button.");
    }

    /**
     * Return application version string.
     * @return application version
     */
    public String getAppVersion() {
        MessageLogger.logAction("LoginPage", "getAppVersion()", "Version: \"" + version.getText() + "\"");
        return version.getText();
    }
}
