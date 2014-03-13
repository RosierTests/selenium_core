package authentication;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import static junit.framework.Assert.assertTrue;

/**
 * Created By: Brian Smith on 3/10/14.
 * Package: authentication
 * Description:
 */
public class CreateAccountPage extends LoadableComponent<CreateAccountPage>{
    private WebDriver localDriver;
    private LoadableComponent parent;

    @FindBy(how= How.CSS, using="form .name-field:first-child")
    public WebElement firstNameInput;

    @FindBy(how= How.CSS, using=".name-field:first-child small:last-child")
    public WebElement firstNameInputError;

    @FindBy(how= How.CSS, using="form .name-field:nth-child(2)")
    public WebElement lastNameInput;

    @FindBy(how= How.CSS, using=".name-field:nth-child(2) small:last-child")
    public WebElement lastNameInputError;

    @FindBy(how= How.CSS, using="form .email-field")
    public WebElement emailInput;

    @FindBy(how= How.CSS, using="form .name-field:last-child")
    public WebElement userNameInput;

    @FindBy(how= How.CSS, using="form .password-field")
    public WebElement passwordInput;

    @FindBy(how= How.CSS, using="form .password-confirmation-field")
    public WebElement confirmPasswordInput;

    @FindBy(how= How.CSS, using="form a")
    public WebElement termsOfUseLink;

    @FindBy(how= How.CSS, using="form button:first-child")
    public WebElement createAccountButton;

    @FindBy(how= How.CSS, using="form button:last-child")
    public WebElement returnToLoginButton;

    public CreateAccountPage(WebDriver driver, LoadableComponent parent) {
        this.localDriver = driver;
        this.parent = parent;
        PageFactory.initElements(this.localDriver, this);
    }

    /**
     * Load the create account page.
     */
    @Override
    protected void load() {
        parent.get();

        LoginPage page = new LoginPage(localDriver);
        page.createAccount();
    }

    /**
     * Throw an error if the create account page cannot load.
     * @throws Error
     */
    @Override
    protected void isLoaded() throws Error {
        assertTrue("Create Account page does not display.", localDriver.getTitle().equals("MockDriver - Create Account"));
    }

    /**
     * Submit a request to create an account.
     * @param firstName user's first name
     * @param lastName user's last name
     * @param email user's email
     * @param userName user's user name
     * @param password user's password
     * @param confirmPassword user's password
     */
    public void submitAccountCreationRequest(String firstName, String lastName, String email, String userName, String password,
                                  String confirmPassword) {
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        emailInput.sendKeys(email);
        userNameInput.sendKeys(userName);
        passwordInput.sendKeys(password);
        confirmPasswordInput.sendKeys(confirmPassword);
        createAccountButton.click();
    }

    /**
     * Return error messages displayed for all fields.
     * @return a concatenated string of all input field error messages, or an empty string if none.
     */
    public String getInputErrors() {
        String concatenatedErrorMessage = "";
        if (firstNameInputError.isDisplayed()) {concatenatedErrorMessage += firstNameInputError.getText();}
        if (!concatenatedErrorMessage.equals("")) {concatenatedErrorMessage += "|";}
        if (lastNameInputError.isDisplayed()) {concatenatedErrorMessage += lastNameInputError.getText();}
        return concatenatedErrorMessage;
    }

    /**
     * Navigate back to the login page.
     */
    public void navigateBackToLoginPage() {
        returnToLoginButton.click();
    }
}
