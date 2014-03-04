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

    @FindBy(how= How.CSS, using="form button:first-child")
    public WebElement submitButton;

    @FindBy(how= How.CSS, using="form button:last-child")
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
        String title = localDriver.getTitle();
        assertTrue("The login page does not display.", localDriver.getTitle().equals("MockDriver - Login"));
    }

    /**
     * Enter your account credentials when attempting to log into the application.
     * @param email your account email address
     * @param password your account password
     */
    public void enterEmailAddressAndPassword(String email, String password) {
        this.emailInput.sendKeys(email);
        this.passwordInput.sendKeys(password);
    }

    /**
     * Return error messages displayed for email addresses and passwords.
     * @return a concatenated string of both user name and password errors, or an empty string if none.
     */
    public String getErrorMessages() {
        String concatenatedErrorMessage = "";
        if (emailInputError.isDisplayed()) {concatenatedErrorMessage += emailInputError.getText();}
        if (!concatenatedErrorMessage.equals("")) {concatenatedErrorMessage += "|";}
        if (passwordInputError.isDisplayed()) {concatenatedErrorMessage += passwordInputError.getText();}
        return concatenatedErrorMessage;
    }

    /**
     * Submit your credentials.
     */
    public void submitLogin() {
        submitButton.click();
    }
}
