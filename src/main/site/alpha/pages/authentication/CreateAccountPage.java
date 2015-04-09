package site.alpha.pages.authentication;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.LoadableComponent;
import ru.yandex.qatools.allure.annotations.Parameter;
import ru.yandex.qatools.allure.annotations.Step;
import site.BasePage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

/**
 * Created By: Brian Smith on 3/10/14.
 * Package: authentication
 * Description: This page represents the create account page.
 */
public class CreateAccountPage extends BasePage{
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

    @FindBy(name = "firstname")
    public WebElement firstNameInput;

    @FindBy(css = "form .row:nth-child(2) .name-field:first-child .error")
    public WebElement firstNameInputError;

    @FindBy(name = "lastname")
    public WebElement lastNameInput;

    @FindBy(css = "form .row:nth-child(2) .name-field:nth-child(2) .error")
    public WebElement lastNameInputError;

    @FindBy(name = "email")
    public WebElement emailInput;

    @FindBy(css = ".email-field .error")
    public WebElement emailInputError;

    @FindBy(name = "username")
    public WebElement userNameInput;

    @FindBy(css = "form .row:nth-child(4) .name-field .error")
    public WebElement userNameInputError;

    @FindBy(name = "password")
    public WebElement passwordInput;

    @FindBy(css = ".password-field .error")
    public WebElement passwordInputError;

    @FindBy(name = "confirmpassword")
    public WebElement confirmPasswordInput;

    @FindBy(css = ".password-confirmation-field .error")
    public WebElement confirmPasswordInputError;

    @FindBy(css = "form a")
    public WebElement termsOfUseLink;

    @FindBy(css = "form button:first-child")
    public WebElement createAccountButton;

    @FindBy(css = "form button:last-child")
    public WebElement returnToLoginButton;

    public By flashError = By.cssSelector("form #flash_notice");

    public CreateAccountPage(WebDriver driver, LoadableComponent parent) {
        super(driver);
        this.parent = parent;
    }

    /**
     * Load the create account page.
     */
    @Override
    protected void load() {
        parent.get();

        LoginPage page = new LoginPage(localDriver);
        page.createAccount();

        waitUntilTitleIs("TestDriver - Create Account", 30);
    }

    /**
     * Throw an error if the create account page cannot load.
     * @throws Error
     */
    @Override
    protected void isLoaded() throws Error {
        assertThat("Create Account page does not display.", getPageTitle(), is(equalTo("TestDriver - Create Account")));
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
        input(firstName, firstNameInput);
        input(lastName, lastNameInput);
        input(email, emailInput);
        input(userName, userNameInput);
        input(password, passwordInput);
        input(confirmPassword, confirmPasswordInput);
        click(createAccountButton);
    }

    /**
     * Return error messages displayed for all fields.
     * @return a concatenated string of all input field error messages, or an empty string if none.
     */
    @Step
    public String getInputErrors() {
        String concatenatedErrorMessage = "";

        // First Name
        if (elementDisplays(firstNameInputError)) {
            firstNameErrorMessage = firstNameInputError.getText();
            concatenatedErrorMessage += firstNameErrorMessage;
        }
        if (!concatenatedErrorMessage.equals("") && elementDisplays(firstNameInputError)) {concatenatedErrorMessage += "|";}

        // Last Name
        if (elementDisplays(lastNameInputError)) {
            lastNameErrorMessage = lastNameInputError.getText();
            concatenatedErrorMessage += lastNameErrorMessage;
        }
        if (!concatenatedErrorMessage.equals("") && elementDisplays(lastNameInputError)) {concatenatedErrorMessage += "|";}

        // Email Address
        if (elementDisplays(emailInputError)) {
            emailErrorMessage = emailInputError.getText();
            concatenatedErrorMessage += emailErrorMessage;
        }
        if (!concatenatedErrorMessage.equals("") && elementDisplays(emailInputError)) {concatenatedErrorMessage += "|";}

        // User name
        if (elementDisplays(userNameInputError)) {
            userNameErrorMessage = userNameInputError.getText();
            concatenatedErrorMessage += userNameErrorMessage;
        }
        if (!concatenatedErrorMessage.equals("") && elementDisplays(userNameInputError)) {concatenatedErrorMessage += "|";}

        // Password
        if (elementDisplays(passwordInputError)) {
            passwordErrorMessage = passwordInputError.getText();
            concatenatedErrorMessage += passwordErrorMessage;
        }
        if (!concatenatedErrorMessage.equals("") &&
                elementDisplays(passwordInputError)) {concatenatedErrorMessage += "|";}

        // Password Confirmation
        if (elementDisplays(confirmPasswordInputError)) {
            confirmPasswordErrorMessage = confirmPasswordInputError.getText();
            concatenatedErrorMessage += confirmPasswordErrorMessage;
        }

        return concatenatedErrorMessage;
    }

    /**
     * Navigate back to the login page.
     */
    @Step
    public void navigateBackToLoginPage() {
        click(returnToLoginButton);

        waitUntilTitleIs("TestDriver - Login", 30);
    }
}
