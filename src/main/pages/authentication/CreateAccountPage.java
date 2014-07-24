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
import ru.yandex.qatools.allure.annotations.Parameter;
import ru.yandex.qatools.allure.annotations.Step;
import utilities.output.MessageLogger;

import static junit.framework.TestCase.assertTrue;

/**
 * Created By: Brian Smith on 3/10/14.
 * Package: authentication
 * Description: This page represents the create account page.
 */
public class CreateAccountPage extends LoadableComponent<CreateAccountPage>{
    private WebDriver localDriver;
    private LoadableComponent parent;

    @Parameter("First Name Error Message")
    private String firstNameErrorMessage;

    @Parameter("Last Name Error Message")
    private String lastNameErrorMessage;

    @Parameter("Email Error Message")
    private String emailErrorMessage;

    @Parameter("User Name Error Message")
    private String userNameErrorMessage;

    @Parameter("Password Error Message")
    private String passwordErrorMessage;

    @Parameter("Confirm Password Error Message")
    private String confirmPasswordErrorMessage;

    @FindBy(how= How.NAME, using="firstname")
    public WebElement firstNameInput;

    @FindBy(how= How.CSS, using="form .row:nth-child(2) .name-field:first-child small:last-child")
    public WebElement firstNameInputError;

    @FindBy(how= How.NAME, using="lastname")
    public WebElement lastNameInput;

    @FindBy(how= How.CSS, using="form .row:nth-child(2) .name-field:nth-child(2) small:last-child")
    public WebElement lastNameInputError;

    @FindBy(how= How.NAME, using="email")
    public WebElement emailInput;

    @FindBy(how= How.CSS, using=".email-field small:last-child")
    public WebElement emailInputError;

    @FindBy(how= How.NAME, using="username")
    public WebElement userNameInput;

    @FindBy(how= How.CSS, using="form .row:nth-child(4) .name-field small:last-child")
    public WebElement userNameInputError;

    @FindBy(how= How.NAME, using="password")
    public WebElement passwordInput;

    @FindBy(how= How.CSS, using=".password-field small:last-child")
    public WebElement passwordInputError;

    @FindBy(how= How.NAME, using="confirmpassword")
    public WebElement confirmPasswordInput;

    @FindBy(how= How.CSS, using=".password-confirmation-field small:last-child")
    public WebElement confirmPasswordInputError;

    @FindBy(how= How.CSS, using="form a")
    public WebElement termsOfUseLink;

    @FindBy(how= How.CSS, using="form button:first-child")
    public WebElement createAccountButton;

    @FindBy(how= How.CSS, using="form button:last-child")
    public WebElement returnToLoginButton;

    public By flashError = By.cssSelector("form #flash_notice");

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

        new WebDriverWait(localDriver, 30).until(
                ExpectedConditions.titleIs("TestDriver - Create Account"));

        MessageLogger.logAction("CreateAccountPage", "load()", "Load create account page.");
    }

    /**
     * Throw an error if the create account page cannot load.
     * @throws Error
     */
    @Override
    protected void isLoaded() throws Error {
        assertTrue("Create Account page does not display.", localDriver.getTitle().equals("TestDriver - Create Account"));
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
    @Step("Submit account creation request with first name \"{0}\", last name \"{1}\", email \"{2}\", username \"{3}\", password \"{4}\", and confirm password \"{5}\".")
    public void submitAccountCreationRequest(String firstName, String lastName, String email, String userName, String password,
                                  String confirmPassword) {
        inputDataOrClear(firstNameInput, firstName);
        MessageLogger.logAction("CreateAccountPage", "submitAccountCreationRequest()", "Enter first name \"" +
                firstName + "\"");
        inputDataOrClear(lastNameInput, lastName);
        MessageLogger.logAction("CreateAccountPage", "submitAccountCreationRequest()", "Enter last name \"" +
                lastName + "\"");
        inputDataOrClear(emailInput, email);
        MessageLogger.logAction("CreateAccountPage", "submitAccountCreationRequest()", "Enter email \"" + email + "\"");
        inputDataOrClear(userNameInput, userName);
        MessageLogger.logAction("CreateAccountPage", "submitAccountCreationRequest()", "Enter user name \"" +
                userName + "\"");
        inputDataOrClear(passwordInput, password);
        MessageLogger.logAction("CreateAccountPage", "submitAccountCreationRequest()", "Enter password \"" +
                password + "\"");
        inputDataOrClear(confirmPasswordInput, confirmPassword);
        MessageLogger.logAction("CreateAccountPage", "submitAccountCreationRequest()",
                "Enter confirm password \"" + confirmPassword + "\"");
        createAccountButton.click();
        MessageLogger.logAction("CreateAccountPage", "submitAccountCreationRequest()", "Click create account button.");
    }

    /**
     * Return error messages displayed for all fields.
     * @return a concatenated string of all input field error messages, or an empty string if none.
     */
    @Step
    public String getInputErrors() {
        String concatenatedErrorMessage = "";
        // First Name
        if (firstNameInputError.isDisplayed()) {
            firstNameErrorMessage = firstNameInputError.getText();
            concatenatedErrorMessage += firstNameErrorMessage;
        }
        if (!concatenatedErrorMessage.equals("") && lastNameInputError.isDisplayed()) {concatenatedErrorMessage += "|";}
        // Last Name
        if (lastNameInputError.isDisplayed()) {
            lastNameErrorMessage = lastNameInputError.getText();
            concatenatedErrorMessage += lastNameErrorMessage;
        }
        if (!concatenatedErrorMessage.equals("") && emailInputError.isDisplayed()) {concatenatedErrorMessage += "|";}
        // Email Address
        if (emailInputError.isDisplayed()) {
            emailErrorMessage = emailInputError.getText();
            concatenatedErrorMessage += emailErrorMessage;
        }
        if (!concatenatedErrorMessage.equals("") && userNameInputError.isDisplayed()) {concatenatedErrorMessage += "|";}
        // User name
        if (userNameInputError.isDisplayed()) {
            userNameErrorMessage = userNameInputError.getText();
            concatenatedErrorMessage += userNameErrorMessage;
        }
        if (!concatenatedErrorMessage.equals("") && passwordInputError.isDisplayed()) {concatenatedErrorMessage += "|";}
        // Password
        if (passwordInputError.isDisplayed()) {
            passwordErrorMessage = passwordInputError.getText();
            concatenatedErrorMessage += passwordErrorMessage;
        }
        if (!concatenatedErrorMessage.equals("") &&
                confirmPasswordInputError.isDisplayed()) {concatenatedErrorMessage += "|";}
        // Password Confirmation
        if (confirmPasswordInputError.isDisplayed()) {
            confirmPasswordErrorMessage = confirmPasswordInputError.getText();
            concatenatedErrorMessage += confirmPasswordErrorMessage;
        }

        MessageLogger.logAction("CreateAccountPage", "getInputErrors()", "First Name Error: \"" +
                firstNameErrorMessage + "\", Last Name Error: \"" + lastNameErrorMessage + "\", Email Error: \"" +
                emailErrorMessage + "\", User Name Error: \"" + userNameErrorMessage + "\", Password Error: \"" +
                passwordErrorMessage + "\", Password Confirmation Error: \"" + confirmPasswordErrorMessage + "\"");
        return concatenatedErrorMessage;
    }

    /**
     * Navigate back to the login page.
     */
    @Step
    public void navigateBackToLoginPage() {
        returnToLoginButton.click();
        MessageLogger.logAction("CreateAccountPage", "navigateBackToLoginPage()", "Click return to login button.");
    }

    /**
     * Clear the input if an empty string is found, otherwise input the text.
     * @param element WebElement
     * @param text input text
     */
    @Step("Enter \"{1}\".")
    private void inputDataOrClear(WebElement element, String text) {
        if (text.equals("") || text == null) {
            element.clear();
        }else {
            element.sendKeys(text);
        }
    }
}
