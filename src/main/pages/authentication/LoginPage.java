package authentication;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import static junit.framework.Assert.assertTrue;

/**
 * Created By: Brian Smith on 2/24/14.
 * Package: authentication
 * Description:
 */
public class LoginPage extends LoadableComponent<LoginPage>{
    private WebDriver localDriver;

    @FindBy(how= How.CSS, using=".email-field input")
    @CacheLookup
    public WebElement emailInput;

    @FindBy(how= How.CSS, using=".email-field small:last-child")
    public WebElement emailInputError;

    @FindBy(how= How.CSS, using=".password-field input")
    @CacheLookup
    public WebElement passwordInput;

    @FindBy(how= How.CSS, using=".password-field small:last-child")
    public WebElement passwordInputError;

    @FindBy(how= How.CSS, using="form input:last-child")
    public WebElement rememberComputer;

    @FindBy(how= How.XPATH, using="//form/div[@tag='data-alert']/div")
    public WebElement flashError;

    @FindBy(how= How.CSS, using="form button:first-child")
    @CacheLookup
    public WebElement submitButton;

    @FindBy(how= How.CSS, using="form button:last-child")
    @CacheLookup
    public WebElement createAccountButton;

    @FindBy(how= How.CSS, using="form .row:last-child div")
    public WebElement version;

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
        localDriver.navigate().to("http://localhost:3000");
    }

    /**
     * Throw an error if the login page cannot load.
     * @throws Error
     */
    @Override
    protected void isLoaded() throws Error {
        //Assert if the title does not match.
        assertTrue("The login page does not display.", localDriver.getTitle().equals("MockDriver - Login"));
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
    }

    /**
     * Return error messages displayed for email addresses and passwords.
     * @return a concatenated string of both user name and password errors, or an empty string if none.
     */
    public String getInputErrors() {
        String concatenatedErrorMessage = "";
        if (emailInputError.isDisplayed()) {concatenatedErrorMessage += emailInputError.getText();}
        if (!concatenatedErrorMessage.equals("")) {concatenatedErrorMessage += "|";}
        if (passwordInputError.isDisplayed()) {concatenatedErrorMessage += passwordInputError.getText();}
        return concatenatedErrorMessage;
    }

    /**
     * Return Rails flash error message.
     * @return a string flash message error
     */
    public String getFlashAlertError() {
        String message;
        message = flashError.isDisplayed() ? flashError.getText() : "";
        return message;
    }

    /**
     * Select the button to create an account.
     */
    public void createAccount() {
        createAccountButton.click();
    }

    /**
     * Return application version string.
     * @return application version
     */
    public String getAppVersion() {
        return version.getText();
    }
}
